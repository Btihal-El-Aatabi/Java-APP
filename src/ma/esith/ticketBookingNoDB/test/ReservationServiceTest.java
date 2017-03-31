package ma.esith.ticketBookingNoDB.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import ma.esith.ticketBookingNoDB.boundary.CustomerService;
import ma.esith.ticketBookingNoDB.boundary.EventService;
import ma.esith.ticketBookingNoDB.boundary.ReservationService;
import ma.esith.ticketBookingNoDB.boundary.ServiceFactory;
import ma.esith.ticketBookingNoDB.control.FullyBookedException;
import ma.esith.ticketBookingNoDB.control.NotFoundException;
import ma.esith.ticketBookingNoDB.entity.Address;
import ma.esith.ticketBookingNoDB.entity.Customer;
import ma.esith.ticketBookingNoDB.entity.Event;
import ma.esith.ticketBookingNoDB.entity.Ticket;

public class ReservationServiceTest {
	
	private static ReservationService serviceR;
	private static EventService 	  serviceE;
	private static CustomerService    serviceC;
	
	
	public static void main(String[] args) throws NotFoundException, FullyBookedException{
		
		ReservationServiceTest test = new ReservationServiceTest();
		System.out.println("\t\t\t Creation of customer1 and customer2 : ");
		test.testCreatCustomer();//we create the 2 customers
		System.out.println("\t\t\t Creation of events : ");
		test.testCreatEvents();//we create the 3 events 
		System.out.println("\t\t\t  Customer1 creates a reservation");
		test.testCreateReservation1();//we will pick date 1
		System.out.println("\t\t\t  Customer2 creates a reservation");
		test.testCreateReservation2();//we will select CustomerID number 2 
		System.out.println("\t\t\t  Customer 1 cancels their reservation");
		// we suppose the cancelling date is between 14 and 4 days, so they should receive 50% refund
		test.testCancelReservation(1);
	}
	
