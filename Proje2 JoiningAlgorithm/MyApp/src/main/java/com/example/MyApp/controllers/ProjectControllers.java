package com.example.MyApp.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.MyApp.Service.Manager;
import com.example.MyApp.entities.Entity;
import com.example.MyApp.entities.RequestEntity;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api")
@AllArgsConstructor
public class ProjectControllers {
	private Manager manager;
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Entity save(@RequestBody RequestEntity requestEntity) {
		return manager.save(requestEntity);
	}
	
	
	
	
	
	
	
	
	
	
	
}
