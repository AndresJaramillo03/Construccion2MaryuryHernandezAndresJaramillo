package app.controller;

import app.controller.request.CreateGuestRequest;
import app.controller.validator.InvoiceValidator;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
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
        
        @PostMapping("/guest")
        
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
        
        public void InvoiceDetailDto()throws Exception {
            System.out.println("Ingrese el numero de elementos: ");
            int items = invoiceValidator.validItem(Utils.getReader().nextLine());
            List<InvoiceDetailDto> invoices = new ArrayList<InvoiceDetailDto>();
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setId(items);
            
            int total = 0;
            for (int i = 0; i < items; i++){
                InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
                invoiceDetailDto.setInvoiceId(invoiceDto);
                invoiceDetailDto.setItem((i + 1));
                System.out.println("Ingrese el monto del item " + invoiceDetailDto.getItem());
                invoiceDetailDto.setAmount(invoiceValidator.validAmount(Utils.getReader().nextLine()));
                total += invoiceDetailDto.getAmount();
                invoices.add(invoiceDetailDto);
                   
            }
            invoiceDto.setTotalAmount(total);
            this.service.createInvoice(invoices);            
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