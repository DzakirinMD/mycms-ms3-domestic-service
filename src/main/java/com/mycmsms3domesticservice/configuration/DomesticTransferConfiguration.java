package com.mycmsms3domesticservice.configuration;

import com.mycmsms3domesticservice.entity.DomesticTransfer;
import com.mycmsms3domesticservice.repository.DomesticTransferRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomesticTransferConfiguration {
    
    @Bean
    public CommandLineRunner commandLineRunner(DomesticTransferRepository domesticTransferRepository) {
        // Check if data already exists in the database
        if (domesticTransferRepository.count() > 0) {
            return args -> {
                // Do nothing
            };
        } else {
            // Return the command line runner
            return args -> {
                DomesticTransfer domesticTransfer = new DomesticTransfer(
                        "domesticTransferTrxId",
                        "domesticTransferName",
                        "testTrx@gmail.com",
                        125.60
                );

                domesticTransferRepository.save(domesticTransfer);
            };
        }
    }
}
