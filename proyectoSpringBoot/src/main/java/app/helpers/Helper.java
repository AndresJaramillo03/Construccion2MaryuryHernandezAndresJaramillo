package app.helpers;

import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Person;
import app.model.User;
import app.model.Partner;
import app.dto.PartnerDto;
import java.time.LocalDateTime;

public abstract class Helper {
	public static PersonDto parse(Person person) {
		PersonDto personDto = new PersonDto();
		personDto.setId(person.getId());
		personDto.setCedula(person.getCedula());
		personDto.setCellPhone(person.getCellPhone());
		personDto.setName(person.getName());
		return personDto;
	}
	
	public static Person parse(PersonDto personDto) {
		Person person = new Person();
		person.setId(personDto.getId());
		person.setCedula(personDto.getCedula());
		person.setCellPhone(personDto.getCellPhone());
		person.setName(personDto.getName());
		return person;
	}
	
	public static UserDto parse(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setPassword(user.getPassword());
		userDto.setPersonId(parse(user.getPersonId()));
		userDto.setRole(user.getRole());
		userDto.setUserName(user.getUserName());
		return userDto;
	}
	
	public static User parse(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setPassword(userDto.getPassword());
		user.setPersonId(parse(userDto.getPersonId()));
		user.setRole(userDto.getRole());
		user.setUserName(userDto.getUserName());
		return user;
	}
        
	public static PartnerDto parse(Partner partner) {
		PartnerDto partnerDto = new PartnerDto();
		partnerDto.setId(partner.getId());
		partnerDto.setUserId(parse(partner.getUserId()));
		partnerDto.setAmount(partner.getAmount());
		partnerDto.setType(partner.getType());
		partnerDto.setCreationDate(partner.getDate());
		return partnerDto;
	}
        
	public static Partner parse(PartnerDto partnerDto) {
		Partner partner = new Partner();
		partner.setId(partnerDto.getId());
		partner.setUserId(parse(partnerDto.getUserId()));
		partner.setAmount(partnerDto.getAmount());
		partner.setType(partnerDto.getType());
		partner.setDate(partnerDto.getCreationDate());
		return partner;
	}
}