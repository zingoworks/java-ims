package codesquad.domain;

import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject, comment);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
