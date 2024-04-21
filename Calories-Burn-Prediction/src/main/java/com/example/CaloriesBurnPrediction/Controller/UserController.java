package com.example.CaloriesBurnPrediction.Controller;

import java.security.Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.CaloriesBurnPrediction.Model.User;
import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Repository.UserRepo;
import com.example.CaloriesBurnPrediction.Service.AppointmentService;
import com.example.CaloriesBurnPrediction.Service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@CrossOrigin("*")
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;

	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@GetMapping("/appointment")
	public String Appointment(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("users", user);
		}
		return "/appointment";
	}
	
	
	
	@PostMapping("/saveAppointment")
	public String saveAppointment(@ModelAttribute Appointment appointment, HttpSession session) {
	    try {
	        // Save the appointment
	        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
	        
	        // Log an INFO message for successful appointment saving
	        logger.info("Appointment saved successfully: " + savedAppointment);

	        // Redirect to the appropriate page
	        return "redirect:/";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while saving appointment", e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	
	
	
	
}
