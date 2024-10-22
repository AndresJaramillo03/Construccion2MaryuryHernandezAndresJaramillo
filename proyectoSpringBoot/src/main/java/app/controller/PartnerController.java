package app.controller;

import app.controller.validator.GuestValidator;
import app.controller.validator.InvoiceValidator;
import app.controller.validator.PartnerValidator;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Partner;
import app.service.ClubService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Setter
@Getter
@NoArgsConstructor
@Controller
public class PartnerController implements ControllerInterface {
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private GuestValidator guestValidator;
    @Autowired
    private PartnerValidator partnerValidator;
    @Autowired
    private ClubService service;
    @Autowired
    private InvoiceValidator invoiceValidator;
    
    private static final String MENU = "ingrese la opcion que desea \n 1. para crear invitado \n 2. para activar invitado \n 3. para desactivar invitado \n 4. para solicitar baja \n 5. para recargar fondos\n 6. para realizar consumo \n 7. para cerrar sesion \n";


    
    @Override
	public void session() throws Exception {
		boolean session = true;
		while (session) {
			session = menu();
		}

	}
	private boolean menu() {
		try {
			System.out.println("bienvenido " + ClubService.user.getUserName());
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
                    return this.requestUnsubscribe();
                }
                case "5": {
                    this.rechargeFunds();
                    return true;
                }
                
                case "6": {
                    this.makeConsumption();
                    return true;
                }
                
                case "7": {
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
        userDto.setPersonId(personDto);
        userDto.setUserName(username);
        userDto.setPassword(password);
        userDto.setRole("guest");
        
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId(userDto);
        guestDto.setStatus("Inactive");
        
        this.service.createGuest(guestDto);
        System.out.println("El Invitado ha sido creado exitosamente");
    }
    
    private void activateGuest() throws Exception {
        //Validar que si es un socio regular no pueda activar mas de 3 invitado al tiempo ------------------------------------
        System.out.println("Ingrese el id del invitado que desea activar:");
        long id = guestValidator.validId(Utils.getReader().nextLine());
        
        GuestDto guestDto = new GuestDto();
        guestDto.setId(id);
        
        service.activateGuest(guestDto);
        System.out.println("El invitado ha sido activado exitosamente");
    }

    private void deactivateGuest() throws Exception {
        System.out.println("Ingrese el id del invitado que desea desactivar:");
        long id = guestValidator.validId(Utils.getReader().nextLine());
        
        GuestDto guestDto = new GuestDto();
        guestDto.setId(id);
        
        service.inactivateGuest(guestDto);
        System.out.println("El invitado ha sido desactivado exitosamente");
    }
     
private boolean requestUnsubscribe() throws Exception {
    try {

        this.service.requestUnsubscribe();
        System.out.println("Se te ha dado de baja con éxito! Cerrando sesión.");
        return true;
    } catch (Exception e) {

        System.out.println(e.getMessage());
        return false;
    }
}
    
    private void rechargeFunds() throws Exception{
        System.out.println("Ingrese el monto que desea recargar");
        double amount = partnerValidator.validAmount(Utils.getReader().nextLine());
        this.service.rechargeFunds(amount);
        System.out.println("Recarga realizada con exito");
    }
    
    private void makeConsumption () throws Exception {
        List<InvoiceDetailDto> invoiceDtoList = new ArrayList<>();
       
        System.out.println ("Ingrese el consumo que desea: ");
        while(true){
            System.out.println("Ingrese su producto: ");
            String product =Utils.getReader().nextLine();
            invoiceValidator.validProduct(product);
            System.out.println("Ingrese el valor de su producto: ");
            double productCost = invoiceValidator.validAmount(Utils.getReader().nextLine());
            System.out.println("Ingrese la cantidad: ");
            int quantity = invoiceValidator.validItem(Utils.getReader().nextLine());

            System.out.println("Desea agregar otro producto? \n Enter para continuar comprando \n 1. Para confirmar la compra");
            String option = Utils.getReader().nextLine();
            
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setCreationDate(LocalDateTime.now());
            invoiceDto.setStatus("Pendiente");
            invoiceDto.setTotalAmount(productCost*quantity);
            
            InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
            invoiceDetailDto.setDescription(product);
            invoiceDetailDto.setAmount(productCost);
            invoiceDetailDto.setItem(quantity);
            invoiceDetailDto.setInvoiceId(invoiceDto);
            
            invoiceDtoList.add(invoiceDetailDto);
            
            if(option.equals("1")){
                break;
                
            }
        }
        System.out.println("Compra realizada");
        this.service.createInvoice(invoiceDtoList);
    }
}