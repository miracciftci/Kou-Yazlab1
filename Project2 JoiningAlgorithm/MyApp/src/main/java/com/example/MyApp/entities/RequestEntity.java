package com.example.MyApp.entities;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestEntity {
	private HashMap<String,  String> texts = new HashMap<>();
}
