package com.mycmsms3domesticservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingControllerTest {

    private final PingController pingController = new PingController();

    @Test
    public void testPing() {
        ResponseEntity<String> response = pingController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}
