package co.develhope.team3.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table
@Data
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=5, message = "il titolo deve contenere al minimo 5 caratteri")
    @NotBlank(message = "il titolo non puo essere vuoto")
    @Column(name = "article_title", nullable = false)
    private String title;

    @NotBlank(message = "il contenuto non puo essere vuoto")
    @Column(nullable = false, length = 100000000)
    private String content;

    @Column(nullable = false)
    @NotBlank(message = "è obbligatorio impostare se l'articolo dev'essere tra le news")
    private Boolean isNews;

    private Date createdOn;

    private Date updateOn;

    private String imageName;

    @ManyToOne
    //@JsonMAnagedReference
    private User user = new User();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    //@JsonBackReference
    private List<Comment> comments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}
