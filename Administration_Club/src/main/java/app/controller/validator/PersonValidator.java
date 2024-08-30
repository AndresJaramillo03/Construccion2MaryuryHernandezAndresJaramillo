package app.controller.validator;

public class PersonValidator extends CommonsValidator {

        public PersonValidator(){
            super();
	}
	
	public void validName(String name) throws Exception{
		super.isValidString("el nombre de la persona ", name);
	}
	
	public long validCedula(String cedula) throws Exception{
		return super.isValidLong("la cedula de la persona ", cedula);
	}
	
	public long validCellphone(String cellphone) throws Exception{
		return super.isValidLong("el telefono de la persona ", cellphone); 
	}     
}
