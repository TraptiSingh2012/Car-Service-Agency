package com.agency.car.schedular.controller;

import com.agency.car.schedular.entities.Booking;
import com.agency.car.schedular.service.SchedularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class ScheduleController {

    @Autowired
    SchedularService schedularService;

    //Making a booking by providing the date and the start_time.
    @PostMapping("")
    public ResponseEntity<String> createBooking(
            @RequestParam("date") String date,
            @RequestParam("start_time") int startTime,
            @RequestParam(value = "service_operator_id", required = false) String operatorId) throws ParseException {

        //if date format is wrong throw an error
        if (!schedularService.isValidDateFormat(date)) {
            return new ResponseEntity<>("Invalid date. Please provide valid date in the following format yyyy-MM-dd.", HttpStatus.BAD_REQUEST);
        }
        //if start_time is not in range throw an error
        if(startTime < 0 || startTime > 23){
            return new ResponseEntity<>("Invalid start_time. Please provide start_time between 0 to 23", HttpStatus.BAD_REQUEST);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(date);

        if (schedularService.createBooking(bookingDate, startTime, operatorId)) {
            return new ResponseEntity<>("Appointment scheduled successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to schedule appointment. Operator not available or slot already booked.", HttpStatus.BAD_REQUEST);
        }
    }
    //Getting all the bookings
    @GetMapping("")
    public ResponseEntity<List<Booking>> getAllBooking(){
            List<Booking> bookings = schedularService.getAllBookings();
            return ResponseEntity.ok(bookings);

    }
    //Getting the booking by id.
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Booking booking = schedularService.getBookingById(id);
        if(booking!=null)
            return ResponseEntity.ok(booking);
        else
            return ResponseEntity.notFound().build();

    }
    //Getting the bookied slots of an operator by providing the id and the date
    @GetMapping("/bookedSlots/{id}")
    public ResponseEntity<String> getBookingById(@PathVariable Long id,
                                                 @RequestParam("date") String date) throws ParseException{
        if (!schedularService.isValidDateFormat(date)) {
            return new ResponseEntity<>("Invalid date. Please provide valid date in the following format yyyy-MM-dd.", HttpStatus.BAD_REQUEST);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(date);

        String allbooking = schedularService.getAllBookedSlotsByIdAndDate(id,bookingDate);
        return ResponseEntity.ok(allbooking);
    }

    //Getting the open slots of an operator by providing the id and the date
    @GetMapping("/openSlots/{id}")
    public ResponseEntity<String> getFreeSlotsById(@PathVariable Long id,
                                                 @RequestParam("date") String date) throws ParseException{
        if (!schedularService.isValidDateFormat(date)) {
            return new ResponseEntity<>("Invalid date. Please provide valid date in the following format yyyy-MM-dd.", HttpStatus.BAD_REQUEST);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(date);

        String allbooking = schedularService.getAllOpenSlotsByIdAndDate(id,bookingDate);
        return ResponseEntity.ok(allbooking);
    }

    //deleting the booking by an id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id) {
        if (schedularService.deleteBookingById(id)) {
            return ResponseEntity.ok("Appointment deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //rescheduling the booking by providing the booking id.
    @PutMapping("/{id}")
    public ResponseEntity<String> rescheduleAppointmentById(@PathVariable Long id,
                                                            @RequestParam("date") String date,
                                                        @RequestParam("date_new") String dateNew,
                                                        @RequestParam("start_time") int startTime,
                                                        @RequestParam("start_time_new") int startTimeNew) throws ParseException{
        if (!schedularService.isValidDateFormat(date) || !schedularService.isValidDateFormat(date)) {
            return new ResponseEntity<>("Invalid date. Please provide valid date in the following format yyyy-MM-dd.", HttpStatus.BAD_REQUEST);
        }

        if(startTime < 0 || startTime > 23 || startTimeNew < 0 || startTimeNew > 23){
            return new ResponseEntity<>("Invalid start_time. Please provide start_time between 0 to 23", HttpStatus.BAD_REQUEST);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(date);
        Date bookingDateNew = dateFormat.parse(dateNew);

        if (schedularService.rescheduleAppointmentById(id,bookingDate,startTime,bookingDateNew,startTimeNew)) {
            return ResponseEntity.ok("Appointment rescheduled successfully");
        } else {
            return ResponseEntity.ok("Sorry,we can't reschedule the appointment.");
        }
    }
}
