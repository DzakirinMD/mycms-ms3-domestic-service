package com.mycmsms3domesticservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "domestic_transfers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
//  @RequiredArgsConstructor create constructor for all fields that are either @NonNull or final.
public class DomesticTransfer {

    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    private Long id;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID domesticTransferTrxId;

    @NonNull
    private String domesticTransferTrxName;

    @NonNull
    @Size(max = 50)
    @Email
    private String domesticTransferTrxEmailRecipient;

    private double domesticTransferTrxAmount;

}
