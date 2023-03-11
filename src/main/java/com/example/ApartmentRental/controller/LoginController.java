package com.example.ApartmentRental.controller;

import com.example.ApartmentRental.model.CrudRepositories.UserRepository;
import com.example.ApartmentRental.model.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/index")
    public String loadPage() {
        return "login";
    }

    @RequestMapping("/check")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String providedPassword = request.getParameter("password");

        Optional<User> u = userRepository.findById(username);

        String pathAfterAuth = "";

        if (u.isPresent()) {
            String password = u.get().getPassword();
            if (providedPassword.equals(password)) {
                request.getSession().setAttribute("username", u.get().getUsername());
                pathAfterAuth = "/dashboard/index";
            } else pathAfterAuth = "/login/index";

        } else pathAfterAuth = "/login/index";

        response.sendRedirect(pathAfterAuth);
    }
}