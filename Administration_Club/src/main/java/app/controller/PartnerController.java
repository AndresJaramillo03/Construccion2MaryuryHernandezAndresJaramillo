package app.controller;

import app.controller.validator.PersonValidator;
import app.dto.PersonDto;
import app.dto.UserDto;

public class PartnerController {
    private PersonValidator personValidator;
    
    private void createGuest(){
        
        System.out.println("Ingrese el nombre");
        String name = Utils.getReader().nextLine();
        System.out.println("Ingrese su cédula");
        String cedula = Utils.getReader().nextLine();
        System.out.println("Ingrese su número de telefono");
        Long celPhone = personValidator.validCelphone(Utils.getReader().nextLine());
        System.out.println("Ingrese el nombre del usuario");
        String username = Utils.getReader().nextLine();
        System.out.println("Ingrese la contraseña del usuario");  
        String password = Utils.getReader().nextLine();
        
        
        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setCedula(cedula); 
        personDto.setCelPhone(celPhone); 
        
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setRole("Invitado");
        System.out.println("El usuario ha sido creado exitosamente");
    }
}
