package co.develhope.team3.blog.models;

import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.models.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(nullable = false, name = "is_news")
    private Boolean isNews;

    @Column
    private Date createdAt;
    @Column
    private Date updateOn;

    private String imageName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonManagedReference
    private User user = new User();

    @Column
    private String createdBy = user.getUsername();


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "article_tag",
                joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    //@JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
