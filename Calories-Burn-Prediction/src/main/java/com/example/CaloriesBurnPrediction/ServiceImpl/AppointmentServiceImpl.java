package com.example.CaloriesBurnPrediction.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Model.User;
import com.example.CaloriesBurnPrediction.Repository.AppointmentRepo;
import com.example.CaloriesBurnPrediction.Repository.UserRepo;
import com.example.CaloriesBurnPrediction.Service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	

	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public Appointment saveAppointment(Appointment appointment) {
		Appointment newAppointment= appointmentRepo.save(appointment);
		return newAppointment;
	}


	@Override
	public void deleteAppointment(Long id) {
		appointmentRepo.deleteById(id);
		
	}


	@Override
	public User saveUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		User newUser= userRepo.save(user);
		return newUser;
	}


	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}


	@Override
	public User getUserByID(Long id) {
		return userRepo.findById(id).get();
	}


	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}


	@Override
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
		
	}

}
