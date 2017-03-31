package ma.esith.ticketBookingNoDB.control;

public class FullyBookedException extends Exception {
	
	public  FullyBookedException(){
		
		super("all dates of this Event are Fully Booked");
	}

}
