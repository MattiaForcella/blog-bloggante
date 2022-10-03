package co.develhope.team3.blog.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    //Codice aggiunto per rispecchiare i vincoli di partecipazione
    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "roles")
    private List<User> users = new ArrayList<>();


    public void setUser(User user) {
        users.add(user);
    }
}
