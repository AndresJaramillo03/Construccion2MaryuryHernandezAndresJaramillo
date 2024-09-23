package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name= "id")
    private long id;
    @Column (name= "userName")
    private String userName;
    @Column (name= "password")
    private String password;
    @Column (name= "role")
    private String role;
    @JoinColumn (name= "personid")
    @OneToOne
    private Person personId;
    
}
