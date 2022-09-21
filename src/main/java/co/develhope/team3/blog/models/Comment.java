package co.develhope.team3.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdOn;


    private String content;

    @ManyToOne
    //@JsonManagedReference
    private Article article;

    @ManyToOne
    @JoinColumn(name = "articles",referencedColumnName = "id")
    //@JsonManagedReference
    private User user;






}
