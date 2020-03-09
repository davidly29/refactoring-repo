import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class CustomerCurrentAccount extends CustomerAccount 
{
	ATMCard atm;
	
public CustomerCurrentAccount()
{
	super();
	this.atm = null;
	
}

public CustomerCurrentAccount(ATMCard atm, String number, double balance, ArrayList<AccountTransaction> transactionList)
{
	super(number, balance, transactionList);	
	this.atm = atm;
}

public ATMCard getAtm()
{
	return this.atm;
}

public void setAtm(ATMCard atm)
{
	this.atm = atm;
}

public static boolean validateAccount(CustomerAccount acc) {
	int count = 3;
	int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
	boolean loop = true;
	boolean on = true;

	while(loop)
	{
		if(count == 0)
		{
			JOptionPane.showMessageDialog(null, "Pin entered incorrectly 3 times. ATM card locked."  ,"Pin",  JOptionPane.INFORMATION_MESSAGE);
			((CustomerCurrentAccount) acc).getAtm().setValid(false);
			// customer(loggedInCustomer);
			return false;
		}

		String Pin = JOptionPane.showInputDialog(null, "Enter 4 digit PIN;");
		int i = Integer.parseInt(Pin);

		if(on)
		{
			if(checkPin == i)
			{
				loop = false;
				JOptionPane.showMessageDialog(null, "Pin entry successful" ,  "Pin", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else
			{
				count --;
				JOptionPane.showMessageDialog(null, "Incorrect pin. " + count + " attempts remaining."  ,"Pin",  JOptionPane.INFORMATION_MESSAGE);
			}

		}

	}
	return true;
	}

	public static void makeDeposit(CustomerAccount acc, double balance) {
		String euro = "\u20ac";
		acc.setBalance(acc.getBalance() + balance);
		Date date = new Date();
		String date2 = date.toString();
		String type = "Lodgement";
		double amount = balance;

		AccountTransaction transaction = new AccountTransaction(date2, type, amount);
		acc.getTransactionList().add(transaction);

		JOptionPane.showMessageDialog(null, balance + euro + " added do you account!" ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "New balance = " + acc.getBalance() + euro ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
	}

	public static void withdraw(CustomerAccount acc, double withdraw) {
		String euro = "\u20ac";
		acc.setBalance(acc.getBalance()-withdraw);
		//recording transaction:
		//		String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		Date date = new Date();
		String date2 = date.toString();

		String type = "Withdraw";
		double amount = withdraw;

		AccountTransaction transaction = new AccountTransaction(date2, type, amount);
		acc.getTransactionList().add(transaction);

		JOptionPane.showMessageDialog(null, withdraw + euro + " withdrawn." ,"Withdraw",  JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "New balance = " + acc.getBalance() + euro ,"Withdraw",  JOptionPane.INFORMATION_MESSAGE);
	}

}