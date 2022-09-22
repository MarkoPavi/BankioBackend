package com.example.controllers;

import com.example.models.Contract;
import com.example.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@CrossOrigin(origins = "http://localhost:8081") //todo*/
@RestController
@RequestMapping("/api")
public class ContractController {

    @Autowired
    private BankService bankService;

    @GetMapping("/getAllContracts")
    public ResponseEntity<List<Contract>> getAllData(){
        return bankService.getAllContracts();
    }

    @GetMapping("/getContract/{id}")
    public ResponseEntity<Contract> getDataById(@PathVariable("id") int id){
        return bankService.getContract(id);
    }


    @PostMapping("/postContract")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract){
        return bankService.createContract(contract);
    }


    @PutMapping("/updateContract/{id}")
    public void updateData(@PathVariable("id") int id, Contract someData){
        bankService.updateData(id,someData);
    }


    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<HttpStatus> deleteContract(@PathVariable("id") int id) {
        return bankService.deleteContract(id);
    }

    @DeleteMapping("/deleteAllContracts")
    public ResponseEntity<HttpStatus> deleteAllContracts() {
        return bankService.deleteAllContracts();
    }
/*
    @GetMapping("/response/published")
    public ResponseEntity<List<SomeData>> findByPublished() {
        try {
            List<SomeData> someDataList = repositoryInterface.findByPublished(true);

            if (someDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(someDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

}
