package com.mycmsms3domesticservice.controller;

import com.mycmsms3domesticservice.config.TestDatabaseConfiguration;
import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
public class DomesticTransferControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DomesticTransferRepository domesticTransferRepository;

    @BeforeEach
    void setUp() {
        DomesticTransfer domesticTransfer1 = new DomesticTransfer("domesticTransferTrxId1", "domesticTransferName1", "testTrx1@gmail.com", 125.60);
        DomesticTransfer domesticTransfer2 = new DomesticTransfer("domesticTransferTrxId2", "domesticTransferName2", "testTrx2@gmail.com", 250.10);
        domesticTransferRepository.saveAll(Arrays.asList(domesticTransfer1, domesticTransfer2));
    }

    @AfterEach
    void tearDown() {
        domesticTransferRepository.deleteAll();
    }

    @Test
    void testGetDomesticTransfers() {
        ResponseEntity<List<DomesticTransfer>> response = restTemplate.exchange(
                "/domestic-transfer",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<DomesticTransfer> domesticTransfers = response.getBody();
        assert domesticTransfers != null;
        assertNotNull(domesticTransfers);
        assertEquals(2, domesticTransfers.size());
        assertEquals("domesticTransferTrxId1", domesticTransfers.get(0).getDomesticTransferTrxId());
        assertEquals("domesticTransferName1", domesticTransfers.get(0).getDomesticTransferTrxName());
        assertEquals("testTrx1@gmail.com", domesticTransfers.get(0).getDomesticTransferTrxEmailRecipient());
        assertEquals(125.60, domesticTransfers.get(0).getDomesticTransferTrxAmount(), 0.001);
        assertEquals("domesticTransferTrxId2", domesticTransfers.get(1).getDomesticTransferTrxId());
        assertEquals("domesticTransferName2", domesticTransfers.get(1).getDomesticTransferTrxName());
        assertEquals("testTrx2@gmail.com", domesticTransfers.get(1).getDomesticTransferTrxEmailRecipient());
        assertEquals(250.10, domesticTransfers.get(1).getDomesticTransferTrxAmount(), 0.001);
    }
}