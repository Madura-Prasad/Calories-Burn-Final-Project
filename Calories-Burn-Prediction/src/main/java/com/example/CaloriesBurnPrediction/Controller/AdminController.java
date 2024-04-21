package com.example.CaloriesBurnPrediction.Controller;

import java.security.Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.CaloriesBurnPrediction.Model.User;
import com.example.CaloriesBurnPrediction.Repository.UserRepo;
import com.example.CaloriesBurnPrediction.Service.AppointmentService;
import com.example.CaloriesBurnPrediction.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	private static final Logger logger = LogManager.getLogger(AdminController.class);
	
	@GetMapping("/")
	public String Dashboard(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("users", user);
		}
		model.addAttribute("user", appointmentService.getAllUser());
		return "/AdminPanel/index";
	}
	
	
	@GetMapping("/addUser")
	public String addUser(Model model, Principal principal) {
	    boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user = userRepo.findByEmail(email);
	        model.addAttribute("users", user);
	    }
	    return "AdminPanel/addUser";
	}
	
	
	@PostMapping("/submits")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
	    try {
	        // Your code to save the user
	        User savedUser = appointmentService.saveUser(user);
	        
	        // Log an INFO message for successful user saving
	        logger.info("User saved successfully: " + savedUser);

	        // Redirect to the appropriate page
	        return "redirect:/admin/";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while saving user", e);
	        
	        // Redirect to an error page
	        return "redirect:/errorPage";
	    }
	}


	@GetMapping("/editUser")
	public String editUser(Model model, Principal principal) {
	    try {
	        boolean isLoggedIn = principal != null;
	        model.addAttribute("isLoggedIn", isLoggedIn);
	        if (isLoggedIn) {
	            String email = principal.getName();
	            User user = userRepo.findByEmail(email);
	            model.addAttribute("users", user);
	        }
	        // Log an INFO message for successfully accessing the edit user page
	        logger.info("Successfully accessed the edit user page");

	        return "AdminPanel/editUser";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while accessing the edit user page", e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}


	@GetMapping("/editUser/{id}")
	public String editSUser(@PathVariable Long id, Model model) {
	    try {
	        // Attempt to retrieve the user by ID
	        User user = appointmentService.getUserByID(id);
	        
	        // Add the user to the model
	        model.addAttribute("user", user);
	        
	        // Log an INFO message for successfully accessing the edit user page
	        logger.info("Successfully accessed the edit user page for user with ID: " + id);

	        return "AdminPanel/editUser";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while accessing the edit user page for user with ID: " + id, e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}


	@PostMapping("/editUser/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
	    try {
	        // Retrieve the existing user by ID
	        User existingUser = appointmentService.getUserByID(id);
	        
	        // Update the existing user's details
	        existingUser.setId(id);
	        existingUser.setName(user.getName());
	        existingUser.setEmail(user.getEmail());
	        existingUser.setMobile(user.getMobile());
	        
	        // Encrypt the new password
	        String newPassword = user.getPassword();
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String hashedPassword = encoder.encode(newPassword);
	        existingUser.setPassword(hashedPassword);
	        
	        // Update the user in the database
	        appointmentService.updateUser(existingUser);
	        
	        // Log an INFO message for successful user update
	        logger.info("User with ID " + id + " updated successfully");

	        return "redirect:/admin/";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while updating user with ID: " + id, e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id) {
	    try {
	        // Attempt to delete the user by ID
	        appointmentService.deleteUser(id);
	        
	        // Log an INFO message for successful user deletion
	        logger.info("User with ID " + id + " deleted successfully");

	        return "redirect:/admin/";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while deleting user with ID: " + id, e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	

	
	@GetMapping("/manageAppointment")
	public String Appointment(Model model, Principal principal) {
	    try {
	        // Check if the user is logged in
	        boolean isLoggedIn = principal != null;

	        // Add attribute for isLoggedIn to the model
	        model.addAttribute("isLoggedIn", isLoggedIn);

	        // If user is logged in, add user information to the model
	        if (isLoggedIn) {
	            String email = principal.getName();
	            User user = userRepo.findByEmail(email);
	            model.addAttribute("users", user);
	        }
	        
	        // Add appointments to the model
	        model.addAttribute("appointment", userService.getAllAppointments());
	        
	        // Log an INFO message for successfully accessing the manage appointment page
	        logger.info("Successfully accessed the manage appointment page");

	        return "/AdminPanel/manageAppointment";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while accessing the manage appointment page", e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	
	@GetMapping("/deleteAppointment/{id}")
	public String deleteAppointment(@PathVariable Long id) {
	    try {
	        // Attempt to delete the appointment by ID
	        appointmentService.deleteAppointment(id);
	        
	        // Log an INFO message for successful appointment deletion
	        logger.info("Appointment with ID " + id + " deleted successfully");

	        return "redirect:/admin/manageAppointment";
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while deleting appointment with ID: " + id, e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	

}
