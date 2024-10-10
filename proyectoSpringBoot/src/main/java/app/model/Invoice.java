package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @JoinColumn (name = "person")
    @OneToOne
    private Person userId; 
    @JoinColumn (name = "parterid")
    @OneToOne
    private Partner partnerId;
    @Column (name = "creatidate")
    private Date creationDate;
    @Column (name = "totalamount")
    private double totalAmount;
    @Column (name = "status")
    private String status; 

}
