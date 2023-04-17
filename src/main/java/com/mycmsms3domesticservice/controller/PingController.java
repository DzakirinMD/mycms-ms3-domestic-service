package com.mycmsms3domesticservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ping Rest Controller
 */
@RestController
@RequestMapping("/ping")
public class PingController {

    /**
     * Handles GET requests to /ping endpoint and returns a ResponseEntity with a "pong" message.
     * @return ResponseEntity with HTTP status 200 OK and a "pong" message
     */
    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
