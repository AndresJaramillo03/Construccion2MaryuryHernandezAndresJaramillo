package app;

import app.controller.ControllerInterface;
import app.controller.LoginController;


public class AdministrationClub {
        public static void main(String[] args) throws Exception {
		ControllerInterface controller = new LoginController();
		try {
			controller.session();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}