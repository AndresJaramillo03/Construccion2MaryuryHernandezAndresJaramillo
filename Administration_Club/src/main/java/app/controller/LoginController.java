package app.controller;

import app.dto.UserDto;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private void login () {
    System.out.println("Ingrese el usuario");
    String userName = Utils.getReader().nextLine();
    System.out.println("Ingrese la contraseña");
    String password = Utils.getReader().nextLine();
    UserDto userDto = new UserDto();
    userDto.setPassword(password);
    userDto.setUserName(userName);
    this.service.login(userDto);
    
  }  
}