	public ReservationServiceTest(){
		serviceR= ServiceFactory.getReservationService();
		serviceC= ServiceFactory.getcustomerService();
		serviceE= ServiceFactory.getEventService();
	}
	/**
	 * Creates a Reservation i.e  Ticket.Dynamically (through the main function). 
	 * The customer enters their ID,
	 * Selects the ID of the Event They want, 
	 * Selects the Date They want to attend the Event on,
	 * Enters the Number of Tickets they want to order,
	 * We check if the amount of tickets they want is available for order,  
	 * @throws NotFoundException : Checks if the Id (of the customer/ event/date) Entered Exists,until the customer enters a valid ID 
	 * @throws FullyBookedException :In case if the Date the Customer wants to attend the event on,
	 * is Fully Booked.
	 */
	public void testCreateReservation2() throws NotFoundException, FullyBookedException{
		Ticket t= new Ticket();
		Event e= new Event();
		Customer c= new Customer();
		LocalDateTime dt;
		int orderedTickets=0;
		int idDate=0;
		boolean done=false;
		while(!done){// to check for a valid CustomerID
		
			// customer enters their ID:
		System.out.println("Enter your CustomerId:");
		Scanner reader0 = new Scanner(System.in);  
		int a = reader0.nextInt();
		

		try{
			// we look for the customer by their ID in the list of customers we have:
			c=serviceC.findCustomer(a);
			
			done=true;
		}catch(NotFoundException nfe){
			System.out.println(nfe.getMessage());
			}
		}
		
		boolean order= false;
		//While Loop repeated in case of : -inserting a wrong id. -Fully booked Events
		while(!order){
			
			//display of events (id + name)
			
			try{
				serviceE.showEvents();
			// picks the Id of the Event he wants 
			System.out.println("Pick an event id :" );
			Scanner reader1 = new Scanner(System.in);  
			reader1.hasNextInt();
			int idEvent = reader1.nextInt();// if there is another number     
			e=serviceE.findEvent(idEvent);//we select the event, if it exists
			
			//display of the dates and places of the event selected 
			serviceE.showDates(idEvent);
		
			// the customer selects the date of the event they want
			System.out.println("pick the id for the date and place you want:");
			Scanner reader2 = new Scanner(System.in);  
			idDate = reader2.nextInt();
			
			if(idDate>=3){
				throw new NotFoundException();
			}
			

			//the customer selects the number of tickets they want		
			System.out.println("select the number of tickets you want :");
			Scanner reader3 = new Scanner(System.in);  
			orderedTickets = reader3.nextInt();
			
			System.out.println("you have selected the event: "+e.getName()+", on :"+e.getDates().get(idDate));

			// we compare the tickets they ordered to the tickets available, and make suggestions accordingly
				serviceE.checkAvailabilityOfEvent(e,idDate,orderedTickets);
				dt= e.getDates().get(idDate);
				t.setDate(dt);
				break;
			}catch(FullyBookedException fbe){// If all the dates of the event are fully booked
				System.out.println(fbe.getMessage());			
				System.out.println("select : - 1 to order for an another event/n           - 2 in order to cancel: 0");
				Scanner reader4 = new Scanner(System.in);  
				int confirm = reader4.nextInt();
				
				switch(confirm) {
				   case 1 :
					   order= false;
				      break;
				   case 2 :
					   System.out.println("order cancelled");
					   return;
				   default :
					   System.out.println("order cancelled");
					   return;
				    	}
				}
			catch(NotFoundException nfe){//if an the idEvent or idDate are not found
				System.out.println(nfe.getMessage());
				order=false;
				}
			}
		
		double charge= orderedTickets*(e.getPrices());
		System.out.println(" the amount of your order is: "+ charge );
		
		System.out.println("in order to confirm your order select: 1,\n               in order to cancel: 0");
		Scanner reader5 = new Scanner(System.in);  
		int finishOrder = reader5.nextInt();

		switch(finishOrder) {
		   case 1 :
			   
			   t.setEvent(e);
			   t.setCustomer(c);
			   t.setId(2);
			   t.setNumberOfTickets(orderedTickets);
			   t.setAddress(c.getAddresses());
			   t.setPrice(charge);
			   System.out.println("Successfull Order!");
			   serviceR.createReservation(t);
			   e.getTickets().add(t);
			   serviceE.updateEvent(e,idDate, orderedTickets);
			   
		      break;
		      default :
				System.out.println("order cancelled");
		
		    	}	
	}

	
	/**
	 * Generates a ticket, statically.
	 * We set the customer(by id), the event (by id) and Date of the event,
	 * We check for availability, and show the customer the charge and ask them to confirm their order.
	 * @throws NotFoundException : to verify if the customer ID and event ID exists.
	 * @throws FullyBookedException : to verify if the date of the event we selected isn't sold out.
	 */
	public void testCreateReservation1(){
		Ticket t=new Ticket();
		try {
			t.setId(1); 							//set ID of the ticket
			Customer c= serviceC.findCustomer(2);
			t.setCustomer(c);						// set the customer
			Event e=serviceE.findEvent(2);
			t.setEvent(e);							// set the event 
			Address add= c.getAddresses();
			t.setAddress(add);						//set the shipping address
			serviceE.showDates(2);
			LocalDateTime date;
			
			System.out.println("pick the id for the date and place you want:");
			Scanner reader2 = new Scanner(System.in);  
			int idDate = reader2.nextInt();

			if(idDate>=3){
				throw new NotFoundException();
			}			  
			int orderedTickets = 5;
			t.setNumberOfTickets(orderedTickets);   // set the number of tickets ordered for the reservation
			serviceE.checkAvailabilityOfEvent(e, idDate, orderedTickets);
			date =  e.getDates().get(idDate);
			t.setDate(date);
			double charge = orderedTickets * e.getPrices();
			t.setPrice(charge);						//set the price of the tickets
			System.out.println(" You have ordered : "+ orderedTickets+" Tickets, the amount of your order is: "+ charge +" €.");
			
			serviceE.updateEvent(e, idDate, orderedTickets);
			serviceR.createReservation(t);
		} catch (NotFoundException nfe) {
			// TODO Auto-generated catch block
			System.out.println(nfe.getMessage());
			//e.printStackTrace();
		}
		catch(FullyBookedException fbe){
			System.out.println(fbe.getMessage());
			//fbe.printStackTrace();
		}
		
	}
	
