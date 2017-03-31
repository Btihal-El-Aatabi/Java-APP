package ma.esith.ticketBookingNoDB.boundary;

import ma.esith.ticketBookingNoDB.control.FullyBookedException;
import ma.esith.ticketBookingNoDB.control.NotFoundException;
import ma.esith.ticketBookingNoDB.entity.Event;

public interface EventService {
	
	public void createEvent(Event e);
	public Event findEvent(int idEvent)throws NotFoundException;
	public void showEvents();
	public void showDates(int id) throws NotFoundException;
	public void updateEvent(Event e,int idDate,int orderedTickets);
	public void checkAvailabilityOfEvent(Event e, int idDate,int ordererTickets) throws FullyBookedException;
	}
