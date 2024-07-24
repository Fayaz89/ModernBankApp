package com.pack.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
	// return response entity with a hashmap containing message, status value and
	// responseObject
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("message", message);
		map.put("status", status.value());
		if(responseObj!=null)
			map.put("responseObj", responseObj);
		ResponseEntity<Object> res = new ResponseEntity<Object>(map, status);
		return res;
	}
}
