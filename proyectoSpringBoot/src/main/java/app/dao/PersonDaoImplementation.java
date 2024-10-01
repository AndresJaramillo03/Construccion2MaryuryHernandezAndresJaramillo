package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PersonDao;
import app.dao.repository.PersonRepository;
import app.dto.PersonDto;
import app.helpers.Helper;
import app.model.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Getter
@Setter
@NoArgsConstructor
@Service

public class PersonDaoImplementation implements PersonDao {
        @Autowired
        PersonRepository personRepository;

	@Override
	public boolean existsByDocument(PersonDto personDto) throws Exception {
            return personRepository.existsByCedula(personDto.getCedula());
            
	}

	@Override
	public void createPerson(PersonDto personDto) throws Exception {
		Person person = Helper.parse(personDto);
               personRepository.save(person);
               personDto.setId(person.getId());
	}

	@Override
	public void deletePerson(PersonDto personDto) throws Exception {
		Person person = Helper.parse(personDto);
                personRepository.delete(person);
        }
                

	@Override
	public PersonDto findByDocument(PersonDto personDto) throws Exception {
            Person person = personRepository.findByCedula(personDto.getCedula());
            return Helper.parse(person);
            
            
        }

}


