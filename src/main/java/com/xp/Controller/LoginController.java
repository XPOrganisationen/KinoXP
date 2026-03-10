package com.xp.Controller;

import com.xp.Service.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

//Jeg ved ikke hvordan man skal tilgå en html side via en form, hvis man ikke bruger thymeleaf.
@RestController
@RequestMapping("/api")
public class LoginController {
    private final EmployeeServiceImpl adminServiceImpl;

    public LoginController(EmployeeServiceImpl adminServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login-page";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        if (adminServiceImpl.login(username, password)) {
            return "admin-page";
        }
        return "login-page";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login-page";
    }
}
