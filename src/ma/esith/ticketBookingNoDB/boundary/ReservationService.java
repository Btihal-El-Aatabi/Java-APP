package ma.esith.ticketBookingNoDB.boundary;

import java.time.LocalDateTime;

import ma.esith.ticketBookingNoDB.control.NotFoundException;
import ma.esith.ticketBookingNoDB.entity.Ticket;

public interface ReservationService {
	
	public void createReservation(Ticket t);
	public void cancelReservation(Ticket t,LocalDateTime cancellingDate);
	public void modifyReservation(Ticket t,LocalDateTime modificationDate);
	public Ticket findReservation(int idTicket)throws NotFoundException; 
		
}
