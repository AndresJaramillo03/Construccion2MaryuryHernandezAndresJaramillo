package app.controller;

import app.controller.request.CreateUserRequest;
import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.controller.validator.PartnerValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.service.ClubService;
import app.service.interfaces.AdminService;
import app.dto.PartnerDto;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
@NoArgsConstructor

public class AdminController implements ControllerInterface {

    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private PartnerValidator partnerValidator;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminService service;
    private static final String MENU = "ingrese la opcion que desea \n 1. para crear socio \n 2. para ver factura Club \n 3. para ver factura Socio \n 4. para ver factura Invitado \n 5. para aprobar promocion \n 6. para cerrar sesion \n";

    @Override
    public void session() throws Exception {

    }

    @PostMapping("/partner")
    private ResponseEntity createPartner(@RequestBody CreateUserRequest request) throws Exception {
        try {
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
            userDto.setUserName(username);
            userDto.setPassword(password);
            userDto.setRole("partner");
            userDto.setPersonId(personDto);
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setUserId(userDto);
            partnerDto.setAmount(50000);
            partnerDto.setType("regular");
            partnerDto.setCreationDate(new Timestamp(System.currentTimeMillis()));
            this.service.createPartner(partnerDto);
            return new ResponseEntity("El usuario ha sido creado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
