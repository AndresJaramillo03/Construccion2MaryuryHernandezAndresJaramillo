package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.controller.validator.PartnerValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.Service;
import app.service.interfaces.AdminService;
import app.service.interfaces.LoginService;
import java.time.LocalDateTime;
import app.dto.PartnerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Getter
@Setter
@NoArgsConstructor

public class AdminController implements ControllerInterface { 
        @Autowired
    	private PersonValidator personValidator;
        @Autowired
	private UserValidator userValidator;
        @Autowired
        private PartnerValidator partnerValidator;
        @Autowired
	private AdminService adminService;
        @Autowired
        private AdminService service;
	private static final String MENU = "ingrese la opcion que desea \n 1. para crear socio \n 2. para ver factura Club \n 3. para ver factura Socio \n 4. para ver factura Invitado \n 5. para aprobar promocion \n 6. para cerrar sesion \n";

	public AdminController() {
                this.partnerValidator = new PartnerValidator();
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

	private boolean options(String option) throws Exception{
		switch (option) {
		case "1": {
			this.createPartner();
			return true;
		}

		case "2": {
			System.out.println("Visualización de factura de club.");
			return false;
		}
		case "3": {
			System.out.println("Visualización de factura de socio.");
			return false;
		}
		case "4": {
			System.out.println("Visualización de factura de invitado.");
			return false;
		}
		case "5": {
			System.out.println("Promoción");
			return false;
                }
		case "6": {
			System.out.println("Se ha cerrado sesion");
			return false;
		}
                
		default: {
			System.out.println("ingrese una opcion valida");
			return true;
		}
		}
	}

    private void createPartner()throws Exception {
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
        
        System.out.println("Ingrese el monto");
        double amount = partnerValidator.validAmount(Utils.getReader().nextLine());
        String type = "partner";

        
        PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setCedula(cedula);
        personDto.setCellPhone(celPhone);
         
       
        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setRole("partner");
        userDto.setPersonId(personDto);
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUserId(userDto);
        partnerDto.setAmount(amount);
        partnerDto.setType(type);
        partnerDto.setCreationDate(LocalDateTime.now());
        this.service.createPartner(partnerDto);
        System.out.println("El usuario ha sido creado exitosamente");
                
    }
}
