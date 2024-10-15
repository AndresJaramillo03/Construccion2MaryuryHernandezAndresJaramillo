package app.service.interfaces;

import app.dto.GuestDto;

public interface PartnerService {
    
    public void createGuest(GuestDto guestDto) throws Exception;
    
    public void activateGuest(GuestDto guestDto) throws Exception;
    
    public void inactivateGuest(GuestDto guestDto) throws Exception;
    
    public void requestUnsubscribe() throws Exception;
    
    public void rechargeFunds(double amount) throws Exception;
}