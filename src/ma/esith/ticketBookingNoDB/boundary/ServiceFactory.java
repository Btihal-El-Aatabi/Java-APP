package ma.esith.ticketBookingNoDB.boundary;

import ma.esith.ticketBookingNoDB.control.CustomerServiceImpl;
import ma.esith.ticketBookingNoDB.control.EventServiceImpl;
import ma.esith.ticketBookingNoDB.control.ReservationServiceImpl;

public  class ServiceFactory {

	public static ReservationService getReservationService(){
		
		return new ReservationServiceImpl();
	}
	
	public static CustomerService getcustomerService(){
		
		return new CustomerServiceImpl();		
	}
	
	public static EventService getEventService(){
		
		return new EventServiceImpl();
	}
	
}
