package app.dao;

import app.dao.interfaces.GuestDao;
import app.dao.repository.GuestRepository;
import app.dto.GuestDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Guest;
import app.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class GuestDaoImplementation implements GuestDao {
    @Autowired
    GuestRepository guestRepository;
    
    @Override
    public void createGuest (GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        guestRepository.save(guest);
        guestDto.setId(guest.getId());
    } 
    
    @Override
    public void findById (GuestDto guestDto) throws Exception{
        Guest guest = guestRepository.findById(guestDto.getId());
        //return Helper.parse(guest);
    }
    
    @Override
    public void updateGuest (GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        guestRepository.save(guest);
    }

    @Override
    public GuestDto findByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        return guestRepository.findByUserId(user);
    }
}