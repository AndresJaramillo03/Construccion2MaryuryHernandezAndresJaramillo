package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
    @JoinColumn (name = "personid")
    @OneToOne
    private Person userId; 
    @JoinColumn (name = "partnerid")
    @OneToOne
    private Partner partnerId;
    @Column (name = "creationdate")
    private LocalDateTime creationDate;
    @Column (name = "amount")
    private double totalAmount;
    @Column (name = "status")
    private String status; 
}