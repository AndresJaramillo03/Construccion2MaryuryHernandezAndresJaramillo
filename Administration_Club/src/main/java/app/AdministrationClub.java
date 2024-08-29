package app;

public class AdministrationClub {
        public static void main(String[] args) throws Exception {
		ControllerInterface controller = new LoginController();
		try {
			controller.session();
			//MYSQLConnection.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
