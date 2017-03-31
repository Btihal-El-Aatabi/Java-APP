package ma.esith.ticketBookingNoDB.control;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ma.esith.ticketBookingNoDB.boundary.ReservationService;
import ma.esith.ticketBookingNoDB.entity.Ticket;

public class ReservationServiceImpl implements ReservationService{
	
	List<Ticket> ticketTab=new ArrayList<Ticket>();
	
	@Override
	public void createReservation(Ticket t) {
		ticketTab.add(t);
		System.out.println("ticket created!");
	}

	@Override
	public void cancelReservation(Ticket t,LocalDateTime cancellingDate) {
		
		LocalDateTime ticketDate= t.getDate();
		double refund=t.getPrice();
		double diffInDays =(int) java.time.Duration.between(cancellingDate, ticketDate).toDays();
		if(diffInDays<14){
			if(diffInDays>=4){
				refund= refund/2 ;
				System.out.println("You will get a 50% refund of the initial ticket's price, a total of: "+refund);
				
			}else{
				System.out.println("You will not receive a refund, you'll be charged a total of : "+ refund);
				}	
		}else{
			
			System.out.println("You will receive a full refund, a total of : "+refund);
				
			}
	}

	@Override
	public void modifyReservation(Ticket t,LocalDateTime modificationDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ticket findReservation(int idTicket) throws NotFoundException {
		// TODO Auto-generated method stub
		Ticket t= new Ticket();
		if(idTicket>=3){
			throw new NotFoundException();
		}
		for(int i=0;i<ticketTab.size();i++){
			
			if (ticketTab.get(i).getId()==idTicket){
				
				t=ticketTab.get(i);
			}
		}
		
		return t;
	}

}
