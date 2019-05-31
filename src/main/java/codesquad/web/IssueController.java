package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/form")
    public String issueForm(@LoginUser User loginUser) {
        return "/issue/form";
    }
}
