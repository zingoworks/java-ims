package codesquad.web;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpSession;

import codesquad.UnAuthenticationException;
import codesquad.security.HttpSessionUtils;
import com.sun.deploy.net.HttpUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/join";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        log.info("user create called");
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) throws UnAuthenticationException {
        log.info("users/login called");
        User user = userService.login(userId, password);
        if (user != null) {
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            log.info("로그인 성공");
            log.info("user info : {}", user.toString());
            return "redirect:/";
        }
        throw new UnAuthenticationException("로그인이 실패했습니다.");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        log.info("logout 성공");
        return "redirect:/";
    }

}
