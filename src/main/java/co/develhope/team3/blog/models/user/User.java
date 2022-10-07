package co.develhope.team3.blog.models.user;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonBackReference
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String about;

    private Boolean ban = false;

    private String activationCode;
    private Boolean isActiveUser = false;



    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonBackReference
    private List<Article> articles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    //@JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Column
    private List<Role> roles = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email= email;
        this.password = password;
    }

    public void setRole(Role role){
        this.roles.add(role);
    }
    @Override
    public String toString() {

        return String.format("%s email: %s --- ID: %d", username, email, id);
    }


    public void removeRole(Role role) {
        roles.remove(role);
    }
}
