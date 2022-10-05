package co.develhope.team3.blog.models.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleName name;

    /*@TODO
    //Codice aggiunto per rispecchiare i vincoli di partecipazione
    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "roles")
    private List<User> users = new ArrayList<>();




    public void setUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

     */
}
