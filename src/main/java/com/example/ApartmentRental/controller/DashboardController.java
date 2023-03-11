package com.example.ApartmentRental.controller;

import com.example.ApartmentRental.model.services.user.UserService;
import com.example.ApartmentRental.model.user.User;
import com.example.ApartmentRental.model.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping("/dashboard")
@Controller
public class DashboardController {

    final static HtmlButton[] tenantsOptions = {
            new HtmlButton("View rented Apartment(Tenant)", "/tenant/all"),
            new HtmlButton("View payment history (Tenant)", "/tenant/payments")
    };
    final static HtmlButton[] workerOptions = {
            new HtmlButton("View pending repairs", "/worker/repairs")
    };
    final static HtmlButton[] adminOptions = {
            new HtmlButton("View managed buildings", "/admin/buildings"),
            new HtmlButton("View payment history", "/admin/payments"),
            new HtmlButton("View pending requests (Admin)", "/admin/requests")
    };
    @Autowired
    UserService userService;


    @RequestMapping("/index")
    public ModelAndView renderDashboard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        User user = userService.findByUsername((String) request.getSession().getAttribute("username")).get();
        Set<UserRole> roles = user.getUserRoles();
        List<HtmlButton> options = new ArrayList<>(0);
        for (UserRole userRole : roles) {
            switch (userRole.getRole()) {
                case Tenant -> options.addAll(List.of(tenantsOptions));
                case Administrator -> options.addAll(List.of(adminOptions));
                case Employee -> options.addAll(List.of(workerOptions));
            }
        }

        mv.addObject("buttons", options);
        mv.setViewName("dashboard.html");
        return mv;
    }

}
