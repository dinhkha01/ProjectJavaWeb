package ra.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("userLogin", "abc");
        return "home"; // Trả về view home.html
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userLogin"); // Xóa thông tin đăng nhập khỏi session
        return "redirect:/"; // Chuyển hướng về trang đăng nhập
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "dashboard"; // Trả về view dashboard.html nếu đã đăng nhập
    }

}
