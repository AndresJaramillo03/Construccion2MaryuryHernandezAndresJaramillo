package app.controller.validator;


public class InvoiceValidator extends CommonsValidator {
    	public InvoiceValidator() {
		super();
	}
	
	public void validItems(String items)throws Exception {
		super.isValidString("items de la factura", items);
	}
	
	public double validTotalAmonunt(String totalAmount) throws Exception{
                return super.isValidDouble("El monto de la factura", totalAmount);
	}
}
