package codesquad.domain;

import codesquad.UnAuthorizedException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Issue extends AbstractEntity {

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    private User author;

    public Issue() {

    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment, User author) {
        this.subject = subject;
        this.comment = comment;
        this.author = author;
    }

    public void update(User loginUser, Issue target) {
        if (!matchAuthor(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.subject = target.subject;
        this.comment = target.comment;
        return;
    }

    private boolean matchAuthor(User loginUser) {
        return this.author == loginUser;
    }
}
