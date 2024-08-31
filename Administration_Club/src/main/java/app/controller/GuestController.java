package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;

public class GuestController implements ControllerInterface {
        private PersonValidator personValidator;
	private UserValidator userValidator;
        private static final String MENU = "Ingrese la opcion que desea\n1 1: Ser socio \n2 Cerrar la Sesion\n";
    
    
    	@Override
	public void session() throws Exception {
		boolean session = true;
		while (session) {
			session = menu();
		}

	}
        	private boolean menu() {
		try {
			//System.out.println("bienvenido " + Service.user.getUserName());
			System.out.print(MENU);
			String option = Utils.getReader().nextLine();
			return options(option);

		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			return true;
		}
	}
        private boolean options(String option) throws Exception{
		switch (option) {
		case "1": {
			//this.converPartner();
			return true;
		}

		case "2": {
			System.out.println("se ha cerrado sesion");
			return false;
		}
		default: {
			System.out.println("ingrese una opcion valida");
			return true;
		}
		}
	}
}