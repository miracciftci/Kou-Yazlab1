package com.example.MyApp.Service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.example.MyApp.DataAccess.EntityRepository;
import com.example.MyApp.entities.Entity;
import com.example.MyApp.entities.RequestEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Manager {
	
	private EntityRepository entityRepository;
	
	
	public Entity save(RequestEntity requestEntity) {
		long startTime = System.nanoTime();
		ArrayList<String> texts = new ArrayList<>();
		int size  = requestEntity.getTexts().size();
		String result = "";
		
		
		for (String string : requestEntity.getTexts().values()) { 
			texts.add(string.trim());
		}
		
		String checkResult = texts.get(0);
		
		// Birlestirme islemi
		for(int i = 1 ; i<size ; i++) {
			if(texts.get(0).contains(" ") && texts.get(i).contains(" ")) {  // ikiside birden cok kelime		
				String sonuc = connectMany(texts.get(0), texts.get(i));
				texts.set(0,sonuc);	
			}else if(texts.get(0).contains(" ") || texts.get(i).contains(" ")) {  // biri cok kelime biri tek kelime
				String sonuc = connectOne(texts.get(0), texts.get(i));
				texts.set(0,sonuc);	
			}else {   // ikiside tek kelime
				String sonuc = connectOne(texts.get(0), texts.get(i));
				texts.set(0,sonuc);	
			}
		}
			
		result = texts.get(0).trim();  // trim sondaki ve bastaki bosluklari siler

		long endTime= System.nanoTime(); 
		long estimatedTime = endTime - startTime;  // gecen sureyi milisaniye cinsinden aldık
		double time =(double)estimatedTime / 1000000000; // saniyeye cevrildi
		
		Entity entity = new Entity();
		entity.setTexts(requestEntity.getTexts());
		entity.setTime(time);
		
		if(checkTexts(result, checkResult)) {
			entity.setResult(result);   // birleştirilebilir
		}else {
			entity.setResult("Birleştirlemez");    // birleştirilemez
		}
		entityRepository.save(entity);
	
		return entity;
	}
	
	
	public String connectOne(String text1 , String text2) {
		String result = "";
	
		for(int i = 0 ; i<text1.length()-2 ; i++) {
			String word = text1.substring(i, i+3).toLowerCase();
			if(text2.toLowerCase().contains(word)) {
				int index = text2.toLowerCase().indexOf(word);
				result += text2.substring(index);
				break;
			}
			result += text1.charAt(i);
		}
		
		if(result.equals(text1.subSequence(0, text1.length()-2))) {
			result += text1.substring(text1.length()-2);
		}
	
		return result;
	}
	
	
	public String connectAll(String text1 , String text2) {
		text1 = text1.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		text2 = text2.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		String result = "";
	
		for(int i = 0 ; i<text1.length()-6 ; i++) {
			String word = text1.substring(i, i+6).toLowerCase();
			if(text2.toLowerCase().contains(word)) {
				System.out.println(word);
				int index = text2.toLowerCase().indexOf(word);
				result += text2.substring(index);
				break;
			}
			result += text1.charAt(i);
		}
		
		return result;
	}
	
	
	public String connectMany (String text1 , String text2){
		text1 = text1.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		text2 = text2.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		String[] dizi1 = text1.split(" ");
		String[] dizi2 = text2.split(" ");
		ArrayList<String> array1 = new ArrayList<>();		
		ArrayList<String> array2 = new ArrayList<>();
		
		String sonuc = "";
		
		for (String string : dizi1) {
			array1.add(string);
		}
		for (String string : dizi2) {
			array2.add(string);
		}
		
		boolean check = false;
		int count = -1;
	
		
		for (String str1 : array1) {
			for (String str2 : array2) {
				if(str1.toLowerCase().equals("ve") || str1.toLowerCase().equals("ile") || str1.toLowerCase().equals("da")|| str1.toLowerCase().equals("de")  ) {
					break;
				}
				if(str1.toLowerCase().equals(str2.toLowerCase())) {
					check = true;
					count = array2.indexOf(str2);
					break;
				}
			}
			if(check) 
				break;
	
			sonuc += str1 + " "; 
		}
		
		if(count != -1) {
			for(int i = count ; i<array2.size() ; i++) {
				sonuc += array2.get(i) + " ";
			}
		}
		
		return sonuc;
	}
	
	
	
	public Boolean checkTexts(String text1,String text2) {
		text1 = text1.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		text2 = text2.replace(",", "").replace(".", "").replace("?", "").replace("\"", "");
		
		if(text1.equals(text2)) {
			return false;
		}
		
		return true;
	}
	
}