	/**
	 * We set the cancellingTime, we look for the ticket by ID, We calculate the difference,
	 *  and show the customer if they will get a refund or not 
	 * 
	 * @param idTicket to identify which ticket to cancel.
	 * @throws NotFoundException to verify if the idTicket exists.
	 */
	public void testCancelReservation(int idTicket) throws NotFoundException{
		try{
		LocalDate date = LocalDate.of(2017, 06,10);
		LocalTime time= LocalTime.of(16,00);
		LocalDateTime cancellingDate = LocalDateTime.of(date, time);
		
		Ticket t= serviceR.findReservation(idTicket);
		serviceR.cancelReservation(t,cancellingDate);
		}catch(NotFoundException nfe){
			System.out.println("The ticket Id you inserted is: "+idTicket+" , ");
			System.out.println(nfe.getMessage());
			}
	}
	
	
	/**
	 * Modifies the ticket Order. In case the customer wants more tickets,
	 * Provided the difference between the modification date and the ticket date is more than 14 days
	 * if difference is less than 14 days, they will just have to cancel the order
	 * @param idTicket
	 * @throws NotFoundException
	 * @throws FullyBookedException
	 */
	public void testModifyReservation(int idTicket) throws NotFoundException, FullyBookedException{
        try {
            Ticket t= serviceR.findReservation(idTicket);
            LocalDate date = LocalDate.of(2017,07,12);
            LocalTime time = LocalTime.of(17, 30);
            LocalDateTime modifyDate = LocalDateTime.of(date, time);
            LocalDateTime ticketDate = t.getDate();
            double diffInDays = (int) java.time.Duration.between(modifyDate, ticketDate).toDays();
            if (diffInDays<14){
                System.out.println("No modification is allowed, would you like cancelling the reservation? ");
                System.out.println("Press 1 to cancel the reservation \n Press 2 to exit");
                Scanner reader1 = new Scanner(System.in);  
                int n = reader1.nextInt();
    			reader1.close();
                if (n==1){
                    serviceR.cancelReservation(t,modifyDate);
                }else{
                    System.out.println("See you soon at the event  ;)   ");
                }
            }else{
                System.out.println("Press 1 to confirm changing order");
                Scanner reader2 = new Scanner(System.in);  
                int m = reader2.nextInt();
    			reader2.close();

                Event e = t.getEvent();

                switch (m){
                case 1 :
                    System.out.println("Please type the ticket's id");
                    Scanner reader5 = new Scanner(System.in);
                    int ticketid = reader5.nextInt();
                    serviceR.findReservation(ticketid);
                    serviceE.showDates(e.getId());
                    System.out.println("Pick a date id");
                    Scanner reader6 = new Scanner(System.in);
                    int idDate = reader6.nextInt();
                    System.out.println("Enter the number of tickets you would like to order");
                    Scanner reader7 = new Scanner(System.in);
                    int nbTickets = reader7.nextInt();
                    serviceE.checkAvailabilityOfEvent(e, idDate, nbTickets);
                default:
                  break;
                    
                }
            }
        } catch (FullyBookedException fbe) {
            fbe.printStackTrace();
        }catch (NotFoundException nfe){
            nfe.printStackTrace();
        }
    }
	/**
	 * Verifies if a Ticket ID is valid or not. if not found it catches the NotFoundException
	 * @param idTicket 
	 */
	public void testFindReservation(int idTicket){
		try{
		Ticket t= serviceR.findReservation(idTicket);
		String eName= t.getEvent().getName();
		LocalDateTime date =t.getDate();
		System.out.println("Your ticket: "+t.getId()+" ,is valid for Event: "+eName+" at "+date);	
		} catch (NotFoundException e) {
			
			System.out.println("The ticket Id you inserted : "+ idTicket +" , is not valid ");
		}
		
	}
	
