package ma.esith.ticketBookingNoDB.control;

import java.util.ArrayList;
import java.util.List;

import ma.esith.ticketBookingNoDB.boundary.CustomerService;
import ma.esith.ticketBookingNoDB.entity.Customer;

/**
 * @author Btihal
 *
 */
public class CustomerServiceImpl implements CustomerService {
	/**
	 * Each customer Created, is stored in an ArrayList( customerTab).
	 */
	List<Customer> customerTab=new ArrayList<Customer>();
	
	/**
	 * @param idCustomer: Search the arrayList of customerTab, for the Cutsomer by ID 
	 * @throws NotFoundException: in case the Id entered
	 */
	@Override
	public Customer findCustomer(int idCustomer) throws NotFoundException {
		
		Customer c= new Customer();
		if(idCustomer>=3){
			throw new NotFoundException();
		}
		for(int i=0;i<customerTab.size();i++){
			
			if (customerTab.get(i).getId()==idCustomer){
				
				c=customerTab.get(i);
			}
		}
		
		
		return c;
	}

	/**
	 * Creates a Customer and adds them to the Arraylist of customers
	 */
	@Override
	public void createCustomer(Customer c) {
		customerTab.add(c);
		System.out.println("Customer Added to Customer Book");
	}

}
