package com.mycmsms3domesticservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.service.DomesticTransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DomesticTransferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DomesticTransferService underTest;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void cleanup() {
        // Delete all created data from the database
        underTest.getAllDomesticTransfers().forEach(domesticTransfer -> underTest.deleteDomesticTransfer(domesticTransfer.getDomesticTransferTrxId()));
    }

    @Test
    public void testGetAllDomesticTransfers() throws Exception {
        // Given
        DomesticTransfer inputTrx1 = new DomesticTransfer("Test send money", "johndoe@example.com", 1000.00);
        DomesticTransfer inputTrx2 = new DomesticTransfer("Test sending money", "johnwick@example.com", 500.00);

        // When
        underTest.createDomesticTransfer(inputTrx1);
        underTest.createDomesticTransfer(inputTrx2);

        // Then
        // Perform GET request to retrieve all domestic transfers
        mockMvc.perform(get("/api/v1/domestic-transfer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].domesticTransferTrxName").value(inputTrx1.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$[0].domesticTransferTrxEmailRecipient").value(inputTrx1.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$[0].domesticTransferTrxAmount").value(inputTrx1.getDomesticTransferTrxAmount()))
                .andExpect(jsonPath("$[1].domesticTransferTrxName").value(inputTrx2.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$[1].domesticTransferTrxEmailRecipient").value(inputTrx2.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$[1].domesticTransferTrxAmount").value(inputTrx2.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testGetSingleDomesticTransfer() throws Exception {
        // Given
        DomesticTransfer createdTrx = new DomesticTransfer();
        createdTrx.setDomesticTransferTrxName("Test send money");
        createdTrx.setDomesticTransferTrxEmailRecipient("johndoe@example.com");
        createdTrx.setDomesticTransferTrxAmount(1000.00);

        // When
        underTest.createDomesticTransfer(createdTrx);

        // Then
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", createdTrx.getDomesticTransferTrxId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxName").value(createdTrx.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(createdTrx.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(createdTrx.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testCreateDomesticTransfer() throws Exception {
        // Given
        DomesticTransfer inputTrx = new DomesticTransfer();
        inputTrx.setDomesticTransferTrxId(UUID.randomUUID());
        inputTrx.setDomesticTransferTrxName("Test send money");
        inputTrx.setDomesticTransferTrxEmailRecipient("johndoe@example.com");
        inputTrx.setDomesticTransferTrxAmount(1000.00);

        // When and Then
        // Perform POST request to create a new domestic transfer
        mockMvc.perform(post("/api/v1/domestic-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTrx)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").isNotEmpty())
                .andExpect(jsonPath("$.domesticTransferTrxName").value(inputTrx.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(inputTrx.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(inputTrx.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testUpdateDomesticTransfer() throws Exception {
        // Given
        DomesticTransfer createdTrx = new DomesticTransfer();
        createdTrx.setDomesticTransferTrxName("Test send money");
        createdTrx.setDomesticTransferTrxEmailRecipient("johndoe@example.com");
        createdTrx.setDomesticTransferTrxAmount(1000.00);

        // When
        DomesticTransfer savedTrx = underTest.createDomesticTransfer(createdTrx);


        // Updated domestic transfer data
        DomesticTransfer updatedTrx = new DomesticTransfer();
        updatedTrx.setDomesticTransferTrxName("Updated send money");
        updatedTrx.setDomesticTransferTrxEmailRecipient("janedoe@example.com");
        updatedTrx.setDomesticTransferTrxAmount(1500.00);

        // Then
        // Perform PUT request to update the domestic transfer
        mockMvc.perform(put("/api/v1/domestic-transfer/{domesticTransferTrxId}", savedTrx.getDomesticTransferTrxId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTrx)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").value(savedTrx.getDomesticTransferTrxId().toString()))
                .andExpect(jsonPath("$.domesticTransferTrxName").value(updatedTrx.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(updatedTrx.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(updatedTrx.getDomesticTransferTrxAmount()));

        // Perform GET request to check if the domestic transfer has been updated
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", savedTrx.getDomesticTransferTrxId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").value(savedTrx.getDomesticTransferTrxId().toString()))
                .andExpect(jsonPath("$.domesticTransferTrxName").value(updatedTrx.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(updatedTrx.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(updatedTrx.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testDeleteDomesticTransfer() throws Exception {
        // Given
        DomesticTransfer createdTrx = new DomesticTransfer();
        createdTrx.setDomesticTransferTrxName("Test send money");
        createdTrx.setDomesticTransferTrxEmailRecipient("johndoe@example.com");
        createdTrx.setDomesticTransferTrxAmount(1000.00);

        // When
        underTest.createDomesticTransfer(createdTrx);

        // Then
        // Perform DELETE request to delete the created domestic transfer
        mockMvc.perform(delete("/api/v1/domestic-transfer/{domesticTransferTrxId}", createdTrx.getDomesticTransferTrxId()))
                .andExpect(status().isOk());

        // Try to get the deleted domestic transfer, should return 404 Not Found
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", createdTrx.getDomesticTransferTrxId()))
                .andExpect(status().isNotFound());

        // Try to delete a non-existing domestic transfer, should return 404 Not Found
        mockMvc.perform(delete("/api/v1/domestic-transfer/{domesticTransferTrxId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}