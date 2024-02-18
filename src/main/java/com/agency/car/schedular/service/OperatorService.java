package com.agency.car.schedular.service;

import com.agency.car.schedular.entities.ServiceOperator;

import java.util.List;

public interface OperatorService {
    void createOperator(String operatorName);
    List<ServiceOperator> getAllOperators();
}
