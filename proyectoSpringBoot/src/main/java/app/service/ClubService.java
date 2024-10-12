package app.service;

import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dao.interfaces.GuestDao;
import app.dao.interfaces.InvoiceDao;
import app.dao.interfaces.InvoiceDetailDao;
import app.dao.interfaces.PartnerDao;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.service.interfaces.LoginService;
import java.sql.SQLException;
import app.dto.PartnerDto;
import app.service.interfaces.AdminService;
import app.service.interfaces.PartnerService;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class ClubService implements LoginService, AdminService, PartnerService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PartnerDao partnerDao;
    
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceDetailDao invoiceDetailDao;
    @Autowired
    private GuestDao guestDao;
    
    
    public static UserDto user;
    
    @Override
    public void login(UserDto userDto) throws Exception {
        UserDto validateDto = userDao.findByUserName(userDto);

        if (validateDto == null) {
            throw new Exception("no existe este usuario registrado");
        }

        if (!userDto.getPassword().equals(validateDto.getPassword())) {
            throw new Exception("usuario o contrasena incorrecto");
        }

        userDto.setRole(validateDto.getRole());
        userDto.setPersonId(validateDto.getPersonId());
        userDto.setId(validateDto.getId());
        user = userDto;
    }

    @Override
    public void logout() {
        user = null;
        System.out.println("se ha cerrado sesion");
    }

    private void createUser(UserDto userDto) throws Exception {

        this.createPerson(userDto.getPersonId());
        if (this.userDao.existsByUserName(userDto)) {
            this.personDao.deletePerson(userDto.getPersonId());
            throw new Exception("ya existe un usuario con ese user name");
        }
        try {
            this.userDao.createUser(userDto);
        } catch (SQLException e) {
            this.personDao.deletePerson(userDto.getPersonId());
        }
    }

    private void createPerson(PersonDto personDto) throws Exception {
        if (this.personDao.existsByDocument(personDto)) {
            throw new Exception("ya existe una persona con ese documento");
        }
        this.personDao.createPerson(personDto);
    }

    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        this.createUser(partnerDto.getUserId());
        try {
            this.partnerDao.createPartner(partnerDto);
        } catch(SQLException e){
            System.out.println("Ocurrio un error: " + e.getMessage());
            this.personDao.deletePerson(partnerDto.getUserId().getPersonId());
        }   
    }
    
    @Override
    public void createGuest(GuestDto guestDto) throws Exception{
        this.createUser(guestDto.getUserId());

        PartnerDto partnerDto = partnerDao.findByUserId(user);
        
        guestDto.setPartnerId(partnerDto);
        
        try {
            this.guestDao.createGuest(guestDto);
        } catch(SQLException e){
            System.out.println("Ocurrio un error: " + e.getMessage());
            this.personDao.deletePerson(guestDto.getUserId().getPersonId());
        }    
    }

    public UserDto findUserByUsername(String username) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        UserDto foundUserDto = userDao.findByUserName(userDto);

        if (foundUserDto == null) {
            throw new Exception("Usuario no encontrado.");
        }
        return foundUserDto;
    }

    //Terminar metodo para que funcione la clase PartnerController :( Actualizar
    public void updateUser(UserDto userDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public void createInvoice(List<InvoiceDetailDto> invoiceDtoList) throws Exception {
        InvoiceDto invoiceDto = invoiceDtoList.get(0).getInvoiceId();
        invoiceDto.setUserId(user.getPersonId());
        if(user.getRole().equals("Partner")){
            invoiceDto.setPartnerId(partnerDao.findByUserId(user));
        }else{
            GuestDto guestDto = guestDao.findByUserId(user);
            invoiceDto.setPartnerId(guestDto.getPartnerId());
        }
        this.invoiceDao.createInvoice(invoiceDto);

        for (InvoiceDetailDto invoiceDetailDto : invoiceDtoList) {
            invoiceDetailDto.setInvoiceId(invoiceDto);
            this.invoiceDetailDao.createInvoiceDetail(invoiceDetailDto);
        }
    }
}
