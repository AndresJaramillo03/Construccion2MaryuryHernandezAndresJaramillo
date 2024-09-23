package app.controller;

import app.controller.validator.UserValidator;
import app.dto.UserDto;
import app.service.Service;
import java.util.HashMap;
import java.util.Map;
import app.controller.ControllerInterface;
import app.service.interfaces.LoginService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;


@Controller
@Getter
@Setter
@NoArgsConstructor
public class LoginController implements ControllerInterface{
	private UserValidator userValidator;
        private LoginService service;
	private static final String MENU= "ingrese la opcion que desea: \n 1. para iniciar sesion. \n 2. para detener la ejecucion.";
	private Map<String,ControllerInterface> roles;
        
        
	public LoginController() {
		this.userValidator= new UserValidator();
		this.service = new Service();
		ControllerInterface adminController = new AdminController();
		ControllerInterface partnerController = new PartnerController();
                ControllerInterface guestController = (ControllerInterface) new GuestController();
		ControllerInterface PartnerController = new PartnerController();
		this.roles= new HashMap<String,ControllerInterface>();
		roles.put("admin", adminController);
		roles.put("partner", partnerController);
		roles.put("guest", guestController);
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
			System.out.println(MENU);
			String option = Utils.getReader().nextLine();
			return options(option);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return true;
		}
	}
	private boolean options(String option) throws Exception {
		switch (option) {
		case "1": {
			this.login();
			return true;
		}
		case "2": {
			System.out.println("se detiene el programa");;
			return false;
		}
		default: {
			System.out.println("ingrese una opcion valida");
			return true;
		}
		}
	}
	
	private void login()throws Exception {
		System.out.println("ingrese el usuario");
		String userName= Utils.getReader().nextLine();
		userValidator.validUserName(userName);
		System.out.println("ingrese la contraseÃ±a");
		String password= Utils.getReader().nextLine();
		userValidator.validPassword(password);
                
                
		UserDto userDto = new UserDto();
		userDto.setPassword(password);
		userDto.setUserName(userName);
		this.service.login(userDto);
                
		if(roles.get(userDto.getRole())==null) {
			throw new Exception ("Rol invalido");
		}
		roles.get(userDto.getRole()).session();	
	}
}
