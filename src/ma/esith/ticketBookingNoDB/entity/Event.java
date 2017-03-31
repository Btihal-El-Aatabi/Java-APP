package ma.esith.ticketBookingNoDB.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event extends Entity{

	private String name;
	private List<LocalDateTime> dates=new ArrayList<LocalDateTime>();
	private List<String> places=new ArrayList<String>();
	private Double prices;
	private List<Integer> ticketsAvailable=new ArrayList<Integer>();
	private List<Integer> capacity=new ArrayList<Integer>();
	private List<Ticket> tickets=new ArrayList<Ticket>() ;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Double getPrices() {
		return prices;
	}
	
	public void setPrices(Double prices) {
		this.prices = prices;
	}

	public List<LocalDateTime> getDates() {
		return dates;
	}
	
	public void setDates(List<LocalDateTime> dates2) {
		this.dates = dates2;
	}
	
	public List<String> getPlaces() {
		return places;
	}
	
	public void setPlaces(List<String> places) {
		this.places = places;
	}

	public List<Integer> getTicketsAvailable() {
		return ticketsAvailable;
	}

	public void setTicketsAvailable(List<Integer> ticketsAvailable) {
		this.ticketsAvailable = ticketsAvailable;
	}

	public List<Integer> getCapacity() {
		return capacity;
	}

	public void setCapacity(List<Integer> capacity) {
		this.capacity = capacity;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
		
}
