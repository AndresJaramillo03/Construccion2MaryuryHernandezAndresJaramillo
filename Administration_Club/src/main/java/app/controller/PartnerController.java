package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PersonDto;
import app.dto.UserDto;

public class PartnerController implements ControllerInterface {
    private PersonValidator personValidator;
    private UserValidator userValidator;
    
    @Override
	public void session() throws Exception {
		boolean session = true;
		while (session) {
			//session = menu();
		}

	}
    
    private void createGuest()throws Exception{
        
        System.out.println("Ingrese el nombre");
        String name = Utils.getReader().nextLine();
        personValidator.validName(name);
        System.out.println("Ingrese su cédula");
        long cedula = personValidator.validCedula(Utils.getReader().nextLine());
        System.out.println("Ingrese su número de telefono");
        long celPhone = personValidator.validCellphone(Utils.getReader().nextLine());
        System.out.println("Ingrese el nombre del usuario");
        String username = Utils.getReader().nextLine();
        userValidator.validUserName(username);
        System.out.println("Ingrese la contraseña del usuario");  
        String password = Utils.getReader().nextLine();
        userValidator.validPassword(password);
        
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
