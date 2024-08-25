package com.example.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bfhl")
public class ApiController {

    private static final String USER_ID = "john_doe_17091999";
    private static final String EMAIL = "john@xyz.com";
    private static final String ROLL_NUMBER = "ABCD123";

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getOperationCode() {
        Map<String, Integer> response = new HashMap<>();
        response.put("operation_code", 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> handlePost(@RequestBody Map<String, Object> request) {
        String operation = (String) request.get("operation");
        Map<String, Object> response;

        switch (operation) {
            case "processData":
                response = processData((List<String>) request.get("data"));
                break;
            case "anotherOperation":
                response = anotherOperation((List<String>) request.get("data"));
                break;
            default:
                response = new HashMap<>();
                response.put("is_success", false);
                response.put("message", "Invalid operation");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Map<String, Object> processData(List<String> data) {
        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        String highestLowercaseAlphabet = null;

        for (String item : data) {
            if (item.matches("[0-9]+")) {
                numbers.add(item);
            } else if (item.matches("[a-zA-Z]")) {
                alphabets.add(item);
                if (item.matches("[a-z]")) {
                    if (highestLowercaseAlphabet == null || item.compareTo(highestLowercaseAlphabet) > 0) {
                        highestLowercaseAlphabet = item;
                    }
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("is_success", true);
        response.put("user_id", USER_ID);
        response.put("email", EMAIL);
        response.put("roll_number", ROLL_NUMBER);
        response.put("numbers", numbers);
        response.put("alphabets", alphabets);
        response.put("highest_lowercase_alphabet", highestLowercaseAlphabet != null ? Collections.singletonList(highestLowercaseAlphabet) : Collections.emptyList());

        return response;
    }

    private Map<String, Object> anotherOperation(List<String> data) {
        // Implement another operation logic here
        // Example: Reverse the data list
        Map<String, Object> response = new HashMap<>();
        response.put("is_success", true);
        response.put("user_id", USER_ID);
        response.put("email", EMAIL);
        response.put("roll_number", ROLL_NUMBER);
        response.put("reversed_data", new ArrayList<>(data));
        Collections.reverse((List<?>) response.get("reversed_data"));
        return response;
    }
}
