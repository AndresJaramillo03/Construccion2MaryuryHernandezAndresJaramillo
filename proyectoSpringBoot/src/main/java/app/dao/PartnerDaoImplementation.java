package app.dao;

import app.dao.repository.PersonRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.dao.interfaces.PartnerDao;
import app.dao.repository.PartnerRepository;
import app.helpers.Helper;
import app.dto.PartnerDto;
import app.model.Partner;

@Getter
@Setter
@NoArgsConstructor
@Service

public class PartnerDaoImplementation implements PartnerDao{
        @Autowired
        PersonRepository personRepository;
        
	@Override
	public PartnerDto findByUserId(PartnerDto partnerDto) throws Exception {
                Partner partner = PartnerRepository.findByUserId(partnerDto.getUserId());
                return Helper.parse(partner);
	}

	/*@Override
	public boolean existsByUserName(PartnerDto userDto) throws Exception {
		return userRepository.existsByUserName(userDto.getUserName());
                
		}

	@Override
	public void createUser(PartnerDto userDto) throws Exception {
		User user = Helper.parse(userDto);
		userRepository.save(user);
                userDto.setId(user.getId());
		}*/
    
}
