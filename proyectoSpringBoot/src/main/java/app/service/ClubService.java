package app.service;

import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dao.interfaces.PartnerDao;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.service.interfaces.LoginService;
import java.sql.SQLException;
import app.dto.PartnerDto;
import app.service.interfaces.AdminService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class ClubService implements LoginService, AdminService {
        @Autowired
        private UserDao userDao;
        @Autowired
        private PersonDao personDao;
        @Autowired
        private PartnerDao partnerDao;
        @Autowired
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
		/* personDto = personDao.findByDocument(userDto.getPersonId());
                if (personDto == null) {
                    throw new Exception("No se pudo encontrar la persona con el documento proporcionado");
                }
		userDto.setPersonId(personDto);*/
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
            //Tener en cuenta para futuras correcciones.
            this.createUser(partnerDto.getUserId());
            //partnerDto.setUserId(userDao.findByUserName(partnerDto.getUserId()));
            this.partnerDao.createPartner(partnerDto);
            
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
        
    //Terminar metodo para que funcione la clase PartnerController :(
    public void updateUser(UserDto userDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}