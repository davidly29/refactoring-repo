
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Menu extends JFrame {
    private ArrayList<Customer> customerList = new ArrayList<Customer>();
    private int position = 0;
    private String password;
    private Customer customer = null;
    private CustomerAccount acc = new CustomerAccount();
    JFrame mainFrame, secondaryFrame;
    JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
    JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
    JLabel customerIDLabel, passwordLabel;
    JTextField customerIDTextField, passwordTextField;
    Container content;
    Customer loggedInCustomer;
    JPanel panel2;
    JButton add;
    String PPS, firstName, surname, DOB, CustomerID;

    public static void main(String[] args) {
        Menu driver = new Menu();
        driver.menuStart();
    }

    public void menuStart() {
        mainFrame = new JFrame("User Type");
        mainFrame.setSize(400, 300);
        mainFrame.setLocation(200, 200);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        JPanel userTypePanel = new JPanel();
        final ButtonGroup userType = new ButtonGroup();
        JRadioButton radioButton;
        userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
        radioButton.setActionCommand("Customer");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("Administrator"));
        radioButton.setActionCommand("Administrator");
        userType.add(radioButton);

        userTypePanel.add(radioButton = new JRadioButton("New Customer"));
        radioButton.setActionCommand("New Customer");
        userType.add(radioButton);

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continuePanel.add(continueButton);

        Container content = mainFrame.getContentPane();
        content.setLayout(new GridLayout(2, 1));
        content.add(userTypePanel);
        content.add(continuePanel);


        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String user = userType.getSelection().getActionCommand();
                //if user selects NEW CUSTOMER--------------------------------------------------------------------------------------
                if (user.equals("New Customer")) {
                    newCustomer();
                }
                //if user select ADMIN----------------------------------------------------------------------------------------------
                if (user.equals("Administrator")) {
                    administrator();
                }
                //if user selects CUSTOMER ----------------------------------------------------------------------------------------
                if (user.equals("Customer")) {
                    customer();
                }
                //-----------------------------------------------------------------------------------------------------------------------
            }
        });
        mainFrame.setVisible(true);
    }

    public void administrator() {
        boolean goContinue = Administration.verifyAdmin();

        if (goContinue) {
            admin();
        }
    }

    public void customer() {
        Customer customer = Customer.validateExistingCustomer(customerList);

        if (customer != null) {
            mainFrame.dispose();
            customer(customer);
        }
    }

    public void newCustomer() {
        mainFrame.dispose();
        secondaryFrame = new JFrame("Create New Customer");
        secondaryFrame.setSize(400, 300);
        secondaryFrame.setLocation(200, 200);
        secondaryFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        Container content = secondaryFrame.getContentPane();
        content.setLayout(new BorderLayout());

        firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
        surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
        pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
        dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
        firstNameTextField = new JTextField(20);
        surnameTextField = new JTextField(20);
        pPSTextField = new JTextField(20);
        dOBTextField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(surnameLabel);
        panel.add(surnameTextField);
        panel.add(pPPSLabel);
        panel.add(pPSTextField);
        panel.add(dOBLabel);
        panel.add(dOBTextField);

        panel2 = new JPanel();
        add = new JButton("Add");

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PPS = pPSTextField.getText();
                firstName = firstNameTextField.getText();
                surname = surnameTextField.getText();
                DOB = dOBTextField.getText();
                password = "";
                CustomerID = "ID" + PPS;

                add.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        secondaryFrame.dispose();
                        ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
                        Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password, accounts);
                        Customer.validateNewCustomer(customer);
                        customerList.add(customer);

                        JOptionPane.showMessageDialog(mainFrame, "CustomerID = " + customer.customerID + "\n Password = " + customer.password, "Customer created.", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.dispose();
                        menuStart();
                    }
                });
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondaryFrame.dispose();
                menuStart();
            }
        });

        panel2.add(add);
        panel2.add(cancel);
        content.add(panel, BorderLayout.CENTER);
        content.add(panel2, BorderLayout.SOUTH);
        secondaryFrame.setVisible(true);
    }

    public void admin() {
        dispose();

        mainFrame = new JFrame("Administrator Menu");
        mainFrame.setSize(400, 400);
        mainFrame.setLocation(200, 200);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);

        JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteCustomer = new JButton("Delete Customer");
        deleteCustomer.setPreferredSize(new Dimension(250, 20));
        deleteCustomerPanel.add(deleteCustomer);

        JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteAccount = new JButton("Delete Account");
        deleteAccount.setPreferredSize(new Dimension(250, 20));
        deleteAccountPanel.add(deleteAccount);

        JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bankChargesButton = new JButton("Apply Bank Charges");
        bankChargesButton.setPreferredSize(new Dimension(250, 20));
        bankChargesPanel.add(bankChargesButton);

        JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton interestButton = new JButton("Apply Interest");
        interestPanel.add(interestButton);
        interestButton.setPreferredSize(new Dimension(250, 20));

        JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editCustomerButton = new JButton("Edit existing Customer");
        editCustomerPanel.add(editCustomerButton);
        editCustomerButton.setPreferredSize(new Dimension(250, 20));

        JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton navigateButton = new JButton("Navigate Customer Collection");
        navigatePanel.add(navigateButton);
        navigateButton.setPreferredSize(new Dimension(250, 20));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton summaryButton = new JButton("Display Summary Of All Accounts");
        summaryPanel.add(summaryButton);
        summaryButton.setPreferredSize(new Dimension(250, 20));

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accountButton = new JButton("Add an Account to a Customer");
        accountPanel.add(accountButton);
        accountButton.setPreferredSize(new Dimension(250, 20));

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Exit Admin Menu");
        returnPanel.add(returnButton);

        JLabel label1 = new JLabel("Please select an option");

        content = mainFrame.getContentPane();
        content.setLayout(new GridLayout(9, 1));
        content.add(label1);
        content.add(accountPanel);
        content.add(bankChargesPanel);
        content.add(interestPanel);
        content.add(editCustomerPanel);
        content.add(navigatePanel);
        content.add(summaryPanel);
        content.add(deleteCustomerPanel);
        content.add(returnPanel);


        bankChargesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.dispose();
                    admin();

                } else {
                    while (!found) {
                        Object customerID = JOptionPane.showInputDialog(mainFrame, "Customer ID of Customer You Wish to Apply Charges to:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;

                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                found = false;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                mainFrame.dispose();
                                found = true;
                                admin();
                            }
                        } else {
                            mainFrame.dispose();
                            mainFrame = new JFrame("Administrator Menu");
                            mainFrame.setSize(400, 300);
                            mainFrame.setLocation(200, 200);
                            mainFrame.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    System.exit(0);
                                }
                            });
                            mainFrame.setVisible(true);


                            JComboBox<String> box = new JComboBox<String>();
                            for (int i = 0; i < customer.getAccounts().size(); i++) {


                                box.addItem(customer.getAccounts().get(i).getNumber());
                            }


                            box.getSelectedItem();

                            JPanel boxPanel = new JPanel();
                            boxPanel.add(box);

                            JPanel buttonPanel = new JPanel();
                            JButton continueButton = new JButton("Apply Charge");
                            JButton returnButton = new JButton("Return");
                            buttonPanel.add(continueButton);
                            buttonPanel.add(returnButton);
                            Container content = mainFrame.getContentPane();
                            content.setLayout(new GridLayout(2, 1));

                            content.add(boxPanel);
                            content.add(buttonPanel);


                            if (customer.getAccounts().isEmpty()) {
                                JOptionPane.showMessageDialog(mainFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                mainFrame.dispose();
                                admin();
                            } else {

                                for (int i = 0; i < customer.getAccounts().size(); i++) {
                                    if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                        acc = customer.getAccounts().get(i);
                                    }
                                }

                                continueButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        String euro = "\u20ac";


                                        if (acc instanceof CustomerDepositAccount) {


                                            JOptionPane.showMessageDialog(mainFrame, "25" + euro + " deposit account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                            acc.setBalance(acc.getBalance() - 25);
                                            JOptionPane.showMessageDialog(mainFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                        }

                                        if (acc instanceof CustomerCurrentAccount) {


                                            JOptionPane.showMessageDialog(mainFrame, "15" + euro + " current account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                            acc.setBalance(acc.getBalance() - 25);
                                            JOptionPane.showMessageDialog(mainFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                        }


                                        mainFrame.dispose();
                                        admin();
                                    }
                                });

                                returnButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        mainFrame.dispose();
                                        menuStart();
                                    }
                                });

                            }
                        }
                    }
                }


            }
        });

        interestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                boolean loop = true;

                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.dispose();
                    admin();

                } else {
                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(mainFrame, "Customer ID of Customer You Wish to Apply Interest to:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                                loop = false;
                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                mainFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            mainFrame.dispose();
                            mainFrame = new JFrame("Administrator Menu");
                            mainFrame.setSize(400, 300);
                            mainFrame.setLocation(200, 200);
                            mainFrame.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent we) {
                                    System.exit(0);
                                }
                            });
                            mainFrame.setVisible(true);


                            JComboBox<String> box = new JComboBox<String>();
                            for (int i = 0; i < customer.getAccounts().size(); i++) {


                                box.addItem(customer.getAccounts().get(i).getNumber());
                            }


                            box.getSelectedItem();

                            JPanel boxPanel = new JPanel();

                            JLabel label = new JLabel("Select an account to apply interest to:");
                            boxPanel.add(label);
                            boxPanel.add(box);
                            JPanel buttonPanel = new JPanel();
                            JButton continueButton = new JButton("Apply Interest");
                            JButton returnButton = new JButton("Return");
                            buttonPanel.add(continueButton);
                            buttonPanel.add(returnButton);
                            Container content = mainFrame.getContentPane();
                            content.setLayout(new GridLayout(2, 1));

                            content.add(boxPanel);
                            content.add(buttonPanel);


                            if (customer.getAccounts().isEmpty()) {
                                JOptionPane.showMessageDialog(mainFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                mainFrame.dispose();
                                admin();
                            } else {

                                for (int i = 0; i < customer.getAccounts().size(); i++) {
                                    if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                        acc = customer.getAccounts().get(i);
                                    }
                                }

                                continueButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        String euro = "\u20ac";
                                        double interest = 0;
                                        boolean loop = true;

                                        while (loop) {
                                            String interestString = JOptionPane.showInputDialog(mainFrame, "Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");//the isNumeric method tests to see if the string entered was numeric.
                                            if (isNumeric(interestString)) {

                                                interest = Double.parseDouble(interestString);
                                                loop = false;

                                                acc.setBalance(acc.getBalance() + (acc.getBalance() * (interest / 100)));

                                                JOptionPane.showMessageDialog(mainFrame, interest + "% interest applied. \n new balance = " + acc.getBalance() + euro, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(mainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                            }


                                        }

                                        mainFrame.dispose();
                                        admin();
                                    }
                                });

                                returnButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        mainFrame.dispose();
                                        menuStart();
                                    }
                                });

                            }
                        }
                    }
                }

            }
        });

        editCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean loop = true;
                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.dispose();
                    admin();

                } else {

                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(mainFrame, "Enter Customer ID:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                mainFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            loop = false;
                        }
                    }

                    mainFrame.dispose();

                    mainFrame.dispose();
                    mainFrame = new JFrame("Administrator Menu");
                    mainFrame.setSize(400, 300);
                    mainFrame.setLocation(200, 200);
                    mainFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });

                    firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
                    surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
                    pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
                    dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
                    customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
                    passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
                    firstNameTextField = new JTextField(20);
                    surnameTextField = new JTextField(20);
                    pPSTextField = new JTextField(20);
                    dOBTextField = new JTextField(20);
                    customerIDTextField = new JTextField(20);
                    passwordTextField = new JTextField(20);

                    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                    JPanel cancelPanel = new JPanel();

                    textPanel.add(firstNameLabel);
                    textPanel.add(firstNameTextField);
                    textPanel.add(surnameLabel);
                    textPanel.add(surnameTextField);
                    textPanel.add(pPPSLabel);
                    textPanel.add(pPSTextField);
                    textPanel.add(dOBLabel);
                    textPanel.add(dOBTextField);
                    textPanel.add(customerIDLabel);
                    textPanel.add(customerIDTextField);
                    textPanel.add(passwordLabel);
                    textPanel.add(passwordTextField);

                    firstNameTextField.setText(customer.getFirstName());
                    surnameTextField.setText(customer.getSurname());
                    pPSTextField.setText(customer.getPPS());
                    dOBTextField.setText(customer.getDOB());
                    customerIDTextField.setText(customer.getCustomerID());
                    passwordTextField.setText(customer.getPassword());

                    //JLabel label1 = new JLabel("Edit customer details below. The save");


                    JButton saveButton = new JButton("Save");
                    JButton cancelButton = new JButton("Exit");

                    cancelPanel.add(cancelButton, BorderLayout.SOUTH);
                    cancelPanel.add(saveButton, BorderLayout.SOUTH);
                    //	content.removeAll();
                    Container content = mainFrame.getContentPane();
                    content.setLayout(new GridLayout(2, 1));
                    content.add(textPanel, BorderLayout.NORTH);
                    content.add(cancelPanel, BorderLayout.SOUTH);

                    mainFrame.setContentPane(content);
                    mainFrame.setSize(340, 350);
                    mainFrame.setLocation(200, 200);
                    mainFrame.setVisible(true);
                    mainFrame.setResizable(false);

                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            customer.setFirstName(firstNameTextField.getText());
                            customer.setSurname(surnameTextField.getText());
                            customer.setPPS(pPSTextField.getText());
                            customer.setDOB(dOBTextField.getText());
                            customer.setCustomerID(customerIDTextField.getText());
                            customer.setPassword(passwordTextField.getText());

                            JOptionPane.showMessageDialog(null, "Changes Saved.");
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            mainFrame.dispose();

                            admin();
                        }
                    });
                }
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();


                mainFrame = new JFrame("Summary of Transactions");
                mainFrame.setSize(400, 700);
                mainFrame.setLocation(200, 200);
                mainFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                mainFrame.setVisible(true);

                JLabel label1 = new JLabel("Summary of all transactions: ");

                JPanel returnPanel = new JPanel();
                JButton returnButton = new JButton("Return");
                returnPanel.add(returnButton);

                JPanel textPanel = new JPanel();

                textPanel.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea(40, 20);
                textArea.setEditable(false);
                textPanel.add(label1, BorderLayout.NORTH);
                textPanel.add(textArea, BorderLayout.CENTER);
                textPanel.add(returnButton, BorderLayout.SOUTH);

                JScrollPane scrollPane = new JScrollPane(textArea);
                textPanel.add(scrollPane);

                for (int a = 0; a < customerList.size(); a++)//For each customer, for each account, it displays each transaction.
                {
                    for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
                        acc = customerList.get(a).getAccounts().get(b);
                        for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

                            textArea.append(acc.getTransactionList().get(c).toString());
                            //Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use this to keep a running total but I couldnt get it  working.

                        }
                    }
                }


                textPanel.add(textArea);
                content.removeAll();


                Container content = mainFrame.getContentPane();
                content.setLayout(new GridLayout(1, 1));
                //	content.add(label1);
                content.add(textPanel);
                //content.add(returnPanel);

                returnButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        mainFrame.dispose();
                        admin();
                    }
                });
            }
        });

        navigateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                    admin();
                } else {
                    showAllUsers();
                }
            }
        });

        accountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.dispose();
                    admin();
                } else {
                    boolean loop = true;
                    boolean found = false;

                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(mainFrame, "Customer ID of Customer You Wish to Add an Account to:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                mainFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            loop = false;
                            //a combo box in an dialog box that asks the admin what type of account they wish to create (deposit/current)
                            String[] choices = {"Current Account", "Deposit Account"};
                            String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
                                    "Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

                            if (account.equals("Current Account")) {
                                //create current account
                                boolean valid = true;
                                double balance = 0;
                                String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1));//this simple algorithm generates the account number
                                ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
                                int randomPIN = (int) (Math.random() * 9000) + 1000;
                                String pin = String.valueOf(randomPIN);

                                ATMCard atm = new ATMCard(randomPIN, valid);

                                CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);

                                customer.getAccounts().add(current);
                                JOptionPane.showMessageDialog(mainFrame, "Account number = " + number + "\n PIN = " + pin, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                                mainFrame.dispose();
                                admin();
                            }

                            if (account.equals("Deposit Account")) {
                                //create deposit account

                                double balance = 0, interest = 0;
                                String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1));//this simple algorithm generates the account number
                                ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

                                CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);

                                customer.getAccounts().add(deposit);
                                JOptionPane.showMessageDialog(mainFrame, "Account number = " + number, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                                mainFrame.dispose();
                                admin();
                            }

                        }
                    }
                }
            }
        });

        deleteCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean found = true, loop = true;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                    dispose();
                    admin();
                } else {
                    {
                        Object customerID = JOptionPane.showInputDialog(mainFrame, "Customer ID of Customer You Wish to Delete:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                                loop = false;
                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                mainFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            if (customer.getAccounts().size() > 0) {
                                JOptionPane.showMessageDialog(mainFrame, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                customerList.remove(customer);
                                JOptionPane.showMessageDialog(mainFrame, "Customer Deleted ", "Success.", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }


                    }
                }
            }
        });

        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean found = true, loop = true;
                {
                    Object customerID = JOptionPane.showInputDialog(mainFrame, "Customer ID of Customer from which you wish to delete an account");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                            loop = false;
                        }
                    }

                    if (!found) {
                        int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            loop = true;
                        } else if (reply == JOptionPane.NO_OPTION) {
                            mainFrame.dispose();
                            loop = false;

                            admin();
                        }
                    } else {
                        //Here I would make the user select a an account to delete from a combo box. If the account had a balance of 0 then it would be deleted. (I do not have time to do this)
                    }


                }
            }

        });
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();
            }
        });
    }

    public void showAllUsers() {


        JButton first, previous, next, last, cancel;
        JPanel gridPanel, buttonPanel, cancelPanel;

        Container content = getContentPane();

        content.setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        gridPanel = new JPanel(new GridLayout(8, 2));
        cancelPanel = new JPanel();

        firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
        surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
        pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
        dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
        customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
        passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
        firstNameTextField = new JTextField(20);
        surnameTextField = new JTextField(20);
        pPSTextField = new JTextField(20);
        dOBTextField = new JTextField(20);
        customerIDTextField = new JTextField(20);
        passwordTextField = new JTextField(20);

        first = new JButton("First");
        previous = new JButton("Previous");
        next = new JButton("Next");
        last = new JButton("Last");
        cancel = new JButton("Cancel");

        firstNameTextField.setText(customerList.get(0).getFirstName());
        surnameTextField.setText(customerList.get(0).getSurname());
        pPSTextField.setText(customerList.get(0).getPPS());
        dOBTextField.setText(customerList.get(0).getDOB());
        customerIDTextField.setText(customerList.get(0).getCustomerID());
        passwordTextField.setText(customerList.get(0).getPassword());

        firstNameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        pPSTextField.setEditable(false);
        dOBTextField.setEditable(false);
        customerIDTextField.setEditable(false);
        passwordTextField.setEditable(false);

        gridPanel.add(firstNameLabel);
        gridPanel.add(firstNameTextField);
        gridPanel.add(surnameLabel);
        gridPanel.add(surnameTextField);
        gridPanel.add(pPPSLabel);
        gridPanel.add(pPSTextField);
        gridPanel.add(dOBLabel);
        gridPanel.add(dOBTextField);
        gridPanel.add(customerIDLabel);
        gridPanel.add(customerIDTextField);
        gridPanel.add(passwordLabel);
        gridPanel.add(passwordTextField);

        buttonPanel.add(first);
        buttonPanel.add(previous);
        buttonPanel.add(next);
        buttonPanel.add(last);

        cancelPanel.add(cancel);

        content.add(gridPanel, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);
        content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
        first.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                position = 0;
                firstNameTextField.setText(customerList.get(0).getFirstName());
                surnameTextField.setText(customerList.get(0).getSurname());
                pPSTextField.setText(customerList.get(0).getPPS());
                dOBTextField.setText(customerList.get(0).getDOB());
                customerIDTextField.setText(customerList.get(0).getCustomerID());
                passwordTextField.setText(customerList.get(0).getPassword());
            }
        });

        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (position < 1) {
                    //don't do anything
                } else {
                    position = position - 1;

                    firstNameTextField.setText(customerList.get(position).getFirstName());
                    surnameTextField.setText(customerList.get(position).getSurname());
                    pPSTextField.setText(customerList.get(position).getPPS());
                    dOBTextField.setText(customerList.get(position).getDOB());
                    customerIDTextField.setText(customerList.get(position).getCustomerID());
                    passwordTextField.setText(customerList.get(position).getPassword());
                }
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (position == customerList.size() - 1) {
                    //don't do anything
                } else {
                    position = position + 1;

                    firstNameTextField.setText(customerList.get(position).getFirstName());
                    surnameTextField.setText(customerList.get(position).getSurname());
                    pPSTextField.setText(customerList.get(position).getPPS());
                    dOBTextField.setText(customerList.get(position).getDOB());
                    customerIDTextField.setText(customerList.get(position).getCustomerID());
                    passwordTextField.setText(customerList.get(position).getPassword());
                }


            }
        });

        last.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                position = customerList.size() - 1;

                firstNameTextField.setText(customerList.get(position).getFirstName());
                surnameTextField.setText(customerList.get(position).getSurname());
                pPSTextField.setText(customerList.get(position).getPPS());
                dOBTextField.setText(customerList.get(position).getDOB());
                customerIDTextField.setText(customerList.get(position).getCustomerID());
                passwordTextField.setText(customerList.get(position).getPassword());
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
                admin();
            }
        });
        setContentPane(content);
        setSize(400, 300);
        setVisible(true);

    }

    public void customer(Customer e1) {
        mainFrame = new JFrame("Customer Menu");
        loggedInCustomer = e1;
        mainFrame.setSize(400, 300);
        mainFrame.setLocation(200, 200);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);

        if (loggedInCustomer.getAccounts().size() <= 0) {
            JOptionPane.showMessageDialog(mainFrame, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.dispose();
            menuStart();
        } else {
            customerMenu();
        }
    }

    public static boolean isNumeric(String str)
    {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public void customerMenu() {
        JPanel buttonPanel = new JPanel();
        JPanel boxPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel("Select Account:");
        labelPanel.add(label);
        JButton returnButton = new JButton("Return");
        buttonPanel.add(returnButton);
        JButton continueButton = new JButton("Continue");
        buttonPanel.add(continueButton);

        JComboBox<String> box = new JComboBox<String>();
        for (int i = 0; i < loggedInCustomer.getAccounts().size(); i++) {
            box.addItem(loggedInCustomer.getAccounts().get(i).getNumber());
        }

        for (int i = 0; i < loggedInCustomer.getAccounts().size(); i++) {
            if (loggedInCustomer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                acc = loggedInCustomer.getAccounts().get(i);
            }
        }

        boxPanel.add(box);
        content = mainFrame.getContentPane();
        content.setLayout(new GridLayout(3, 1));
        content.add(labelPanel);
        content.add(boxPanel);
        content.add(buttonPanel);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainFrame.dispose();
                mainFrame = new JFrame("Customer Menu");
                mainFrame.setSize(400, 300);
                mainFrame.setLocation(200, 200);
                mainFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
                mainFrame.setVisible(true);

                JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton statementButton = new JButton("Display Bank Statement");
                statementButton.setPreferredSize(new Dimension(250, 20));
                statementPanel.add(statementButton);
                JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton lodgementButton = new JButton("Lodge money into account");
                lodgementPanel.add(lodgementButton);
                lodgementButton.setPreferredSize(new Dimension(250, 20));
                JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton withdrawButton = new JButton("Withdraw money from account");
                withdrawalPanel.add(withdrawButton);
                withdrawButton.setPreferredSize(new Dimension(250, 20));
                JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton returnButton = new JButton("Exit Customer Menu");
                returnPanel.add(returnButton);
                JLabel label1 = new JLabel("Please select an option");

                content = mainFrame.getContentPane();
                content.setLayout(new GridLayout(5, 1));
                content.add(label1);
                content.add(statementPanel);
                content.add(lodgementPanel);
                content.add(withdrawalPanel);
                content.add(returnPanel);

                statementButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        mainFrame.dispose();
                        mainFrame = new JFrame("Customer Menu");
                        mainFrame.setSize(400, 600);
                        mainFrame.setLocation(200, 200);
                        mainFrame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent we) {
                                System.exit(0);
                            }
                        });
                        mainFrame.setVisible(true);
                        JLabel label1 = new JLabel("Summary of account transactions: ");
                        JPanel returnPanel = new JPanel();
                        JButton returnButton = new JButton("Return");
                        returnPanel.add(returnButton);
                        JPanel textPanel = new JPanel();
                        textPanel.setLayout(new BorderLayout());
                        JTextArea textArea = new JTextArea(40, 20);
                        textArea.setEditable(false);
                        textPanel.add(label1, BorderLayout.NORTH);
                        textPanel.add(textArea, BorderLayout.CENTER);
                        textPanel.add(returnButton, BorderLayout.SOUTH);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        textPanel.add(scrollPane);

                        for (int i = 0; i < acc.getTransactionList().size(); i++) {
                            textArea.append(acc.getTransactionList().get(i).toString());
                        }
                        textPanel.add(textArea);
                        content.removeAll();

                        Container content = mainFrame.getContentPane();
                        content.setLayout(new GridLayout(1, 1));
                        content.add(textPanel);

                        returnButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                mainFrame.dispose();
                                customer(loggedInCustomer);
                            }
                        });
                    }
                });

                lodgementButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        boolean on = false;
                        double balance = 0;

                        if (acc instanceof CustomerCurrentAccount) {
                            on = CustomerCurrentAccount.validateAccount(acc);
                        }

                        if (on) {
                            String balanceTest = JOptionPane.showInputDialog(mainFrame, "Enter amount you wish to lodge:");
                            if (isNumeric(balanceTest)) {
                                balance = Double.parseDouble(balanceTest);
                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            }

                            CustomerCurrentAccount.makeDeposit(acc, balance);
                        }
                    }
                });

                withdrawButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        boolean on = false;
                        double withdraw = 0;

                        if (acc instanceof CustomerCurrentAccount) {
                            on = CustomerCurrentAccount.validateAccount(acc);
                        }

                        if (on) {
                            String balanceTest = JOptionPane.showInputDialog(mainFrame, "Enter amount you wish to withdraw (max 500):");
                            if (isNumeric(balanceTest)) {
                                withdraw = Double.parseDouble(balanceTest);
                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if (withdraw > 500) {
                                JOptionPane.showMessageDialog(mainFrame, "500 is the maximum you can withdraw at a time.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                withdraw = 0;
                            }
                            if (withdraw > acc.getBalance()) {
                                JOptionPane.showMessageDialog(mainFrame, "Insufficient funds.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                withdraw = 0;
                            }

                            CustomerCurrentAccount.withdraw(acc, withdraw);
                        }
                    }
                });

                returnButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        mainFrame.dispose();
                        menuStart();
                    }
                });
            }
        });
    }
}