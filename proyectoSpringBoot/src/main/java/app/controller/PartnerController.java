package app.controller;

import app.controller.request.CreateInvoiceDetailRequest;
import app.controller.request.CreateUserRequest;
import app.controller.request.UpdateGuestRequest;
import app.controller.request.UpdatePartnerRequest;
import app.controller.response.InvoiceResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    
    @PostMapping("partner/activateGuest")
    private ResponseEntity activateGuest(@RequestBody UpdateGuestRequest request) throws Exception {
        try{
            System.out.println("Ingrese el id del invitado que desea activar:");
            
            long id = guestValidator.validId(request.getId());
            GuestDto guestDto = new GuestDto();
            guestDto.setId(id);
            service.activateGuest(guestDto);
            return new ResponseEntity("El invitado ha sido activado exitosamente",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("partner/desactivateGuest")
    private ResponseEntity deactivateGuest(@RequestBody UpdateGuestRequest request) throws Exception {
        try{
            System.out.println("Ingrese el id del invitado que desea desactivar:");
            long id = guestValidator.validId(request.getId());

            GuestDto guestDto = new GuestDto();
            guestDto.setId(id);

            service.inactivateGuest(guestDto);
            
            return new ResponseEntity("El invitado ha sido desactivado exitosamente",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e,HttpStatus.BAD_REQUEST);
        }
    }
     
    @PostMapping("partner/requestUnsubscribe")
    private ResponseEntity requestUnsubscribe(@RequestHeader("username") String username) throws Exception {
        try {
            UserDto userDto = new UserDto();
            userValidator.validUserName(username);
            userDto.setUserName(username);
            this.service.requestUnsubscribe(userDto);
            return new ResponseEntity("Se te ha dado de baja con éxito! Cerrando sesión.",HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("partner/rechargeFunds")
    private ResponseEntity rechargeFunds(@RequestHeader("username") String username,@RequestBody UpdatePartnerRequest request) throws Exception{
        try{
            UserDto userDto = new UserDto();
            userValidator.validUserName(username);
            userDto.setUserName(username);
            System.out.println("Ingrese el monto que desea recargar");
            double amount = partnerValidator.validAmount(request.getAmount());
            this.service.rechargeFunds(amount,userDto);
            
            return new ResponseEntity("Recarga realizada con exito", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
   @PostMapping("partner/makeConsumption")
    private ResponseEntity makeConsumption (@RequestHeader("username") String username, @RequestBody CreateInvoiceDetailRequest request) throws Exception {
            try{
                List<InvoiceDetailDto> invoiceDtoList = new ArrayList<>();

                System.out.println ("Ingrese el consumo que desea: ");
                    System.out.println("Ingrese su producto: ");
                    String product = request.getProduct();
                    invoiceValidator.validProduct(product);
                    System.out.println("Ingrese el valor de su producto: ");
                    double productCost = invoiceValidator.validAmount(request.getProductCost());
                    System.out.println("Ingrese la cantidad: ");
                    int quantity = invoiceValidator.validItem(request.getQuantity());

                    InvoiceDto invoiceDto = new InvoiceDto();
                    invoiceDto.setCreationDate(LocalDateTime.now());
                    invoiceDto.setStatus("Pendiente");
                    invoiceDto.setTotalAmount(productCost*quantity);

                    PartnerDto partner = new PartnerDto();
                    UserDto userPartner = new UserDto();
                    userPartner.setUserName(username);
                    partner.setUserId(userPartner);
                    invoiceDto.setPartnerId(partner);
                    
                    String document = request.getDocument();
                    PersonDto person = new PersonDto();
                    
                    person.setCedula(invoiceValidator.validDocument(document));
                    invoiceDto.setUserId(person);
                    
                    InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
                    invoiceDetailDto.setDescription(product);
                    invoiceDetailDto.setAmount(productCost);
                    invoiceDetailDto.setItem(quantity);
                    invoiceDetailDto.setInvoiceId(invoiceDto);
                    invoiceDtoList.add(invoiceDetailDto);
                this.service.createInvoice(invoiceDtoList);
                return new ResponseEntity("Compra realizada",HttpStatus.OK);
            } catch(Exception e){
                return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
            
        }
    
    @PostMapping("partner/payInvoices")
    private ResponseEntity payInvoices(@RequestHeader("username") String username, @RequestBody UpdatePartnerRequest request) throws Exception {
        try{
            UserDto userDto = new UserDto();
            userValidator.validUserName(username);
            userDto.setUserName(username);
            List<InvoiceDto> pendingInvoices = this.service.getPendingInvoices(userDto);
            if (pendingInvoices.isEmpty()) {
               throw new Exception("No tienes facturas pendientes por pagar.");
            }
            System.out.println("Tienes las siguientes facturas pendientes:");
            double totalPending = 0;

            for (InvoiceDto invoice : pendingInvoices) {
                System.out.println("Factura ID: " + invoice.getId() + " - Monto: " + invoice.getTotalAmount());
                totalPending += invoice.getTotalAmount();
            }

            System.out.println("El total a pagar es: " + totalPending);
            System.out.println("Deseas proceder con el pago? (si/no): ");
            String confirmation = request.getConfirmation();

            if (confirmation.equalsIgnoreCase("si")) {
                System.out.println(userDto.getUserName());
                boolean test = this.service.payPendingInvoices(pendingInvoices,userDto);
                if(test){
                    
                return new ResponseEntity("Facturas pagadas exitosamente.",HttpStatus.OK);
                }
                else{
                    return new ResponseEntity("No tienes el monto para pagar alguna factura.",HttpStatus.OK);
                }
            } else {
                return new ResponseEntity("Pago cancelado.",HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
}