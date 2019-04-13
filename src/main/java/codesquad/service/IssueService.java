package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class IssueService {

    private IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    public Issue update(@LoginUser User loginUser, long id, IssueDto updatedIssue) {
        Issue original = issueRepository.findById(id).orElseThrow(NoResultException::new);
        original.update(loginUser, updatedIssue._toIssue());
        return issueRepository.save(original);
    }
}
