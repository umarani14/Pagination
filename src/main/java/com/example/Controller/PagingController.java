package com.example.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.Entity.Orders;
import com.example.Service.PagingServie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.example.Service.PagingServie;

@RestController
public class PagingController {
	
	Logger log= LoggerFactory.getLogger(PagingController.class);
	
	@Autowired
	PagingServie pagingServie;
	
	@Autowired(required=true)
	RestTemplate restTemplate1;

	@GetMapping("/getItem")
	public ResponseEntity<List<Orders>> getItems(){
		ResponseEntity<List<Orders>> rs = null;
		List<Orders> list = null;
		try {
			list=pagingServie.getItems();
			
		}catch(Exception e) {
			return new ResponseEntity<List<Orders>>(list, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);
	
		
	}
	
	@GetMapping(value="/pageable")
	public Page<Orders> getItemsByPage(org.springframework.data.domain.Pageable page){
		Page page1=pagingServie.getItemsForPage(page);	
		return page1;
	}
	
	@GetMapping(value="/orders/page")
	public ResponseEntity<Page<Orders>> getOrdersWithPageandSize(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
		org.springframework.data.domain.Pageable paging=PageRequest.of(page, size);
		try {
			Page<Orders> pages=pagingServie.getItemsForPage(paging);
			return new ResponseEntity<Page<Orders>>(pages,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Page<Orders>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/page/sort")
	public ResponseEntity<Page<Orders>> getOrdersWithPageAndSort(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="6") int size){
		
		org.springframework.data.domain.Pageable paging= PageRequest.of(page, size, Sort.by("date"));
		
		try {
			Page<Orders> pages=pagingServie.getItemsForPage(paging);
			
//			JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/orderLines.json"));
			
			return new ResponseEntity<Page<Orders>>(pages,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Page<Orders>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/writetoFile")
	public String writeToFile() {
		String apiUrl="http://localhost:8080/page/sort";
		String jsonString;
        JSONObject jsonObject;
		try {
			String responseBody = restTemplate1.getForObject(apiUrl, String.class);
			Path path=Paths.get("output.json");
			Files.write(path, responseBody.getBytes());
			log.info("File written to: " + path.toAbsolutePath());
			jsonString = new String(
	                Files.readAllBytes(Paths.get(""+path.toAbsolutePath())));
			jsonObject = new JSONObject(jsonString);
			JSONArray docs
            = jsonObject.getJSONArray("content");
			File file = new File("C:\\Users\\Public\\Frontend\\PagingRepo\\PagingRepo\\outputCsv.csv");
			String csvString = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csvString);
			return "successfully written";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  "exception while writting";
		}
	}
	
//	@GetMapping("/WriteToCSV")
//	private void writeJSONToCsv() {
//		String apiUrl="http://localhost:8080/page/sort";
//		String jsonString;
//        JSONObject jsonObject;
//		try {
//			jsonString = new String(
//	                Files.readAllBytes(Paths.get("C:\\Users\\Public\\Frontend\\PagingRepo\\PagingRepo\\output.json")));
//			jsonObject = new JSONObject(jsonString);
//			JSONArray docs
//            = jsonObject.getJSONArray("content");
//			File file = new File("C:\\Users\\Public\\Frontend\\PagingRepo\\PagingRepo\\outputCsv.csv");
//			String csvString = CDL.toString(docs);
//            FileUtils.writeStringToFile(file, csvString);			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
