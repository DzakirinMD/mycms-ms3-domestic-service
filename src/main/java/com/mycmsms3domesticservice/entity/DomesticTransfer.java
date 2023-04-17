package com.mycmsms3domesticservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "domestic_transfers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
//  @RequiredArgsConstructor create constructor for all fields that are either @NonNull or final.
public class DomesticTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String domesticTransferTrxId;

    @NonNull
    private String domesticTransferTrxName;

    @NonNull
    @Size(max = 50)
    @Email
    private String domesticTransferTrxEmailRecipient;

    private double domesticTransferTrxAmount;

    public DomesticTransfer(@NonNull String domesticTransferTrxId, @NonNull String domesticTransferTrxName, @NonNull String domesticTransferTrxEmailRecipient, double domesticTransferTrxAmount) {
        this.domesticTransferTrxId = domesticTransferTrxId;
        this.domesticTransferTrxName = domesticTransferTrxName;
        this.domesticTransferTrxEmailRecipient = domesticTransferTrxEmailRecipient;
        this.domesticTransferTrxAmount = domesticTransferTrxAmount;
    }
}
