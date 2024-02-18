package com.agency.car.schedular.service;

import com.agency.car.schedular.entities.Booking;

import java.util.Date;
import java.util.List;

public interface SchedularService {
    boolean createBooking(Date date, int startTime, String serviceOperatorId);
    List<Booking> getAllBookings();
    Booking getBookingById(Long Id);
    String getAllBookedSlotsByIdAndDate(Long id,Date date);
    String getAllOpenSlotsByIdAndDate(Long id,Date date);
    boolean deleteBookingById(Long Id);
    boolean rescheduleAppointmentById(Long id,Date bookingDate,int startTime,Date bookingDateNew,int startTimeNew);
    boolean isValidDateFormat(String date);
}
