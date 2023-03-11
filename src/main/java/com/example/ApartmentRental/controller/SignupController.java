package com.example.ApartmentRental.controller;
import com.example.ApartmentRental.model.services.user.UserService;
import com.example.ApartmentRental.model.user.User;
import com.example.ApartmentRental.model.user.UserFactory;
import com.example.ApartmentRental.model.user.UserTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.ApartmentRental.model.user.UserTypes.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private UserService service;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/create")
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String legalName = request.getParameter("legalName");
        String email = request.getParameter("email");

        String tenantMonad = request.getParameter("tenant");
        String workerMonad = request.getParameter("worker");
        String adminMonad = request.getParameter("administrator");

        if(password.isBlank()) {
            response.sendRedirect("/signup/index");
            return;
        }

        Set<UserTypes> userTypes = Arrays.stream(new String[]{tenantMonad, workerMonad, adminMonad})
            .filter(Objects::nonNull)
            .map(type -> switch (type) {
                default -> Tenant;
                case "worker" -> Employee;
                case "administrator" -> Administrator;
            }).collect(Collectors.toSet());

        if(userTypes.size() == 0) {
            response.sendRedirect("/signup/index");
            return;
        }

        User u = UserFactory.createUser(
            userTypes
            .stream()
            .findAny().get(),
            username,
            password,
            legalName,
            email
        );

        service.save(u);
        userTypes.forEach(type -> insertWithManualQuery(type, u));

        response.sendRedirect("/signup/index");
    }

    @RequestMapping("/index")
    public String loadLoginPage() {
        return "signup";
    }

    @RequestMapping("/test")
    public String t() {
        return "t.html";
    }

    void insertWithManualQuery(UserTypes type, User user) {
        String table = switch (type) {
            case Tenant -> "tenants";
            case Administrator -> "managers";
            case Employee -> "workers";
        };

        String check = user
            .getClass()
            .getSimpleName()
            .toLowerCase();

        try {
            jdbcTemplate.execute("INSERT INTO " + table + " (username) VALUES ('" + user.getUsername() + "')");
        } catch (DataIntegrityViolationException ignored) {}
        if(table.equals("managers"))
            table = "administrator";

        if(!check.equals(table)) {
            jdbcTemplate.execute("INSERT INTO user_role (role, username) VALUES(" + type.ordinal() + ", '" + user.getUsername() + "')");
        }
    }
}
