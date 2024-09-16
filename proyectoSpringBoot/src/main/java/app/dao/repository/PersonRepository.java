package app.dao.repository;

import app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {

    public boolean existsByCedula(long cedula);

    public Person findByCedula(long cedula);
    
}
