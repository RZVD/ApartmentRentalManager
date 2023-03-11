package com.example.ApartmentRental.controller;

import com.example.ApartmentRental.model.Repair;
import com.example.ApartmentRental.model.services.repairs.RepairService;
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
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    RepairService repairService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/repairs")
    private ModelAndView showAllRepairs(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String username = (String) request.getSession().getAttribute("username");
        List<Repair> repairs = repairService.getWorkerRepairs(username);

        mv.addObject("repairs", repairs);
        mv.setViewName("worker_repairs.html");

        return mv;
    }

    @RequestMapping("/complete/{id}")
    private void markAsComplete(@PathVariable("id") Integer requestId, HttpServletResponse response) throws IOException {
        jdbcTemplate.update(
        "UPDATE repairs SET time_of_repair = ? " +
             "WHERE repairs.repair_id = ? ",
             new Date(),
             requestId
        );

        response.sendRedirect("/worker/repairs");
    }
}
