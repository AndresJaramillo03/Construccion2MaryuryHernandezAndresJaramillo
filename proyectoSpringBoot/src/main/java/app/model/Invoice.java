package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="invoice")

public class Invoice {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name = "id")
    private long id; 
    @Column (name = "userid")
    private User userId; 
    @Column (name = "parterid")
    private Partner partnerId;
    @Column (name = "creatidate")
    private Date creationDate;
    @Column (name = "totalamount")
    private double totalAmount;
    @Column (name = "status")
    private boolean status; 

}
