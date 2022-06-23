
package com.example.services;

import com.example.interfaces.RepositoryInterface;
import com.example.models.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BankService {

    final static Logger log = LoggerFactory.getLogger(BankService.class);
    @Autowired
    RepositoryInterface repositoryInterface;

    public ResponseEntity<List<Contract>> getAllContracts(){
        try {

            List<Contract> contractList = new ArrayList<>(repositoryInterface.findAll());
            return new ResponseEntity<>(contractList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Contract> getContract(int id){
        Optional<Contract> contractData = repositoryInterface.findById(id);

        //todo
        if(contractData.isPresent()){
            return new ResponseEntity<>(contractData.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<Contract> createContract(Contract contract){
        try {
            Contract finalData = repositoryInterface.
                    save(Contract.builder()
                            .name(contract.getName())
                            .surname(contract.getSurname())
                            .title(contract.getTitle())
                            .gender(contract.getGender())
                            .address(contract.getAddress())
                            .postalCode(contract.getPostalCode())
                            .birthCountry(contract.getBirthCountry())
                            .stayingCountry(contract.getStayingCountry())
                            .citizenship(contract.getCitizenship())
                            .phoneNumber(contract.getPhoneNumber())
                            .loanAmmount(contract.getLoanAmmount())
                            //.loanDuration(someData.getLoanDuration())
                            .interestRate(contract.getInterestRate())
                            .monthlyPayment(contract.getMonthlyPayment())
                            .taxes(contract.getTaxes())
                            //.iban(someData.getIban())
                            .income(contract.getIncome())
                            .published(false)
                            //.relationshipStatus(someData.getRelationshipStatus())
                            .description(contract.getDescription())
                            .build());
            return new ResponseEntity<>(finalData, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteContract(int id){
        try {
            repositoryInterface.deleteById(id);
            log.info("Successfuly deleted contract with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("No contract with id : " + id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<HttpStatus> deleteAllContracts(){
        try {
            repositoryInterface.deleteAll();
            log.info("Deleted all contracts successfully.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("No contracts found.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateData(int id, Contract someData){
        Optional<Contract> checkData = repositoryInterface.findById(id);

        if(checkData.isPresent()){
            Contract tempData = checkData.get();
            tempData.setName(someData.getName());
            tempData.setDescription(someData.getDescription());
            tempData.setIncome(someData.getIncome());
            tempData.setPublished(someData.getPublished());
            log.info("Updated contract with id : " + id);
            new ResponseEntity<>(repositoryInterface.save(tempData), HttpStatus.OK);
        }else{
            log.info("Contract with ID: + " + id + " not found");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

