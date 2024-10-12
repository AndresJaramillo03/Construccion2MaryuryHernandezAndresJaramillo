package app.dao.interfaces;

import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.UserDto;
import java.util.List;


public interface GuestDao {
    
    public void createGuest (GuestDto guestDto) throws Exception;
    
    public GuestDto findById (GuestDto guestDto) throws Exception;
    
    public void updateGuest (GuestDto guestDto) throws Exception;
    public GuestDto findByUserId(UserDto userDto) throws Exception;

}
