/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Sallam
 */
@RestController
@RequestMapping("/sendNotification")
public class NotificationController {

    private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final String FIREBASE_SERVER_KEY = "AAAAGPbBPA4:APA91bEo_W2KLSHTc49SZtozDWtoDkWnrGSMbX4Rzhue716s_lhNDFhXm_rlJUWZ64hzshATGXr8gEz02DkxYFIoY1jXN9eTNDySRyIrgfOWfl27793UDGDLuczw0SHi9SJmT72DxAK5";

    @GetMapping("/clearAll/{token}")
    public void sendClearDataMessage(@PathVariable("token") String deviceToken) {
        JSONObject msg = new JSONObject();

        msg.put("title", "clearAll");
        msg.put("body", "wipe device data");
        msg.put("notificationType", "Test");

        callToFcmServer(msg, deviceToken);

    }

    private String callToFcmServer(JSONObject message, String receiverFcmKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("data", message);
//        json.put("notification", message);
        json.put("to", receiverFcmKey);
        json.put("priority", "high");

        System.out.println("Sending :" + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
        return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    }

}
