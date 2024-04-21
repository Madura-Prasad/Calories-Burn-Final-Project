package com.example.CaloriesBurnPrediction.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Repository.AppointmentRepo;
import com.example.CaloriesBurnPrediction.Service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	

	
	@Autowired
	private AppointmentRepo appointmentRepo;


	@Override
	public Appointment saveAppointment(Appointment appointment) {
		Appointment newAppointment= appointmentRepo.save(appointment);
		return newAppointment;
	}

}
