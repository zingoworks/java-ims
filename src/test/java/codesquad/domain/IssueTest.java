package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

public class IssueTest {

    @Test
    public void update_owner() {
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() {

    }
}