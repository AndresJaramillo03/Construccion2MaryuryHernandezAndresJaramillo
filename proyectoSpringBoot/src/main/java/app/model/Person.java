package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="person")

public class Person {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name= "id")
    private long id;
    @Column (name="document")
    private long cedula;
    @Column (name="name")
    private String name;
    @Column (name="cellphone")
    private long cellPhone;
}