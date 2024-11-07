package app.controller;

import app.controller.request.CreateUserRequest;
import app.controller.validator.GuestValidator;
import app.controller.validator.InvoiceValidator;
import app.controller.validator.PartnerValidator;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.ClubService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Setter
@Getter
@NoArgsConstructor
@RestController
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
   

    
    @Override
	public void session() throws Exception {
	}
    
    @PostMapping ("/createGuest")
    private ResponseEntity createGuest(@RequestBody CreateUserRequest request )throws Exception{
        try{
        
        String name = request.getName();
        personValidator.validName(name);
        long cedula = personValidator.validCedula(request.getDocument());
        long celPhone = personValidator.validCellphone(request.getNumber());
        String username = request.getUsername();
        userValidator.validUserName(username);
        String password = request.getPassword();
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
        
        PartnerDto partner = new PartnerDto();
        UserDto userPartner = new UserDto();
        userPartner.setUserName(request.getUserNamePartner());
        partner.setUserId(userPartner);
        
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId(userDto);
        guestDto.setStatus("Inactive");
        guestDto.setPartnerId(partner);
        
        this.service.createGuest(guestDto);
        return new ResponseEntity("El Invitado ha sido creado exitosamente", HttpStatus.OK);
        
        }catch (Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
    
    private void payInvoices() throws Exception {
    List<InvoiceDto> pendingInvoices = this.service.getPendingInvoices();

    if (pendingInvoices.isEmpty()) {
        System.out.println("No tienes facturas pendientes por pagar.");
        return;
    }

    System.out.println("Tienes las siguientes facturas pendientes:");
    double totalPending = 0;

    for (InvoiceDto invoice : pendingInvoices) {
        System.out.println("Factura ID: " + invoice.getId() + " - Monto: " + invoice.getTotalAmount());
        totalPending += invoice.getTotalAmount();
    }

    System.out.println("El total a pagar es: " + totalPending);
    System.out.println("Deseas proceder con el pago? (si/no): ");
    String confirmation = Utils.getReader().nextLine();

    if (confirmation.equalsIgnoreCase("si")) {
        this.service.payPendingInvoices(pendingInvoices);
        System.out.println("Facturas pagadas exitosamente.");
    } else {
        System.out.println("Pago cancelado.");
    }
}
}