package com.agency.car.schedular.service;

import com.agency.car.schedular.database.OperatorRepository;
import com.agency.car.schedular.entities.ServiceOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements  OperatorService{
    @Autowired
    private OperatorRepository operatorRepository;
    //Method to create the service operator
    @Override
    public void createOperator(String operatorName) {
        ServiceOperator serviceOperator = new ServiceOperator();
        serviceOperator.setName(operatorName);
        operatorRepository.save(serviceOperator);
    }
    //Get all the service operator from the database
    @Override
    public List<ServiceOperator> getAllOperators() {
        return operatorRepository.findAll();
    }
}
