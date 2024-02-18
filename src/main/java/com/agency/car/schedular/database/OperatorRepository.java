package com.agency.car.schedular.database;

import com.agency.car.schedular.entities.ServiceOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<ServiceOperator, Long> {
}