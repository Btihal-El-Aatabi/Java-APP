package ma.esith.ticketBookingNoDB.boundary;

import ma.esith.ticketBookingNoDB.control.NotFoundException;
import ma.esith.ticketBookingNoDB.entity.Customer;


public interface CustomerService {

	

	public Customer findCustomer(int idCustomer)throws NotFoundException;
	public void createCustomer(Customer c);
	
}
