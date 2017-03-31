package ma.esith.ticketBookingNoDB.control;

public class NotFoundException extends Exception {

	public NotFoundException() {
		super("ID not found");
		
	}

	public NotFoundException(String msg) {
		super(msg);
		
	}

	
}
