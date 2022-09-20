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

    @Column(nullable = false, unique = true)
    @Email(message = "email non corretta")
    @NotBlank(message = "il campo email non puo essere vuoto")
    private String email;

    private String about;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonBackReference
    private List<Article> articles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    //@JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {

        return String.format("%s email: %s --- ID: %d", username, email, id);
    }


}
