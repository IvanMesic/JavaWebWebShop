package hr.meske.javaWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User() {
    }

    private String name;
    private String password;
    private String privilege;

    // Custom constructor for initialization
    public User(String name, String password, String privilege) {
        this.name = name;
        this.password = password;
        this.privilege = privilege;
    }
}
