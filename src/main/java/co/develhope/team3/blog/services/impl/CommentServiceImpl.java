package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl {

    @Autowired
    private CommentRepository commentRepository;
    public boolean deleteComment(Long id) {
        commentRepository.deleteById(id);
        return true;
    }

    public void comment(Comment comment) {
        commentRepository.save(comment);
    }
}
