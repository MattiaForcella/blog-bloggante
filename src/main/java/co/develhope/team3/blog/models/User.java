package co.develhope.team3.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20, message = "il campo username deve essere compreso tra i 3 e i 20 caratteri")
    @NotBlank(message = "il campo username non puo essere vuoto")
    private String username;

    //TODO quando in futurò verrà implementata la classe relativa alla registrazione, ricordarsi di aggiungere la codifica dalla libreria di Pasquale (giustifica lenght = 150)
    @Column(length = 150)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String about;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonBackReference
    private List<Article> articles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    //@JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {

        return String.format("%s email: %s --- ID: %d", username, email, id);
    }


}
