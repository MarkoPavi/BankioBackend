package com.example.controllers;

import com.example.interfaces.RepositoryInterface;
import com.example.models.SomeData;
import com.example.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*@CrossOrigin(origins = "http://localhost:8081") //todo*/
@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private BankService bankService;

/*    @Autowired
    RepositoryInterface repositoryInterface;*/

    /*@GetMapping("/response")
    public ResponseEntity<List<SomeData>> getAllData(@RequestParam(required = false) String title){
        try {
            List<SomeData> someDataList = new ArrayList<SomeData>();

            if (title == null)
                repositoryInterface.findAll().forEach(someDataList::add);
            else
                repositoryInterface.findByTitleContaining(title).forEach(someDataList::add);

            if (someDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(someDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }*/

    @GetMapping("/getContract/{id}")
    public ResponseEntity<SomeData> getDataById(@PathVariable("id") int id){
        return bankService.getContract(id);
    }


    @PostMapping("/postContract")
    public ResponseEntity<SomeData> createContract(@RequestBody SomeData someData){
        return bankService.createContract(someData);
    }

/*
    @PutMapping("/response/{id}")
    public ResponseEntity<SomeData> updateData(@PathVariable("id") int id,SomeData someData){
        Optional<SomeData> checkData = repositoryInterface.findById(id);

        if(checkData.isPresent()){
            SomeData tempData = checkData.get();
            tempData.setName(someData.getName());
            tempData.setDescription(someData.getDescription());
            tempData.setIncome(someData.getIncome());
            tempData.setPublished(someData.getPublished());
            return new ResponseEntity<>(repositoryInterface.save(tempData), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/response/{id}")
    public ResponseEntity<HttpStatus> deleteData(@PathVariable("id") int id) {
        try {
            repositoryInterface.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/response")
    public ResponseEntity<HttpStatus> deleteAllData() {
        try {
            repositoryInterface.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
