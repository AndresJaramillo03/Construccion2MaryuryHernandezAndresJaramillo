package app.service;

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
import app.service.interfaces.InvoiceService;
import app.service.interfaces.PartnerService;
import java.util.ArrayList;
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

public class ClubService implements LoginService, AdminService, PartnerService, InvoiceService {

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
    public List<InvoiceDetailDto> getAllInvoicesDetail() throws Exception{
        return invoiceDetailDao.findAllInvocesDetail();
    }
    
    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        this.createUser(partnerDto.getUserId());
        try {
            this.partnerDao.createPartner(partnerDto);
        } catch (SQLException e) {
            System.out.println("Ocurrio un error: " + e.getMessage());
            this.personDao.deletePerson(partnerDto.getUserId().getPersonId());
        }
    }

    @Override
    public void createGuest(GuestDto guestDto) throws Exception {
        this.createUser(guestDto.getUserId());
        UserDto user = userDao.findByUserName(guestDto.getPartnerId().getUserId());
        PartnerDto partnerDto = partnerDao.findByUserId(user);

        guestDto.setPartnerId(partnerDto);

        try {
            this.guestDao.createGuest(guestDto);
        } catch (SQLException e) {
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

    @Override
    public void activateGuest(GuestDto guestDto) throws Exception {
        guestDto = guestDao.findById(guestDto);
        
        if(guestDto== null){
        throw new Exception ("invitado no existe");
        }
        
        
        guestDto.setStatus("Active");
 
        guestDao.updateGuest(guestDto);
    }

    @Override
    public void inactivateGuest(GuestDto guestDto) throws Exception {

        guestDto = guestDao.findById(guestDto);

        guestDto.setStatus("Inactive");
        guestDao.updateGuest(guestDto);
    }
    
    @Override
    public void createInvoice(List<InvoiceDetailDto> invoiceDtoList) throws Exception {
        
        InvoiceDto invoiceDto = invoiceDtoList.get(0).getInvoiceId();
        PersonDto personDto = personDao.findByDocument(invoiceDto.getUserId());
        invoiceDto.setUserId(personDto);
        
        UserDto userDto = userDao.findByUserName(invoiceDto.getPartnerId().getUserId());
        
        
        PartnerDto partnerDto = partnerDao.findByUserId(userDto);
        
        invoiceDto.setPartnerId(partnerDto);
        if (userDto.getRole().equalsIgnoreCase("Partner")) {
            invoiceDto.setPartnerId(partnerDao.findByUserId(userDto));
        } else {
            GuestDto guestDto = guestDao.findByUserId(userDto);
            
            invoiceDto.setPartnerId(guestDto.getPartnerId());
        }
        double total = 0;
        for (InvoiceDetailDto invoiceDetailDto : invoiceDtoList){
            total += invoiceDetailDto.getInvoiceId().getTotalAmount();
        }
        invoiceDto.setTotalAmount(total);
        this.invoiceDao.createInvoice(invoiceDto);
        for (InvoiceDetailDto invoiceDetailDto : invoiceDtoList) {
            invoiceDetailDto.setInvoiceId(invoiceDto);
            this.invoiceDetailDao.createInvoiceDetail(invoiceDetailDto);
        }
    }

@Override
public void requestUnsubscribe(UserDto userDto) throws Exception {
    user = userDao.findByUserName(userDto);
    PartnerDto partnerDto = partnerDao.findByUserId(user);
    
    List<InvoiceDto> invoices = invoiceDao.findByPartnerId(partnerDto);
    
    for (InvoiceDto invoice : invoices) {
        if (!invoice.getStatus().equalsIgnoreCase("pagado")) {
            throw new Exception("No puede darse de baja al socio porque tiene facturas pendientes por pagar.");
        }
    }
    personDao.deletePerson(user.getPersonId());
}

    @Override
    public void rechargeFunds(double amount, UserDto userDto) throws Exception {
        user = userDao.findByUserName(userDto);
        PartnerDto partnerDto = partnerDao.findByUserId(user);
        double currentAmount = partnerDto.getAmount();
        List<InvoiceDto> pendingInvoices = this.getPendingInvoices(userDto);
        
        if (currentAmount + amount > 1000000 && partnerDto.getType().equalsIgnoreCase("regular")) {
            throw new Exception("El monto excede el maximo permitido");
        }
        if (currentAmount + amount > 5000000 && partnerDto.getType().equalsIgnoreCase("vip")) {
            throw new Exception("El monto excede el maximo permitido");
        }
        partnerDto.setAmount(currentAmount  + amount);
        currentAmount = partnerDto.getAmount();
        if(!pendingInvoices.isEmpty()){
            for(InvoiceDto invoice: pendingInvoices){
                if(currentAmount >= invoice.getTotalAmount()){
                    currentAmount-= invoice.getTotalAmount();
                    invoice.setStatus("pagado");
                    invoiceDao.updateInvoice(invoice);
                }
            }
        }
        partnerDto.setAmount(currentAmount);
        partnerDao.updatePartner(partnerDto);
    }
    
    public List<InvoiceDto> getPendingInvoices(UserDto userDto) throws Exception {
        user = userDao.findByUserName(userDto);
        PartnerDto partnerDto = partnerDao.findByUserId(user);

        List<InvoiceDto> invoices = invoiceDao.findByPartnerId(partnerDto);
        List<InvoiceDto> pendingInvoices = new ArrayList<>();

        for (InvoiceDto invoice : invoices) {
            if (!invoice.getStatus().equalsIgnoreCase("pagado")) {
                pendingInvoices.add(invoice);
            }
    }
    
    return pendingInvoices;
}
    
    @Override
    public void updateInvoice(InvoiceDto invoiceDto) throws Exception {
    this.invoiceDao.updateInvoice(invoiceDto);  
}
    @Override
    public boolean payPendingInvoices(List<InvoiceDto> pendingInvoices,UserDto userDto) throws Exception {
        user = userDao.findByUserName(userDto);
        PartnerDto partnerDto = partnerDao.findByUserId(user);
        double currentAmount = partnerDto.getAmount();
        
        int count = 0;
        for (InvoiceDto invoice : pendingInvoices) {

            if(!invoice.getStatus().equalsIgnoreCase("pagado") && invoice.getTotalAmount() <= currentAmount){
                invoice.setStatus("pagado");
                currentAmount -= invoice.getTotalAmount();
                partnerDto.setAmount(currentAmount);
                invoiceDao.updateInvoice(invoice);
                partnerDao.updatePartner(partnerDto);
                count+= 1;
                
            }
        }
        if(count>0){
            return true;
        }else{
            return false;
        }
        
    }
    
}