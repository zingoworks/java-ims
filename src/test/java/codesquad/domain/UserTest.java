package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.exception.InvalidPasswordException;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UserTest {
    public static final User ADMIN = new User(1L, "admin", "password", "adminName");
    public static final User ZINGO = new User(1L, "zingo", "password", "zingoName");

    @Test
    public void update_owner() throws Exception {
        User origin = ADMIN;
        User loginUser = origin;

        User target = new User("admin", "password", "targetName");
        origin.update(loginUser, target);

        assertThat(origin.getName()).isEqualTo(target.getName());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = ADMIN;
        User loginUser = ZINGO;
        User target = new User("admin", "password", "targetName");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = ADMIN;
        User target = new User("admin", "password", "targetName");
        origin.update(origin, target);

        assertThat(origin.getName()).isEqualTo(target.getName());
    }

    @Test(expected = InvalidPasswordException.class)
    public void update_mismatch_password() {
        User origin = ADMIN;
        User target = new User("admin", "wrongPassword", "targetName");
        origin.update(origin, target);
    }

    @Test
    public void match_password() throws Exception {
        User user = ADMIN;
        assertTrue(user.matchPassword(user.getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        User user = ADMIN;
        assertFalse(user.matchPassword(user.getPassword() + "2"));
    }
}
