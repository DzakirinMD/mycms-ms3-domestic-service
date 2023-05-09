package com.mycmsms3domesticservice.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.entity.dto.DomesticTransferEventDTO;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DomesticTransferConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomesticTransferConsumer.class);

    @Autowired
    private DomesticTransferRepository domesticTransferRepository;

    /**
     * Kafka listener method to consume and process domestic transfer events.
     *
     * @param message the incoming Kafka message
     */
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void createDomesticTransferKafka(String message) {
        try {
            LOGGER.info(String.format("Domestic Transfer Event received in domestictransfers-service => %s", message));

            ObjectMapper objectMapper = new ObjectMapper();
            DomesticTransferEventDTO domesticTransferEventDTO = objectMapper.readValue(message, DomesticTransferEventDTO.class);

            DomesticTransfer domesticTransfer = new DomesticTransfer();
            domesticTransfer.setDomesticTransferTrxName(domesticTransferEventDTO.getDomesticTransferDTO().getDomesticTransferTrxName());
            domesticTransfer.setDomesticTransferTrxEmailRecipient(domesticTransferEventDTO.getDomesticTransferDTO().getDomesticTransferTrxEmailRecipient());
            domesticTransfer.setDomesticTransferTrxAmount(domesticTransferEventDTO.getDomesticTransferDTO().getDomesticTransferTrxAmount());

            domesticTransferRepository.save(domesticTransfer);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Constraint violation occurred while saving domestic transfer: " + e.getMessage());
        }
    }
}
