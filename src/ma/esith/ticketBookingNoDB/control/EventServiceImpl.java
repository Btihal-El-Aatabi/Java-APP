package ma.esith.ticketBookingNoDB.control;
/**
 * 
 * This Class deals with Events
 */
import java.util.ArrayList;
import java.util.List;


import ma.esith.ticketBookingNoDB.boundary.EventService;
import ma.esith.ticketBookingNoDB.entity.Event;

public class EventServiceImpl implements EventService {
	/**
	 * An arrayList of all events created.
	 */
	List<Event> eventTab= new ArrayList<Event>();
	/**
	 * Creates an Event, and adds it to the arraylist of events 
	 */
	@Override
	public void createEvent(Event e) {
		eventTab.add(e);
		System.out.println("Event Created");
	}

	/**
	 * @param idEvent
	 *  checks if Event exists in the arrayList 
	 */
	@Override
	public Event findEvent(int idEvent) throws NotFoundException {
		Event e= new Event();
		if(idEvent>=3){
			throw new NotFoundException();
		}
		for(int i=0;i<eventTab.size();i++){
			
			if (eventTab.get(i).getId()==(idEvent)){
				
				e=eventTab.get(i);
			}
		}
		return e;
	}

	/**
	 * Shows all events created, and their name.
	 * 
	 */
	@Override
	public void showEvents() {
		Event e=new Event();		
		for(int i=0;i<eventTab.size();i++){
			e=eventTab.get(i);
			System.out.println(e.getId()+"  for "+e.getName()+" event");
			}
	}
	/**
	 * Shows all the dates for the event selected, if the Id selected is valid,
	 *  if the ID does not exist, throws NotFoundException exception.
	 */
	@Override
	public void showDates(int id) throws NotFoundException {

		Event e;
		e = findEvent(id);
		System.out.println(" you have selected the "+e.getName()+" event, to select the number attributed to the date you want: ");
		for(int i=0;i<e.getDates().size();i++){
			System.out.println("      "+i +"	Date: "+e.getDates().get(i)+" place: "+e.getPlaces().get(i));
			
		}	 
	}
	/**
	 * updates the number of tickets available, after creation of a reservation(i.e ticket).
	 */
	@Override
	public void updateEvent(Event e,int idDate, int orderedTickets) {
		int availableTickets= e.getTicketsAvailable().get(idDate)-orderedTickets;
		e.getTicketsAvailable().set(idDate, availableTickets);
	}
	
	
	/**
	 * Checks if a Date of an event is available
	 * @param e The event the customer has picked.
	 * @param idDate the Id of the date the customer has picked.
	 * @param ordererTickets The number 
	 * 
	 */
	@Override
	public void checkAvailabilityOfEvent(Event e,int idDate,int ordererTickets) throws FullyBookedException {
			
			if(idDate>=3){
				throw new FullyBookedException();
				}
		
			int availableTickets= e.getTicketsAvailable().get(idDate);
			int r= availableTickets - ordererTickets;
		
			if(r<0){					
				idDate++;
				checkAvailabilityOfEvent(e,idDate,ordererTickets);
				
				}
			if(r>=0)
			System.out.println("the event you have selected is available on :  "+e.getDates().get(idDate));
	}

}
