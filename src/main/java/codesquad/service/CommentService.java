package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.domain.issue.Issue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment create(User loginUser, long issueId, String body) {
        Issue issue = issueService.findById(issueId);
        Comment comment = new Comment(loginUser, issue, body);
        return add(comment);
    }
}
