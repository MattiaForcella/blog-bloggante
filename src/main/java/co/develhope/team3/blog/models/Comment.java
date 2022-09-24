package co.develhope.team3.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long createdOn;


    private String content;

    private Integer flagged;

    @ManyToOne
    //@JsonManagedReference
    private Article article;

    @ManyToOne
    @JoinColumn(name = "articles",referencedColumnName = "id")
    //@JsonManagedReference
    private User user;








}
