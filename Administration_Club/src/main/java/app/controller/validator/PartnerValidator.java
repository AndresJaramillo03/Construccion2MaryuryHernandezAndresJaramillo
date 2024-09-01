package app.controller.validator;

public class PartnerValidator extends CommonsValidator {
        public PartnerValidator () {
            super();
	}
	
	public double validAmount(String amount) throws Exception{
		return super.isValidDouble("El monto del socio", amount);
	}
        
        public void validType(String Type) throws Exception{
		super.isValidString("El tipo de socio no es correcto ", Type);
        } 
}
