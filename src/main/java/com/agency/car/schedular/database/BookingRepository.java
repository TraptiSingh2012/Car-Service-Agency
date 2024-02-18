package com.agency.car.schedular.database;


import com.agency.car.schedular.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByOperatorIdAndDateAndStartTime(Long operatorId, Date date, int startTime);
    List<Booking> findByOperatorIdAndDate(Long id,Date date);
}
