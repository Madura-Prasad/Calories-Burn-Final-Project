package com.example.CaloriesBurnPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CaloriesBurnPrediction.Model.Appointment;
import com.example.CaloriesBurnPrediction.Model.User;

public interface AppointmentRepo extends JpaRepository<Appointment, Long>{


}
