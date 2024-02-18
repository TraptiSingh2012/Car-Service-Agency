Read Me: 
•	The project is built on the Java, Spring boot 
•	The database is the H2 database.

Problem:
•	I want to create an online scheduler website for the car service agency. The agency has many service operators who must take in appointments for customers for a limited time.
•	A Service operator can take 1-hour appointments for all 24 hours, and they are available 24 x 7 x 365 days (sorry no vacation for them).
•	If a Service operator is booked for a specific time slot, he obviously cannot take any more customer appointments.

•	I want to be able to do the following:
- Ability to 'book' an appointment slot by specific Operator or any
- Ability to 'reschedule' or 'cancel' appointment
- Ability to show all booked appointments of an operator. This should display all individual appointments i.e. 1-2, 2-3, 3-4
- Ability to show open slots of appointments for an operator. This should merge all consecutive open slots and show as 4-14 instead of 4-5, 5-6 ... 13-14.

Assumptions: 
•	Number of service operators can be any.
•	The booking is done for one hour by the customer (by default).
•	24 hour format is used to specify the time.

The following APIs are implemented here:
1.	Make a service operator
Request: POST
URL: {url}/api/oerators
Request Parameters: 
name: name of the operator(have to pass)
 
2.	Get all service operator
Request: GET
URL : {url}/api/oerators
	 
3.	Make a booking/appointment
Request: POST
URL: {url}/api/booking
Request Parameter: 
	date: date of the appointment(has to pass)
	start_time: start_time of the appointment(has to pass)
	service_operator_id: operator id(optional)
	(if service_operator_id will not pass then any operator id is taken  at random)
 
4.	GET all the bookings
Request: GET
URL: {url}/api/booking
 
5.	GET a booking by Id
Request: GET
URL: {url}/api/booking/{id}
 
6.	DELETE a booking
Request: DELETE
URL: {url}/api/booking/{id}
 
7.	Reschedule a booking/appointment
Request: PUT
URL: {url}/api/booking/{id}
Request Parameter: 
	date: date of the original appointment (has to pass)
	start_time: start_time of the original appointment (has to pass)
	date_new: date of the new appointment (has to pass)
	start_time_new: start_time of the new appointment (has to pass)
 
8.	Get all the booked slots of an operator 
Request: GET
URL: {url}/api/booking/bookedSlots/{operator_id}
Request Parameter: 
	date: particular date for appointments (has to pass)
 
9.	Get all the open slots of an operator 
Request: GET
URL: {url}/api/booking/openSlots/{operator_id}
Request Parameter: 
	date: particular date for open slots (has to pass)
 
