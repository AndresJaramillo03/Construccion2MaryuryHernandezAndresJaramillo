package app.dao.repository;

import app.dto.PartnerDto;
import app.dto.UserDto;
import app.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long>{

    public  Partner findByUserId(UserDto userId);
   
    
    
}
