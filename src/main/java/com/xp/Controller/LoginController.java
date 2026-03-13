package com.xp.Controller;

import com.xp.Model.DTOs.EmployeeCredentials;
import com.xp.Service.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public Boolean login(@RequestBody EmployeeCredentials employeeCredentials) {
        return adminServiceImpl.login(employeeCredentials.username(), employeeCredentials.password());
    }

    @GetMapping("/logout")
    public String logout() {
        return "login-page";
    }
}
