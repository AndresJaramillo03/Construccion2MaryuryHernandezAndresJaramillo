package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;

public class PartnerController implements ControllerInterface {
    private PersonValidator personValidator;
    private UserValidator userValidator;
    private Service service;
    private static final String MENU = "ingrese la opcion que desea \n 1. para crear invitado \n 2. para activar invitado \n 3. para desactivar invitado \n 4. para solicitar baja \n 5. para cerrar sesion \n";

    public PartnerController() {
        this.personValidator = new PersonValidator();
        this.userValidator = new UserValidator();
        this.service = new Service();
        
    }
    
    @Override
	public void session() throws Exception {
		boolean session = true;
		while (session) {
			session = menu();
		}

	}
	private boolean menu() {
		try {
			System.out.println("bienvenido " + Service.user.getUserName());
			System.out.print(MENU);
			String option = Utils.getReader().nextLine();
			return options(option);

		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			return true;
		}
	}

        private boolean options(String option) throws Exception {
            switch (option) {
                case "1": {
                    this.createGuest();
                    return true;
                }
                case "2": {
                    this.activateGuest();
                    return true;
                }
                case "3": {
                    this.deactivateGuest();
                    return true;
                }
                case "4": {
                    this.requestDeregistration();
                    return true;
                }
                case "5": {
                    System.out.println("se ha cerrado sesion");
                    return false;
                }
                default: {
                    System.out.println("ingrese una opcion valida");
                    return true;
                }
        }
}
    
    private void createGuest()throws Exception{
        
        System.out.println("Ingrese el nombre");
        String name = Utils.getReader().nextLine();
        personValidator.validName(name);
        System.out.println("Ingrese su cedula");
        long cedula = personValidator.validCedula(Utils.getReader().nextLine());
        System.out.println("Ingrese su numero de telefono");
        long celPhone = personValidator.validCellphone(Utils.getReader().nextLine());
        System.out.println("Ingrese el nombre del usuario");
        String username = Utils.getReader().nextLine();
        userValidator.validUserName(username);
        System.out.println("Ingrese la contrasena del usuario");  
        String password = Utils.getReader().nextLine();
        userValidator.validPassword(password);
        
        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setCedula(cedula); 
        personDto.setCellPhone(celPhone); 
        
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setRole("Invitado");
        System.out.println("El usuario ha sido creado exitosamente");
    }
    
    private void activateGuest() throws Exception {
        System.out.println("Ingrese el nombre de usuario del invitado que desea activar:");
        String username = Utils.getReader().nextLine();

        UserDto userDto = service.findUserByUsername(username);

        if (userDto == null) {
            throw new Exception("No existe un invitado con ese nombre de usuario");
        }

        if (!userDto.getRole().equals("Inactivo")) {
            throw new Exception("El invitado ya esta activo o tiene otro rol");
        }

        userDto.setRole("Activo");
        service.updateUser(userDto);

        System.out.println("El invitado ha sido activado exitosamente");
    }

    private void deactivateGuest() throws Exception {
        System.out.println("Ingrese el nombre de usuario del invitado que desea desactivar:");
        String username = Utils.getReader().nextLine();

        UserDto userDto = service.findUserByUsername(username);

        if (userDto == null) {
            throw new Exception("No existe un invitado con ese nombre de usuario");
        }

        if (!userDto.getRole().equals("Activo")) {
            throw new Exception("El invitado no esta activo");
        }

        userDto.setRole("Inactivo");
        service.updateUser(userDto);

        System.out.println("El invitado ha sido desactivado exitosamente");
    }

    private void requestGuestDeactivation() throws Exception {
        System.out.println("Ingrese el nombre de usuario del invitado que desea solicitar baja:");
        String username = Utils.getReader().nextLine();

        UserDto userDto = service.findUserByUsername(username);

        if (userDto == null) {
            throw new Exception("No existe un invitado con ese nombre de usuario");
        }

        userDto.setRole("Baja solicitada");
        service.updateUser(userDto);

        System.out.println("Se ha solicitado la baja del invitado exitosamente");
    }
    
    public boolean handleOption(int option) {
        switch (option) {
            case 4:
                return requestDeregistration();

            default:
                return false;
        }
    }

    private boolean requestDeregistration() {
        System.out.println("Baja solicitada con exito");
        return true; 
    }
     
}

