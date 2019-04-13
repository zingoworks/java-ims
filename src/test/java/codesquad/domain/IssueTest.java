package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static codesquad.domain.UserTest.ADMIN;
import static codesquad.domain.UserTest.ZINGO;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class IssueTest {
    public static Issue ADMIN_ISSUE = new Issue("admin subject", "admin comment", ADMIN);

    @Test
    public void update_owner() {
        Issue origin = ADMIN_ISSUE;
        User loginUser = ADMIN;
        Issue target = new Issue("target subject", "target comment");
        origin.update(loginUser, target);

        assertThat(origin.getSubject()).isEqualTo(target.getSubject());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() {
        Issue origin = ADMIN_ISSUE;
        User loginUser = ZINGO;
        Issue target = new Issue("target subject", "target comment");
        origin.update(loginUser, target);
    }
}