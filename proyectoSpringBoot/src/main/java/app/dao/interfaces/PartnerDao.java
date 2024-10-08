package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;


public interface PartnerDao {
    
    	public PartnerDto findById(PartnerDto userDto) throws Exception;

	public boolean existsById(PartnerDto userDto) throws Exception;
        
        public PartnerDto findByUserId(PartnerDto partnerDto) throws Exception;

        public void createPartner(PartnerDto partnerDto) throws Exception;
    
        public void updatePartner (PartnerDto partnerDto) throws Exception;
        
}
