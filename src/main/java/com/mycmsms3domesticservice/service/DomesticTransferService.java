package com.mycmsms3domesticservice.service;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<DomesticTransfer> getDomesticTransfers(){
        return domesticTransferRepository.findAll();
    }

    public Optional<DomesticTransfer> getSingleDomesticTransfers(UUID domesticTransferTrxId) {
        return domesticTransferRepository.findById(domesticTransferTrxId);
    }

    public void createDomesticTransfer(DomesticTransfer domesticTransfer) {
        domesticTransferRepository.save(domesticTransfer);
    }

    public void deleteDomesticTransfer(UUID domesticTransferTrxId) {
        boolean exist = domesticTransferRepository.existsById(domesticTransferTrxId);

        if (!exist){
            throw new IllegalStateException("Domestic Transaction with id " + domesticTransferTrxId + " does not exist !");
        }

        domesticTransferRepository.deleteById(domesticTransferTrxId);
    }

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
}
