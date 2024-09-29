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
    PartnerRepository partnerRepository;

    @Override
    public PartnerDto findByUserId(PartnerDto partnerDto) throws Exception {
            Partner partner = partnerRepository.findByUserId(partnerDto.getUserId());
            //if()
            //throw new Exception("");
            return Helper.parse(partner);
    }

    @Override
    public PartnerDto findById(PartnerDto partnerDto) throws Exception {
         throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean existsById(PartnerDto partnerDto) throws Exception {
        return partnerRepository.existsById(partnerDto.getId());
    }

    @Override
    public void createPartner(PartnerDto partnerDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void updatePartner (PartnerDto partnerDto) throws Exception {
        Partner partner  = Helper.parse(partnerDto);
        partnerRepository.save(partner);
    }
}