package com.agency.car.schedular.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long operatorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private int startTime;

    private int endTime;

}
