package co.develhope.team3.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long createdOn;

    private String content;

    private Integer flag;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    //@JsonManagedReference
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    //@JsonManagedReference
    private User user;








}
