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

    @Column(name = "article_title", nullable = false)
    private String title;

    @Column(nullable = false, length = 100000000)
    private String content;

    @Column(nullable = false)
    private Boolean isNews;

    private Date createdOn;

    private Date updateOn;

    private String imageName;

    @ManyToOne
    //@JsonMAnagedReference
    private User user = new User();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "article_id",
                joinColumns = @JoinColumn(name = "articles", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "tag", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    //@JsonBackReference
    private List<Comment> comments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}
