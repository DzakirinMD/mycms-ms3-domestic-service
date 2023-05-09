package com.mycmsms3domesticservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.service.DomesticTransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private DomesticTransfer inputTrx1;

    private DomesticTransfer inputTrx2;

    @BeforeEach
    public void setup() {
        // Given
        inputTrx1 = new DomesticTransfer("Test send money", "johndoe@example.com", 500.00);
        inputTrx2 = new DomesticTransfer("Test sending money", "johnwick@example.com", 1000.00);

        // When
        underTest.createDomesticTransfer(inputTrx1);
        underTest.createDomesticTransfer(inputTrx2);
    }

    @AfterEach
    public void cleanup() {
        // Delete all created data from the database
        underTest.deleteDomesticTransfer(inputTrx1.getDomesticTransferTrxId());
        underTest.deleteDomesticTransfer(inputTrx2.getDomesticTransferTrxId());
    }

    @Test
    public void testGetAllDomesticTransfers() throws Exception {
        // Then
        // Perform GET request to retrieve all domestic transfers
        mockMvc.perform(get("/api/v1/domestic-transfer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx1.getDomesticTransferTrxId() + "')].domesticTransferTrxName").value(inputTrx1.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx1.getDomesticTransferTrxId() + "')].domesticTransferTrxEmailRecipient").value(inputTrx1.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx1.getDomesticTransferTrxId() + "')].domesticTransferTrxAmount").value(inputTrx1.getDomesticTransferTrxAmount()))
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx2.getDomesticTransferTrxId() + "')].domesticTransferTrxName").value(inputTrx2.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx2.getDomesticTransferTrxId() + "')].domesticTransferTrxEmailRecipient").value(inputTrx2.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$[?(@.domesticTransferTrxId == '" + inputTrx2.getDomesticTransferTrxId() + "')].domesticTransferTrxAmount").value(inputTrx2.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testGetSingleDomesticTransfer() throws Exception {
        // Then
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", inputTrx1.getDomesticTransferTrxId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxName").value(inputTrx1.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(inputTrx1.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(inputTrx1.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testCreateDomesticTransfer() throws Exception {
        //  Then
        // Perform POST request to create a new domestic transfer
        mockMvc.perform(post("/api/v1/domestic-transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTrx1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").isNotEmpty())
                .andExpect(jsonPath("$.domesticTransferTrxName").value(inputTrx1.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(inputTrx1.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(inputTrx1.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testUpdateDomesticTransfer() throws Exception {
        // Then
        // Perform PUT request to update the domestic transfer
        mockMvc.perform(put("/api/v1/domestic-transfer/{domesticTransferTrxId}", inputTrx1.getDomesticTransferTrxId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTrx2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").value(inputTrx1.getDomesticTransferTrxId().toString()))
                .andExpect(jsonPath("$.domesticTransferTrxName").value(inputTrx2.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(inputTrx2.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(inputTrx2.getDomesticTransferTrxAmount()));

        // Perform GET request to check if the domestic transfer has been updated
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", inputTrx1.getDomesticTransferTrxId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.domesticTransferTrxId").value(inputTrx1.getDomesticTransferTrxId().toString()))
                .andExpect(jsonPath("$.domesticTransferTrxName").value(inputTrx2.getDomesticTransferTrxName()))
                .andExpect(jsonPath("$.domesticTransferTrxEmailRecipient").value(inputTrx2.getDomesticTransferTrxEmailRecipient()))
                .andExpect(jsonPath("$.domesticTransferTrxAmount").value(inputTrx2.getDomesticTransferTrxAmount()));
    }

    @Test
    public void testDeleteDomesticTransfer() throws Exception {
        // Then
        // Perform DELETE request to delete the created domestic transfer
        mockMvc.perform(delete("/api/v1/domestic-transfer/{domesticTransferTrxId}", inputTrx1.getDomesticTransferTrxId()))
                .andExpect(status().isOk());

        // Try to get the deleted domestic transfer, should return 404 Not Found
        mockMvc.perform(get("/api/v1/domestic-transfer/{domesticTransferTrxId}", inputTrx1.getDomesticTransferTrxId()))
                .andExpect(status().isNotFound());

        // Try to delete a non-existing domestic transfer, should return 404 Not Found
        mockMvc.perform(delete("/api/v1/domestic-transfer/{domesticTransferTrxId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}