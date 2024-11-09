package app.controller;

import app.controller.request.CreateGuestRequest;
import app.controller.request.CreateInvoiceDetailRequest;
import app.controller.validator.InvoiceValidator;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
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
import org.springframework.web.bind.annotation.RequestHeader;


@Controller
@NoArgsConstructor
@Getter
@Setter

public class GuestController implements ControllerInterface {
        @Autowired
        private PersonValidator personValidator;
        @Autowired
	private UserValidator userValidator;
        @Autowired
        private ClubService service;
        @Autowired
        private InvoiceValidator invoiceValidator;
        
    
    	@Override
	public void session() throws Exception {
	}
        
        @PostMapping("/guest/converPartner")
        
        private ResponseEntity convertPartner(@RequestBody CreateGuestRequest request) throws Exception {
           try{
            UserDto currentUser = ClubService.user;

            if (currentUser == null) {
                throw new Exception("No hay un usuario invitado registrado en la sesion actual");
            }

            currentUser.setRole("Socio");

            System.out.println("El usuario ha sido convertido a socio exitosamente");
              return new ResponseEntity("El usuario ha sido convertido a socio exitosamente", HttpStatus.OK);
           } catch (Exception e){
               return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
           }
        }
        
 
    @PostMapping("guest/makeConsumption")
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
                return new ResponseEntity("ALGO FALLO: " + e,HttpStatus.BAD_REQUEST);
            }
            
        }
}