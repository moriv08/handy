package ru.handy.handy.controllers;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    private final PrincipalUserDetailsService principalUserDetailsService;

    public LoginController(PrincipalUserDetailsService principalUserDetailsService) {
        this.principalUserDetailsService = principalUserDetailsService;
    }

    @GetMapping
    public String indexPage(Principal principal){

        String role = principalUserDetailsService.findPrincipalEntityByUsername(principal.getName()).getRole();

        if (role.length() > 0)
            return "redirect:/api/home";

        return "login";
    }

    @GetMapping("/login")
    public String loginPage(Device device){

        if (device.isMobile())
            return "mobile/m_login";

        return "login";
    }
}
