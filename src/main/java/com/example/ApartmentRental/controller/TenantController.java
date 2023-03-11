package com.example.ApartmentRental.controller;

import com.example.ApartmentRental.model.Apartment;
import com.example.ApartmentRental.model.Payment;
import com.example.ApartmentRental.model.services.apartments.ApartmentService;
import com.example.ApartmentRental.model.services.payments.PaymentService;
import com.example.ApartmentRental.model.services.repairs.RepairService;
import com.example.ApartmentRental.model.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RepairService repairService;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/all")
    private ModelAndView showAllApartments(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String username = (String) request.getSession().getAttribute("username");

        List<Apartment> rentedApartments = apartmentService
            .getAll()
            .stream()
            .filter(apartment -> apartment
                .getTenant()
                .getUsername()
                .equals(username))
            .toList();

        mv.setViewName("tenant_apartments.html");
        mv.addObject("rentedApartments", rentedApartments);

        return mv;
    }

    @RequestMapping("/request/form/{id}")
    private ModelAndView renderForm(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("tenant_form.html");
        mv.addObject("apartmentId", id);

        return mv;
    }

    @RequestMapping("/request/{id}")
    private void requestRepair(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String repairDescription = request.getParameter("description");
        String requestedBy= (String) request.getSession().getAttribute("username");

        jdbcTemplate.update(
            "INSERT INTO repairs(description, time_of_request, apartment_apartment_id, requested_by) " +
                    "VALUES (?, ?, ?, ?)",
                repairDescription,
                new Date(),
                id,
                requestedBy
        );

        response.sendRedirect("/tenant/all");
    }

    @RequestMapping("/pay/{id}")
    private void makePayment(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getSession().getAttribute("username");

        jdbcTemplate.update(
            "UPDATE payments set paid_at = ?, tenant_username = ? " +
            "WHERE apartment_apartment_id = ? AND paid_at is null",
            new Date(),
            username,
            id
        );
        response.sendRedirect("/tenant/all");
    }

    @RequestMapping("/payments")
    private ModelAndView showAllPayments(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String username = (String) request.getSession().getAttribute("username");

        List<Payment> paymentHistory = paymentService
            .getAll()
            .stream()
            .filter(payment -> {
                var userMonad = payment.getTenant();

                return userMonad != null && userMonad
                        .getUsername()
                        .equals(username);
            })
            .toList();

        mv.setViewName("tenant_payment_history.html");
        mv.addObject("paymentHistory", paymentHistory);

        return mv;
    }
}
