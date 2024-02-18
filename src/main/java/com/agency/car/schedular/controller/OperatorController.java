package com.agency.car.schedular.controller;

import com.agency.car.schedular.entities.ServiceOperator;
import com.agency.car.schedular.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    //Post an operator by passing the name of the operator.
    @PostMapping("")
    public ResponseEntity<String> createOperator(@RequestParam("name") String name) {
        operatorService.createOperator(name);
        return new ResponseEntity<>("Operator created successfully", HttpStatus.CREATED);
    }

    //Get all the operators of the company
    @GetMapping("")
    public ResponseEntity<List<ServiceOperator>> getAllOperators() {
        List<ServiceOperator> operators = operatorService.getAllOperators();
        return ResponseEntity.ok(operators);
    }

}
