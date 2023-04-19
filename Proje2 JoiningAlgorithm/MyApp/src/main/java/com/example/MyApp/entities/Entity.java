package com.example.MyApp.entities;

import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Entity {
	private double time;
	private HashMap<String,  String> texts = new HashMap<>();
	private String result;
}

