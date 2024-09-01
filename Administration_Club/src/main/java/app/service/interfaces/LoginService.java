package app.service.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;

public interface LoginService {
    public void login(UserDto userDto) throws Exception;
    public void logout();

    public void createPartner(UserDto partnerDto) throws Exception;
}
