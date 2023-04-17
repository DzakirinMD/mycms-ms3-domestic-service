package com.mycmsms3domesticservice.repository;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticTransferRepository extends JpaRepository<DomesticTransfer, Long> {
}
