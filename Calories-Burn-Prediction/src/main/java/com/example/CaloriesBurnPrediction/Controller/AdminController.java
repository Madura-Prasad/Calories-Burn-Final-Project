package com.example.CaloriesBurnPrediction.Controller;

import java.security.Principal;
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
	            User saveUser = appointmentService.saveUser(user);
	            return "redirect:/admin/";
	        } catch (Exception e) {
	            return "redirect:/errorPage";
	        }
	    }

	    @GetMapping("/editUser")
	    public String editUser(Model model, Principal principal) {
	        boolean isLoggedIn = principal != null;
	        model.addAttribute("isLoggedIn", isLoggedIn);
	        if (isLoggedIn) {
	            String email = principal.getName();
	            User user = userRepo.findByEmail(email);
	            model.addAttribute("users", user);
	        }
	        return "AdminPanel/editUser";
	    }

	    @GetMapping("/editUser/{id}")
	    public String editSUser(@PathVariable Long id, Model model) {
	        try {
	            User user = appointmentService.getUserByID(id);
	            model.addAttribute("user", user);
	            return "AdminPanel/editUser";
	        } catch (Exception e) {
	            return "redirect:/errorPage";
	        }
	    }

	    @PostMapping("/editUser/{id}")
	    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
	        try {
	            User existingUser = appointmentService.getUserByID(id);
	            existingUser.setId(id);
	            existingUser.setName(user.getName());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setMobile(user.getMobile());
	            String newPassword = user.getPassword();
	            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	            String hashedPassword = encoder.encode(newPassword);
	            existingUser.setPassword(hashedPassword);
	            appointmentService.updateUser(existingUser);
	            return "redirect:/admin/";
	        } catch (Exception e) {
	            return "redirect:/errorPage";
	        }
	    }

	    @GetMapping("/deleteUser/{id}")
	    public String deleteUser(@PathVariable Long id) {
	        try {
	        	appointmentService.deleteUser(id);
	          
	            return "redirect:/admin/";
	        } catch (Exception e) {
	            
	            return "redirect:/errorPage";
	        }
	    }
	

	
	@GetMapping("/manageAppointment")
	public String Appointment(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("users", user);
		}
		model.addAttribute("appointment", userService.getAllAppointments());
		return "/AdminPanel/manageAppointment";
	}
	
	@GetMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        try {
        	appointmentService.deleteAppointment(id);
         
            return "redirect:/admin/manageAppointment";
        } catch (Exception e) {
            
            return "redirect:/errorPage";
        }
    }
	

}
