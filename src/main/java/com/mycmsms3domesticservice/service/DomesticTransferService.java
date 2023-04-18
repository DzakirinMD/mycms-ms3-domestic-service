package com.mycmsms3domesticservice.service;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomesticTransferService {

    @Autowired
    private DomesticTransferRepository domesticTransferRepository;

    public List<DomesticTransfer> getDomesticTransfers(){
        return domesticTransferRepository.findAll();
    }

    public DomesticTransfer getSingleDomesticTransfers() {
        return null;
    }
}
