package app.dao.repository;

import app.dto.PartnerDto;
import app.dto.UserDto;
import app.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long>{

    //public Partner findByUserId(Long userId);
    
    @Query("SELECT p FROM Partner p JOIN p.userId u WHERE u.id = :userId")
    Partner findByUserId(@Param("userId") Long userId);
    
    public Partner findById (long id);
}
