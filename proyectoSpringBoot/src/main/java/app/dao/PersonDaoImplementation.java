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
                
                /*
		String query = "DELETE FROM PERSON WHERE DOCUMENT = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1,person.getCedula());
		preparedStatement.execute();
		preparedStatement.close();	}*/

	@Override
	public PersonDto findByDocument(PersonDto personDto) throws Exception {
            Person person = personRepository.findByCedula(personDto.getCedula());
            return Helper.parse(person);
            
            
            
		/*String query = "SELECT ID,NAME,DOCUMENT,CELLPHONE FROM PERSON WHERE DOCUMENT = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1, personDto.getCedula());
		ResultSet resulSet = preparedStatement.executeQuery();
		if (resulSet.next()) {
			Person person = new Person();
			person.setId(resulSet.getLong("ID"));
			person.setName(resulSet.getString("NAME"));
			person.setCedula(resulSet.getLong("DOCUMENT"));
			person.setCellPhone(resulSet.getLong("CELLPHONE"));
			resulSet.close();
			preparedStatement.close();
			return Helper.parse(person);
		}
		resulSet.close();
		preparedStatement.close();
		return null; */ 
        }

}


