package ma.esith.ticketBookingNoDB.entity;



import ma.esith.ticketBookingNoDB.entity.Customer;

public class Address extends Entity {
	private String city;
	private boolean isSendingAddress;
	private Customer customer;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isSendingAddress() {
		return isSendingAddress;
	}
	public void setSendingAddress(boolean isSendingAddress) {
		this.isSendingAddress = isSendingAddress;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	

}
