package com.mycmsms3domesticservice.service;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DomesticTransferService {

    @Autowired
    private DomesticTransferRepository domesticTransferRepository;

    /**
     * Retrieves all domestic transfers.
     *
     * @return List of all domestic transfers
     */
    public List<DomesticTransfer> getAllDomesticTransfers(){
        return domesticTransferRepository.findAll();
    }

    /**
     * Retrieves a single domestic transfer by its ID.
     *
     * @param domesticTransferTrxId the ID of the domestic transfer
     * @return ResponseEntity containing the domestic transfer if found, or a not found error message
     */
    public ResponseEntity<Optional<DomesticTransfer>> getSingleDomesticTransfers(UUID domesticTransferTrxId) {
        Optional<DomesticTransfer> optionalDomesticTransfer = domesticTransferRepository.findById(domesticTransferTrxId);

        if (optionalDomesticTransfer.isPresent()) {
            return ResponseEntity.ok(optionalDomesticTransfer);
        } else {
            String errorMessage = String.format("Domestic transfer transaction with ID %s not found.", domesticTransferTrxId);
            return ResponseEntity.notFound().header("error-message", errorMessage).build();
        }
    }

    /**
     * Creates a new domestic transfer.
     *
     * @param domesticTransfer the domestic transfer to be created
     * @return the created domestic transfer
     */
    public DomesticTransfer createDomesticTransfer(DomesticTransfer domesticTransfer) {
        domesticTransferRepository.save(domesticTransfer);
        return domesticTransfer;
    }

    /**
     * Updates an existing domestic transfer.
     *
     * @param domesticTransferTrxId the ID of the domestic transfer to be updated
     * @param domesticTransfer the updated domestic transfer
     * @return ResponseEntity containing the updated domestic transfer if found, or a not found error message
     */
    @Transactional
    public ResponseEntity<DomesticTransfer> updateDomesticTransfer(UUID domesticTransferTrxId, DomesticTransfer domesticTransfer) {
        Optional<DomesticTransfer> optionalDomesticTransfer = domesticTransferRepository.findById(domesticTransferTrxId);

        if (optionalDomesticTransfer.isPresent()) {
            DomesticTransfer existingDomesticTransfer = optionalDomesticTransfer.get();
            existingDomesticTransfer.setDomesticTransferTrxName(domesticTransfer.getDomesticTransferTrxName());
            existingDomesticTransfer.setDomesticTransferTrxEmailRecipient(domesticTransfer.getDomesticTransferTrxEmailRecipient());
            existingDomesticTransfer.setDomesticTransferTrxAmount(domesticTransfer.getDomesticTransferTrxAmount());
            return ResponseEntity.ok(domesticTransferRepository.save(existingDomesticTransfer));
        } else {
            String errorMessage = String.format("Domestic transfer transaction with ID %s not found.", domesticTransferTrxId);
            return ResponseEntity.notFound().header("error-message", errorMessage).build();
        }
    }

    /**
     * Deletes a domestic transfer by its ID.
     *
     * @param domesticTransferTrxId the ID of the domestic transfer to be deleted
     * @return ResponseEntity containing the deletion status message or a not found error message
     */
    public ResponseEntity<String> deleteDomesticTransfer(UUID domesticTransferTrxId) {
        boolean exist = domesticTransferRepository.existsById(domesticTransferTrxId);
        if (!exist) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Domestic Transaction with id " + domesticTransferTrxId + " does not exist !");
        } else {
            domesticTransferRepository.deleteById(domesticTransferTrxId);
            return ResponseEntity.ok("Domestic transfer deleted successfully.");
        }
    }
}
