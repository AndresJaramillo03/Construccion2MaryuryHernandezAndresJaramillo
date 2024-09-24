package app.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PartnerDto {
    private long id;
    private UserDto userId;
    private double amount;
    private String type; 
    private Timestamp creationDate;    
}
