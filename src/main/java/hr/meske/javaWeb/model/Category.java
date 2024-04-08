package hr.meske.javaWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Category() {
    }

    public Category(String name, String description) {
        this.name = description;
        this.description = description;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}