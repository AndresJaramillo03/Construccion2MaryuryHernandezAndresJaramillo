package app.dao.repository;

import app.dto.UserDto;
import app.model.Partner;

public interface PartnerRepository {

    public static Partner findByUserId(UserDto userId);
    
    
}
