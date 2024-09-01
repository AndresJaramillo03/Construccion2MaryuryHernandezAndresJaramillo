package app.service;

import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.service.interfaces.LoginService;
import java.sql.SQLException;

//public class Service implements AdminService, LoginService , PartnerService {
public class Service implements LoginService {
        private UserDao userDao;
        private PersonDao personDao;
        //private InvoiceDao invoiceDao;
        private UserDto userDto;
        
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
                throw new Exception("usuario o contraseña incorrecto");
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
		PersonDto personDto = personDao.findByDocument(userDto.getPersonId());
		userDto.setPersonId(personDto);
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
}