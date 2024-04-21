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

import com.example.CaloriesBurnPrediction.Model.User;
import com.example.CaloriesBurnPrediction.Repository.UserRepo;
import com.example.CaloriesBurnPrediction.Service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@CrossOrigin("*")
public class FrontPageController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	
	private static final Logger logger = LogManager.getLogger(FrontPageController.class);
	
	@GetMapping("/")
	public String Index(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "index";
	}
	
	
	@GetMapping("/about")
	public String About(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "about";
	}
	

	@GetMapping("/contact")
	public String Contact(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "contactUs";
	}
	
	
	@GetMapping("/doctor")
	public String Doctors(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "doctors";
	}
	
	@GetMapping("/404")
	public String error(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "error";
	}
	
	
	
	@GetMapping("/signin")
	public String Signin(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "login";
	}
	
	@GetMapping("/signup")
	public String Signup(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "register";
	}
	
	
	
	@GetMapping("/service")
	public String Service(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "services";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
	    try {
	        // Check if the user already exists
	        User existingUser = userRepo.findByEmail(user.getEmail());

	        if (existingUser != null) {
	            // Set error message and redirect if user already exists
	            session.setAttribute("msgError", "Email address already exists. Please use a different email.");
	            session.removeAttribute("msg");
	            return "redirect:/signup";
	        } else {
	            // Save the user and handle success or failure
	            User savedUser = userService.saveUser(user);

	            if (savedUser != null) {
	                // Set success message and redirect after successful registration
	                session.setAttribute("msg", "Registration successful. Please sign in.");
	                session.removeAttribute("msgError");
	                return "redirect:/signup";
	            } else {
	                // Set error message and redirect if something went wrong during registration
	                session.setAttribute("msgError", "Something went wrong on the server.");
	                session.removeAttribute("msg");
	                return "redirect:/signup";
	            }
	        }
	    } catch (Exception e) {
	        // Log the exception with an ERROR level
	        logger.error("An error occurred while processing user registration", e);
	        
	        // Redirect to an error page or handle the exception as appropriate
	        return "redirect:/errorPage";
	    }
	}

	
	
	
	
}
