import javax.swing.*;
import java.util.ArrayList;

public class Customer {

	String PPS ="";
	String surname = "";
	String firstName = "";
	String DOB ="";
	String customerID = "";
	String password = "";
	
	ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount> ();

	//Blank constructor
	public Customer()
	{
		this.PPS = "";
		this.surname = "";
		this.firstName = "";
		this.DOB = "";
		this.customerID = "";
		this.password = "";
		this.accounts = null;
	}
	
	//Constructor with details
	public Customer(String PPS, String surname, String firstName, String DOB, String customerID, String password, ArrayList<CustomerAccount> accounts)
	{
		this.PPS = PPS;
		this.surname = surname;
		this.firstName = firstName;
		this.DOB = DOB;
		this.customerID = customerID;
		this.password = password;;
		this.accounts = accounts;
	}
	
	//Accessor methods
	public String getPPS()
	{
		return this.PPS;
	}
	
	public String getSurname()
	{
		return this.surname;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getDOB()
	{
		return this.DOB;
	}
	
	public String getCustomerID()
	{
		return this.customerID;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public ArrayList<CustomerAccount> getAccounts()
	{
		return this.accounts;
	}
	
	//mutator methods
	public void setPPS(String PPS)
	{
		this.PPS = PPS;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setDOB(String DOB)
	{
		this.DOB = DOB;
	}
	


	
	public void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setAccounts(ArrayList<CustomerAccount> accounts)
	{
		this.accounts = accounts;
	}
	
	public String toString()
	{
		return "PPS number = " + this.PPS + "\n"
				+ "Surname = " + this.surname + "\n"
				+ "First Name = " + this.firstName + "\n"
				+ "Date of Birth = " + this.DOB + "\n"
				+ "Customer ID = " + this.customerID;
			
	}

	public static Customer validateNewCustomer(Customer customerToValidate) {
		boolean passwordCheck = false;
		while (!passwordCheck) {
			customerToValidate.password = JOptionPane.showInputDialog(null, "Enter 7 character Password;");

			if (customerToValidate.password.length() < 7) {
				JOptionPane.showMessageDialog(null, "Password must be at least 7 characters long", "Password Error", JOptionPane.OK_OPTION);
			}else {
				passwordCheck = true;
			}
		}

		ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount> ();
		customerToValidate.setAccounts(accounts);
		return customerToValidate;
	}

	public static Customer validateExistingCustomer(ArrayList<Customer> customerList) {
		boolean customerParam = false;
		boolean customerPass = false;
		Customer customer = null;

		while (!customerParam) {
			Object customerID = JOptionPane.showInputDialog(null, "Enter Customer ID:");

			for (Customer aCustomer : customerList) {
				if (aCustomer.getCustomerID().equals(customerID))//search customer list for matching customer ID
				{
					customerParam = true;
					customer = aCustomer;
				} else {
					int reply = JOptionPane.showConfirmDialog(null, "User not found. Try again?", "Not Found", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {

					} else if (reply == JOptionPane.NO_OPTION) {
						customerParam = true;
						customerPass = true;
//						mainFrame.dispose();
//						menuStart();
					}
				}
			}
		}
		while(!customerPass)
		{
			Object customerPassword = JOptionPane.showInputDialog(null, "Enter Customer Password");

			if(!customer.getPassword().equals(customerPassword))//check if customer password is correct
			{
				int reply  = JOptionPane.showConfirmDialog(null, "Incorrect password. Try again?", "Password Error", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
				}
				else if(reply == JOptionPane.NO_OPTION){
				}
				} else {
					customerPass = true;
				}
		}
		return customer;
	}
	
}
