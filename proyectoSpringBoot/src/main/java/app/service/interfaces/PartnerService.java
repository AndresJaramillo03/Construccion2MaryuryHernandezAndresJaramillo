package app.service.interfaces;

import app.dto.GuestDto;
import app.dto.InvoiceDto;
import app.dto.UserDto;
import java.util.List;

public interface PartnerService {
    
    public void createGuest(GuestDto guestDto) throws Exception;
    
    public void activateGuest(GuestDto guestDto) throws Exception;
    
    public void inactivateGuest(GuestDto guestDto) throws Exception;
    
    public void requestUnsubscribe(UserDto userDto) throws Exception;
    
    public void rechargeFunds(double amount,UserDto userDto) throws Exception;
    public boolean payPendingInvoices(List<InvoiceDto> pendingInvoices,UserDto userDto) throws Exception;
    
}