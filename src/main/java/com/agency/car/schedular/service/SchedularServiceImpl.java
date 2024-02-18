package com.agency.car.schedular.service;

import com.agency.car.schedular.database.BookingRepository;
import com.agency.car.schedular.database.OperatorRepository;
import com.agency.car.schedular.entities.Booking;
import com.agency.car.schedular.entities.ServiceOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchedularServiceImpl implements SchedularService{
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private OperatorService operatorService;

    //function to create the booking
    @Override
    public boolean createBooking(Date date, int startTime, String serviceOperatorId){
        System.out.println(date+" "+startTime+" "+serviceOperatorId);

        if(serviceOperatorId!=null){
            Long operatorId=Long.parseLong(serviceOperatorId);
            Optional<ServiceOperator> service_operator = operatorRepository.findById(operatorId);
            if (service_operator.isPresent()) {
                ServiceOperator operator = service_operator.get();
                System.out.println(operator.getName());

                if (!isSlotAvailable(operator, date, startTime)) {
                    // Overlapping appointments, slot not available
                    return false;
                }
                // Return true if the appointment is successfully scheduled
                return true;
            }
        }else {
            // ServiceOperator with the serviceOperatorId does not exist, check for all serviceOperator from serviceOperators.
            List<ServiceOperator> operatorList=operatorService.getAllOperators();
            System.out.println("In service Operator List "+operatorList.size());
            for(ServiceOperator operator:operatorList){
                if (isSlotAvailable(operator, date, startTime)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean isSlotAvailable(ServiceOperator operator, Date date, int startTime) {
        // Retrieve existing appointments for the operator on the specified date
        List<Booking> existingBooking = bookingRepository.findByOperatorIdAndDateAndStartTime(operator.getId(), date, startTime);
        System.out.println(existingBooking);
        if(existingBooking.isEmpty()){
            Booking booking=new Booking();
            booking.setOperatorId(operator.getId());
            booking.setDate(date);
            booking.setStartTime(startTime);
            booking.setEndTime(startTime+1);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }
    //Get all the bookings in the system
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    //get the booking by particular id
    @Override
    public Booking getBookingById(Long Id){
        Optional<Booking> booking = bookingRepository.findById(Id);
        return booking.orElse(null);
    }

    //function to get the booked slots of a particular operator on a particular date
    @Override
    public String getAllBookedSlotsByIdAndDate(Long id,Date date){
        Optional<ServiceOperator> service_operator = operatorRepository.findById(id);
        String output="";
        if (service_operator.isPresent()) {
            List<Booking> bookingList=bookingRepository.findByOperatorIdAndDate(id,date);
            if(bookingList.isEmpty()){
                output += "There is no booking for the Service Operator on the mentioned date.";
                return output;
            }
            output+="The bookings for the given operator are: ";
            List<Integer> list=new ArrayList<>();
            for(Booking booking: bookingList){
               list.add(booking.getStartTime());
            }
            Collections.sort(list);
            StringBuilder outputBuilder = new StringBuilder(output);
            for(int l:list){
                outputBuilder.append(l).append("-").append(l + 1).append(", ");
            }
            outputBuilder.replace(outputBuilder.length() - 2, outputBuilder.length(), "");
            output = outputBuilder.toString();
            return output;
        }
        output+="Sorry, there is no service operator with the given id. Please check.";
        return output;
    }

    //function to get the open slots of a particular operator on a particular date
    @Override
    public String getAllOpenSlotsByIdAndDate(Long id,Date date){
        Optional<ServiceOperator> service_operator = operatorRepository.findById(id);
        String output="";
        if (service_operator.isPresent()) {
            List<Booking> bookingList=bookingRepository.findByOperatorIdAndDate(id,date);
            if(bookingList.isEmpty()){
                output += "The Service Operator is TOTALLY FREE on the mentioned date.";
                return output;
            }
            if(bookingList.size()==24){
                output += "The Service Operator is NOT FREE on the mentioned date.";
                return output;
            }
            output+="The open slots for the given operator: ";
            List<Integer> list=new ArrayList<>();
            for(Booking booking: bookingList){
                list.add(booking.getStartTime());
            }
            Collections.sort(list);
            StringBuilder outputBuilder = new StringBuilder(output);
            int s=0;
            for(int l:list){
                if(s==24) break;
                if(l==s) {
                    s = l + 1;
                    continue;
                }
                outputBuilder.append(s).append("-").append(l).append(", ");
                s=l+1;
            }
            outputBuilder.replace(outputBuilder.length() - 2, outputBuilder.length(), "");
            output = outputBuilder.toString();
            return output;
        }
        output+="Sorry, there is no service operator with the given id. Please check.";
        return output;
    }
    //Delete the booking by id
    @Override
    public boolean deleteBookingById(Long Id) {
        if (bookingRepository.existsById(Id)) {
            bookingRepository.deleteById(Id);
            return true;
        } else {
            return false;
        }
    }
    //Reschedule an appointment
    @Override
    public boolean rescheduleAppointmentById(Long id,Date date,int startTime,Date dateNew,int startTimeNew){
        System.out.println(id+" "+date+" "+startTime+" "+dateNew+" "+startTimeNew);
        Optional<ServiceOperator> service_operator = operatorRepository.findById(id);
        if (service_operator.isPresent()){
            ServiceOperator operator = service_operator.get();
            List<Booking> existingBooking = bookingRepository.findByOperatorIdAndDateAndStartTime(operator.getId(), date, startTime);
            if(existingBooking.isEmpty()) return false;
            if (!isSlotAvailable(operator, dateNew, startTimeNew)) return false;
            bookingRepository.deleteById(id);
            // Return true if the appointment is successfully scheduled
            return true;
        }
        //return false if id is not in the service operator list
        else return false;
    }
    //function to check if the date provided is in valid format or not
    @Override
    public boolean isValidDateFormat(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
