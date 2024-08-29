package app.controller.validator;

public class PartnerValidator extends CommonsValidator {
        public PartnerValidator () {
            super();
	}
	
	public void validAmount(String amount) throws Exception{
		super.isValidString("El valor no es correcto ", amount);
	}
        
        public void validType(String Type) throws Exception{
		super.isValidString("El tipo de socio no es correcto ", Type);
        } 
}
