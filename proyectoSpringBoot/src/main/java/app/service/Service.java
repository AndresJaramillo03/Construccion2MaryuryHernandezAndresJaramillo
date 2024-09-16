package app.service;

import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.service.interfaces.LoginService;
import java.sql.SQLException;
import app.dto.PartnerDto;
import app.service.interfaces.AdminService;

public class Service implements LoginService, AdminService {
        private UserDao userDao;
        private PersonDao personDao;
        private UserDto userDto;
        private PartnerDto partnerDto;
        
        public static UserDto user;
        
        public Service() {
		this.userDao = new UserDaoImplementation();
		this.personDao = new PersonDaoImplementation();
            }    

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

        public void updateUser(UserDto userDto) throws Exception {
            if (userDto == null || userDto.getUserName() == null) {
                throw new Exception("Datos de usuario invalidos");
            }
            userDao.updateUser(userDto);
        }
}