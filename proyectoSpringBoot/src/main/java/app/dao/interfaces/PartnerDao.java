package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;


public interface PartnerDao {
    
    	public PartnerDto findByUserName(PartnerDto userDto) throws Exception;

	public boolean existsByUserName(PartnerDto userDto) throws Exception;
	
	public void createUser(PartnerDto userDto) throws Exception;
    
}
