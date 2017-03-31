package ma.esith.ticketBookingNoDB.entity;


import ma.esith.ticketBookingNoDB.entity.Address;


public class Customer extends Entity {
	private String firstName;
	private String lastName;
	private Address addresses = new Address();
	private CreditCard cCard;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getAddresses() {
		return addresses;
	}
	public void setAddresses(Address addresses) {
		this.addresses = addresses;
	}
	public CreditCard getcCard() {
		return cCard;
	}
	public void setcCard(CreditCard cCard) {
		this.cCard = cCard;
	}
	
	

}
