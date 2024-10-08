package app.dao.interfaces;

import app.dto.GuestDto;


public interface GuestDao {
    
    public void createGuest (GuestDto guestDto) throws Exception;
    
    public void findById (GuestDto guestDto) throws Exception;
    
    public void updateGuest (GuestDto guestDto) throws Exception;

}
