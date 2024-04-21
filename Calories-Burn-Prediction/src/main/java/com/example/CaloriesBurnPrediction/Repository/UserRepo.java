package com.example.CaloriesBurnPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CaloriesBurnPrediction.Model.User;


public interface UserRepo extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);

}
