package com.mycmsms3domesticservice.controller;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.service.DomesticTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Domestic Transfers Rest Controller
 */
@RestController
@RequestMapping("domestic-transfer")
public class DomesticTransferController {

    @Autowired
    private DomesticTransferService domesticTransferService;

    /**
     * Handles HTTP GET requests to retrieve a list of domestic transfer resources.
     *
     * @return a ResponseEntity with HTTP status 200 OK and a list of domestic transfer resources.
     */
    @GetMapping
    public ResponseEntity<List<DomesticTransfer>> getDomesticTransfers() {
        return ResponseEntity.ok(domesticTransferService.getDomesticTransfers());
    }
}
