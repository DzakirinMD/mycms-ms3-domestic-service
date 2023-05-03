package com.mycmsms3domesticservice.controller;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.service.DomesticTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Domestic Transfer Rest Controller
 */
@RestController
@RequestMapping("/api/v1/domestic-transfer")
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

    /**
     * Retrieves a single domestic transfer from the domestic transfer microservice by its transaction ID.
     *
     * @param domesticTransferTrxId a String representing the transaction ID of the domestic transfer to retrieve.
     * @return a ResponseEntity containing a DomesticTransfer object representing the requested domestic transfer, if it exists.
     * @throws ResourceNotFoundException if no domestic transfer with the specified ID is found.
     */
    @GetMapping("/{domesticTransferTrxId}")
    public ResponseEntity<DomesticTransfer> getSingleDomesticTransfer(@PathVariable UUID domesticTransferTrxId) {
        return ResponseEntity.ok(domesticTransferService
                .getSingleDomesticTransfers(domesticTransferTrxId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id : " + domesticTransferTrxId))
        );
    }

    /**
     * Creates a new domestic transfer.
     *
     * @param domesticTransfer a DomesticTransfer object representing the domestic transfer to create.
     */
    @PostMapping
    @Operation(summary = "Creates a new domestic transfer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public DomesticTransfer createDomesticTransfer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The domestic transfer object to create.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DomesticTransfer.class))
            )
            @RequestBody DomesticTransfer domesticTransfer) {
        return domesticTransferService.createDomesticTransfer(domesticTransfer);
    }

    @PutMapping("/{domesticTransferTrxId}")
    public ResponseEntity<DomesticTransfer> updateDomesticTransfer(@PathVariable UUID domesticTransferTrxId, @RequestBody DomesticTransfer domesticTransfer){
        return domesticTransferService.updateDomesticTransfer(domesticTransferTrxId, domesticTransfer);
    }

    @DeleteMapping("/{domesticTransferTrxId}")
    public void deleteDomesticTransfer(@PathVariable UUID domesticTransferTrxId) {
        domesticTransferService.deleteDomesticTransfer(domesticTransferTrxId);
    }
}
