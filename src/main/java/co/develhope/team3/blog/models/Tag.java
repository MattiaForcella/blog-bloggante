package co.develhope.team3.blog.models;

import co.develhope.team3.blog.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "tags")
    private List<Article> article;

}
