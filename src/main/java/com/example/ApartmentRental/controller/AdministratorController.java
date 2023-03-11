package com.example.ApartmentRental.controller;

import com.example.ApartmentRental.model.Apartment;
import com.example.ApartmentRental.model.ApartmentBuilding;
import com.example.ApartmentRental.model.Payment;
import com.example.ApartmentRental.model.Repair;
import com.example.ApartmentRental.model.services.apartment_buildings.ApartmentBuildingService;
import com.example.ApartmentRental.model.services.apartments.ApartmentService;
import com.example.ApartmentRental.model.services.payments.PaymentService;
import com.example.ApartmentRental.model.services.repairs.RepairService;
import com.example.ApartmentRental.model.services.user.UserService;
import com.example.ApartmentRental.model.user.Administrator;
import com.example.ApartmentRental.model.user.User;
import com.example.ApartmentRental.model.user.UserTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    ApartmentBuildingService apartmentBuildingService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RepairService repairService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ApartmentService apartmentService;

    @RequestMapping("/buildings")
    ModelAndView displayManagedBuildings(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String username = (String) request.getSession().getAttribute("username");

        List<ApartmentBuilding> buildings =
                apartmentBuildingService
                    .getAll()
                    .stream()
                    .filter(building -> building
                        .getBuildingManager()
                        .getUsername()
                        .equals(username)
                    )
                    .toList();

        mv.addObject("buildings", buildings);
        mv.setViewName("admin_buildings.html");

        return mv;
    }

    @RequestMapping("/buildings/form")
    private String showForm() {
        return "admin_new_building_form.html";
    }

    @RequestMapping("/buildings/add")
    private void addBuilding(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String buildingName = request.getParameter("buildingName");
        String buildingDescription = request.getParameter("buildingDescription");
        String managerUsername = request.getParameter("buildingManagerUsername");
        String buildingPhone = request.getParameter("buildingPhone");
        String address = request.getParameter("address");


        Administrator buildingManager = (Administrator) userService.findByUsername(managerUsername).get();

        ApartmentBuilding newBuilding = new ApartmentBuilding(buildingName, buildingDescription, address, buildingManager, buildingPhone);

        apartmentBuildingService.save(newBuilding);

        response.sendRedirect("/admin/buildings");
    }

    @RequestMapping("/apartments/{id}")
    private ModelAndView showApartments(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();

        List<Apartment> apartments = apartmentService
            .getAll()
            .stream()
            .filter(
                apartment -> apartment
                    .getBuilding()
                    .getBuildingId()
                    .equals(id)
            )
            .toList();


        mv.addObject("apartments", apartments);
        mv.addObject("buildingId", id);

        mv.setViewName("admin_apartments.html");
        return mv;
    }

    @RequestMapping("/buildings/delete/{id}")
    private void deleteBuilding(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {

        apartmentBuildingService.remove(apartmentBuildingService.findById(id).get());

        response.sendRedirect("/admin/buildings");
    }

    @RequestMapping("/apartments/update/form/{id1}/{id2}")
    private ModelAndView loadUpdateForm(@PathVariable("id1") Integer apartmentId, @PathVariable("id2") Integer buildingId) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("admin_update.html");
        mv.addObject("apartmentId", apartmentId);
        mv.addObject("buildingId", buildingId);
        return mv;
    }

    @RequestMapping("/apartments/update/{id1}/{id2}")
    private void updateApartmentTenant(@PathVariable("id1") Integer apartmentId, @PathVariable("id2") Integer buildingId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("tenantUsername");
        jdbcTemplate.update(
                "UPDATE apartments " +
                "SET tenant_username = ? " +
                "WHERE apartment_id = ? ",
                username,
                apartmentId
        );

        response.sendRedirect("/admin/apartments/" + buildingId);
    }


    @RequestMapping("/apartments/delete/{id1}/{id2}")
    private void deleteApartment(@PathVariable("id1") Integer apartmentId, @PathVariable("id2") Integer buildingId, HttpServletResponse response) throws IOException {

        Apartment apt = apartmentService.findById(apartmentId).get();

        apartmentService.remove(apt);

        response.sendRedirect("/admin/apartments/" + buildingId);
    }


    @RequestMapping("/apartments/form/{id}")
    private ModelAndView showApartmentForm(@PathVariable("id") Integer buildingId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin_new_apartment_form.html");
        mv.addObject("buildingId", buildingId);
        return mv;
    }

    @RequestMapping("/apartments/add/{id}")
    private void addApartment(@PathVariable("id") Integer apartmentBuildingId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer apartmentNumber = null;
        Double usableArea = null;
        String tenantUsername = request.getParameter("tenantUsername");

        Integer numberOfRooms = null;

        try {
            numberOfRooms = Integer.valueOf(request.getParameter("numberOfRooms"));
            numberOfRooms = numberOfRooms > 0 ? numberOfRooms : null;
        } catch (Exception ignored) {
        }
        try {
            usableArea = Double.valueOf(request.getParameter("usableArea"));
            usableArea = usableArea > 0 ? usableArea : null;
        } catch (Exception ignored) {
        }
        try {
            apartmentNumber = Integer.valueOf(request.getParameter("apartmentNumber"));
            apartmentNumber = apartmentNumber > 0 ? apartmentNumber : null;
        } catch (Exception ignored) {
        }

        if(Arrays.stream(new Object[]{apartmentNumber, usableArea, numberOfRooms})
                .allMatch(Objects::nonNull)
        ) insertApartmentWithManualQuery(apartmentNumber, numberOfRooms, usableArea, apartmentBuildingId, tenantUsername);
        response.sendRedirect("/admin/apartments/" + apartmentBuildingId);
    }

    @RequestMapping("/payments")
    private ModelAndView showPayments(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String adminUsername = (String) request.getSession().getAttribute("username");

        List<Payment> payments = paymentService.getAdminPayments(adminUsername);

        mv.addObject("payments", payments);
        mv.setViewName("admin_payments.html");
        return mv;
    }

    @RequestMapping("/requests")
    private ModelAndView viewRequests(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String adminUsername = (String) request.getSession().getAttribute("username");

        List<Repair> repairs = repairService
            .getAdminRepairs(adminUsername)
            .stream()
            .filter(
                repair -> repair
                    .getRepairMen()
                    .isEmpty()
            ).toList();

        mv.addObject("repairs", repairs);
        mv.setViewName("admin_requests.html");
        return mv;
    }

    @RequestMapping("/assign/{id}")
    private void assignRepairmen(@PathVariable("id") Integer id, @ModelAttribute("worker1") String worker1, @ModelAttribute("worker2") String worker2, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username1 = request.getParameter("worker1");
        String username2 = request.getParameter("worker2");

        Arrays.stream(new String[]{username1, username2})
            .forEach(username -> jdbcTemplate.update(
            "INSERT INTO workers_history_of_repairs(repair_men_username, history_of_repairs_repair_id) " +
                "VALUES(?, ?)",
                username,
                id
            )
        );

        response.sendRedirect("/admin/requests");
    }

    @RequestMapping("/assign/form/{id}")
    private ModelAndView renderAssignForm(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("requestId", id);
        mv.setViewName("admin_assign_form.html");

        List<User> workers = userService
            .getAll()
            .stream()
            .filter(user -> user
                .getUserRoles()
                .stream()
                .anyMatch(userRole -> userRole
                    .getRole() == UserTypes.Employee
            )).toList();

        mv.addObject("workers", workers);
        return mv;
    }


    @RequestMapping("/payments/form/{id1}/{id2}")
    private ModelAndView showPaymentsForm(@PathVariable("id1") Integer apartmentId, @PathVariable("id2") Integer buildingId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin_payments_form");
        mv.addObject("apartmentId", apartmentId);
        mv.addObject("buildingId", buildingId);

        return mv;
    }

    @RequestMapping("payments/add/{id1}/{id2}")
    private void createPayment(@PathVariable("id1") Integer apartmentId, @PathVariable("id2") Integer buildingId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        BigInteger amount = null;

        try {
            amount = new BigInteger(request.getParameter("amount"));
        }catch (Exception ignored) {
            response.sendRedirect("/admin/apartments/" + buildingId);
            return;
        }
        Apartment apartment = apartmentService.findById(apartmentId).get();

        amount = amount.compareTo(BigInteger.ZERO) > 0 ? amount : null;
        if(amount == null)  {
            response.sendRedirect("/admin/apartments/" + buildingId);
            return;
        }

        jdbcTemplate.update(
        "INSERT INTO payments(amount, emitted_at, apartment_apartment_id, tenant_username) " +
            "VALUES (?, ?, ?, ?) ",
                amount,
                new Date(),
                apartmentId,
                apartment.getTenant().getUsername()
        );

        response.sendRedirect("/admin/apartments/" + buildingId);
    }

    void insertApartmentWithManualQuery(Integer apartmentNumber, Integer numberOfRooms, Double usableArea, Integer apartmentBuildingId, String tenantUsername) {
        jdbcTemplate.execute(
       "INSERT INTO apartments(apartment_number, number_of_rooms, usable_area, building_building_id, tenant_username) " +
            "VALUES (" + apartmentNumber + ", " + numberOfRooms + ", " + usableArea + ", " + apartmentBuildingId + ", '" + tenantUsername + "')"
        );
    }
}