	/**
	 * Creates 3 events. for each Event we set an ID, 
	 * assigns 3 places, 3 dates,3 prices, the capacity,
	 * the number of tickets available
	 * and stores them in an arrayList
	 */
	public void testCreatEvents(){
		Event e1= new Event();
		Event e2 = new Event();
		Event e3 = new Event();
		// EVENT 1 
		
		e1.setId(1);                 	   	     //id 1
		e1.setName("Football Match"); 	         //name
		for(int i=0;i<=2;i++){
		e1.getTicketsAvailable().add(50+i*10);   //TicketsAvailable
		e1.getCapacity().add(600+(i*35));        //Capacity
		e1.setPrices(150.00-(i*15));             //price
		}
		LocalDate date1 = LocalDate.of(2017, 06, 12);
		LocalDate date2 = LocalDate.of(2017, 06, 18);
		LocalDate date3 = LocalDate.of(2017, 06, 22);
		LocalTime t1= LocalTime.of(19,00);
		LocalTime t2= LocalTime.of(16,00);
		LocalTime t3= LocalTime.of(17,30);

		LocalDateTime dt1= LocalDateTime.of(date1,t1);
		LocalDateTime dt2= LocalDateTime.of(date2,t2);
		LocalDateTime dt3= LocalDateTime.of(date3,t3);

		e1.getDates().add(dt1);        // storing of Date1 
		e1.getDates().add(dt2);        // storing of Date2
		e1.getDates().add(dt3);        // storing of Date3
		
		e1.getPlaces().add("Edinburg");        // storing of place 1 
		e1.getPlaces().add("Brighton ");        // storing of place 2
		e1.getPlaces().add("Manchester");      // storing of place 3
		serviceE.createEvent(e1);//                Event 1 Registered
		
		// EVENT 2
		e2.setId(2);                  //id 
		e2.setName("Macbeth Play ");
		for(int i=0;i<=2;i++){//name
		e2.getTicketsAvailable().add(4+(i*15));   //TicketsAvailable
		e2.getCapacity().add(250+(i*35));          //Capacity
		e2.setPrices(170.00-(i*17)); 		//price
		} 
		date1 = LocalDate.of(2017, 06, 12);
		date2 = LocalDate.of(2017, 06, 18);
		date3 = LocalDate.of(2017, 06, 22);
		t1= LocalTime.of(19,00);
		t2= LocalTime.of(16,00);
		t3= LocalTime.of(17,30);

		dt1= LocalDateTime.of(date1,t1);
		dt2= LocalDateTime.of(date2,t2);
		dt3= LocalDateTime.of(date3,t3);

		e2.getDates().add(dt1);        // storing of Date1
		e2.getDates().add(dt2);        // storing of Date2
		e2.getDates().add(dt3);        // storing of Date3
		
		e2.getPlaces().add("London");          // storing of place 1
		e2.getPlaces().add("Birmingham");        // storing of place 2
		e2.getPlaces().add("Cardiff");           // storing of place 3
		
		serviceE.createEvent(e2);//                Event 2 Registered
		
		// EVENT 3
		
		e3.setId(3);                 	   	     //id 3
		e3.setName("Hackathon "); 	         //name
		for(int i=0;i<=2;i++){
		e3.getTicketsAvailable().add(50+i*10);   //TicketsAvailable
		e3.getCapacity().add(600+(i*35));        //Capacity
		e3.setPrices(150.00-(i*15));             //price
		}
		 date1 = LocalDate.of(2017, 04, 13);
		 date2 = LocalDate.of(2017, 05, 13);
		 date3 = LocalDate.of(2017, 06, 13);
		 t1= LocalTime.of(19,00);
		 t2= LocalTime.of(16,00);
		 t3= LocalTime.of(17,30);

		 dt1= LocalDateTime.of(date1,t1);
		 dt2= LocalDateTime.of(date2,t2);
		 dt3= LocalDateTime.of(date3,t3);

		e3.getDates().add(dt1);        // storing of Date1 
		e3.getDates().add(dt2);        // storing of Date2
		e3.getDates().add(dt3);        // storing of Date3
		
		e3.getPlaces().add("Gent");        // storing of place 1 
		e3.getPlaces().add("Mons ");        // storing of place 2
		e3.getPlaces().add("Bruges");      // storing of place 3
		
		serviceE.createEvent(e3);//                Event 3 Registered
		
	}
	/**
	 * Creates 2 customers 
	 */
	public void testCreatCustomer(){
		//1st Customer
		Customer c1= new Customer();
		c1.setId(1);
		c1.setFirstName("Luke");
		c1.setLastName("Skywalker");
		
		//2nd Customer
		Customer c2 = new Customer();
		c2.setId(2);
		c2.setFirstName("Han");
		c2.setLastName("Solo");
		
		//address of the 1st Customer
		Address add1 = new Address();
		add1.setId(1);
		add1.setSendingAddress(true);
		add1.setCity("Tatooine");
		add1.setCustomer(c1);
		c1.setAddresses(add1);
		serviceC.createCustomer(c1);//creation of the 1st customer
		
		//address of the 2nd Customer
		Address add2= new Address();
		add2.setId(2);
		add2.setSendingAddress(true);
		add2.setCity("Galactic City");
		add2.setCustomer(c2);
		c2.setAddresses(add2);
		serviceC.createCustomer(c2);//creation of the 2nd customer
	}
	/**
	 * Checks if customer is registered
	 * @param id 
	 */
	public void testFindCutomer(int id){
		try {
			Customer c= serviceC.findCustomer(id);
			System.out.println("id: "+c.getId()+"name: "+c.getFirstName()+"  "+c.getLastName());
		} catch (NotFoundException e) {

			System.out.println(e.getMessage());
		}
	}
		

}
