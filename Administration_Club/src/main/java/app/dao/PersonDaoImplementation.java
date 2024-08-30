package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PersonDao;
import app.dto.PersonDto;
import app.helpers.Helper;
import app.model.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PersonDaoImplementation implements PersonDao {

	@Override
	public boolean existsByDocument(PersonDto personDto) throws Exception {
		String query = "SELECT 1 FROM PERSON WHERE DOCUMENT = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1, personDto.getCedula());
		ResultSet resulSet = preparedStatement.executeQuery();
		boolean exists = resulSet.next();
		resulSet.close();
		preparedStatement.close();
		return exists;
	}

	@Override
	public void createPerson(PersonDto personDto) throws Exception {
		Person person = Helper.parse(personDto);
		String query = "INSERT INTO PERSON(NAME,DOCUMENT,CELLPHONE) VALUES (?,?,?) ";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setString(1, person.getName());
		preparedStatement.setLong(2,person.getCedula());
		preparedStatement.setLong(3, person.getCelPhone());
		preparedStatement.execute();
		preparedStatement.close();
	}

	@Override
	public void deletePerson(PersonDto personDto) throws Exception {
		Person person = Helper.parse(personDto);
		String query = "DELETE FROM PERSON WHERE DOCUMENT = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1,person.getCedula());
		preparedStatement.execute();
		preparedStatement.close();	}

	@Override
	public PersonDto findByDocument(PersonDto personDto) throws Exception {
		String query = "SELECT ID,NAME,CEDULA,CELLPHONE FROM PERSON WHERE DOCUMENT = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1, personDto.getCedula());
		ResultSet resulSet = preparedStatement.executeQuery();
		if (resulSet.next()) {
			Person person = new Person();
			person.setId(resulSet.getLong("ID"));
			person.setName(resulSet.getString("NAME"));
			person.setCedula(resulSet.getLong("CEDULA"));
			person.setCelPhone(resulSet.getInt("CELPHONE"));
			resulSet.close();
			preparedStatement.close();
			return Helper.parse(person);
		}
		resulSet.close();
		preparedStatement.close();
		return null;
	}

}


