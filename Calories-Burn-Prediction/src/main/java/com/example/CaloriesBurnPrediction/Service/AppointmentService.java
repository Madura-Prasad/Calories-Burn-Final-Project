package com.example.CaloriesBurnPrediction.Service;

import java.util.List;

import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Model.User;

public interface AppointmentService {
	
	public Appointment saveAppointment(Appointment appointment);
	
	void deleteAppointment(Long id);
	
	public User saveUser(User user);

	List<User> getAllUser();

	User getUserByID(Long id);

	User updateUser(User user);
	
	void deleteUser(Long id);

}
