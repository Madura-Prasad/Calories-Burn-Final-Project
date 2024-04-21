package com.example.CaloriesBurnPrediction.Service;



import java.util.List;

import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Model.User;


public interface UserService {

	public User saveUser(User user);
	
	List<Appointment> getAllAppointments();

	

}
