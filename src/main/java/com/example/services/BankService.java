
package com.example.services;

import com.example.factories.WebClientFactory;
import com.example.interfaces.RepositoryInterface;
import com.example.models.SomeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class BankService {

    @Autowired
    RepositoryInterface repositoryInterface;

    public ResponseEntity<SomeData> getContract(int id){
        Optional<SomeData> contractData = repositoryInterface.findById(id);

        if(contractData.isPresent()){
            return new ResponseEntity<>(contractData.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<SomeData> createContract(SomeData someData){
        try {
            SomeData finalData = repositoryInterface.
                    save(SomeData.builder()
                            .name(someData.getName())
                            .surname(someData.getSurname())
                            .title(someData.getTitle())
                            .gender(someData.getGender())
                            .address(someData.getAddress())
                            .postalCode(someData.getPostalCode())
                            .birthCountry(someData.getBirthCountry())
                            .stayingCountry(someData.getStayingCountry())
                            .citizenship(someData.getCitizenship())
                            .phoneNumber(someData.getPhoneNumber())
                            .loanAmmount(someData.getLoanAmmount())
                            //.loanDuration(someData.getLoanDuration())
                            .interestRate(someData.getInterestRate())
                            .monthlyPayment(someData.getMonthlyPayment())
                            .taxes(someData.getTaxes())
                            //.iban(someData.getIban())
                            .income(someData.getIncome())
                            .published(false)
                            //.relationshipStatus(someData.getRelationshipStatus())
                            .description(someData.getDescription())
                            .build());
            return new ResponseEntity<>(finalData, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

