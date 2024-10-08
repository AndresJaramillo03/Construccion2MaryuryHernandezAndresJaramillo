package app.controller.validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@Component  

public class InvoiceValidator extends CommonsValidator {
    	
	
	public void validItems(String items)throws Exception {
		super.isValidString("items de la factura", items);
	}
	
	public double validTotalAmonunt(String totalAmount) throws Exception{
                return super.isValidDouble("El monto de la factura", totalAmount);
	}
        public int validItem(String item) throws Exception {
            return super.isValidInteger("numero de items de la factura", item);
        }
        public double validAmount(String item) throws Exception {
            return super.isValidDouble("valor del item", item);
        }
}
