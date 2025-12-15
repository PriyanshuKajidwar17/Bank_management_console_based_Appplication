
// import java.util.ArrayList;
// import java.util.Iterator;
import java.util.*;

class Manager {
    String name;
    String password;
    int acc_number;
    String IFSC_code;
    String Branch;

    public Manager(String name, String password, int acc_number, String IFSC_code, String Branch) {
        this.name = name;
        this.password = password;
        this.acc_number = acc_number;
        this.IFSC_code = IFSC_code;
        this.Branch = Branch;
    }

}

abstract class Account {
    double balance;
    String name;

    Account(String name) {
        this.name = name;
        this.balance = 0;
    }

    public void deposit(double amt) {
        if (amt > 0) {
            balance += amt;
        } else {
            System.out.println("Invalid Amount");
        }
    }

    public void withdraw(Double amt) {
        if (amt > balance) {
            System.out.println("Sufficient amount is not present in this account ...");
        } else {
            balance -= amt;
            System.out.println(amt + " rupees withdraw successfully...");
        }
    }

    public double getbalance() {
        //System.out.println("Current Balance is : "+balance);
        return balance;
    }

    public abstract void ShowAccountType();
}

class SavingAccount extends Account {
    public SavingAccount(String name) {
        super(name);
    }

    // method overriding
    public void ShowAccountType() {

    }

}

public class BankingSystem {

    // Super Admin Credential
    static final String Admin_username = "admin";
    static final String passwd = "1234";

    // for taking input
    static final Scanner sc = new Scanner(System.in);

    // hashmap for Bank Manager
    static HashMap<String, String> manager_data = new HashMap<>();
    static HashMap<String, Manager> customer_data = new HashMap<>();
    static HashMap<String, String> customer_data_cust = new HashMap<>();

    public static void main(String[] args) {

        String str;

        while (true) {
            System.out.println();
            System.out.println("==============================================================");
            System.out.println("*************** Welcome to Bank Management System *********** ");
            System.out.println("==============================================================");
            System.out.println();
            System.out.println("Choices.....");
            System.out.println("1. Super user(admin) Login ");
            System.out.println("2. Bank Manager Login");
            System.out.println("3. Create Account for Customer");
            System.out.println("4. Exit");
            System.out.println("5. Clear Screen...");
            System.out.println();
            System.out.print("Enter Your Choice: \t");
            str = sc.nextLine();

            // switch case

            switch (str) {
                case "1" -> {
                    // admin super user login
                    String aname;
                    String apasswd;
                    System.out.print("Enter Admin username: ");
                    aname = sc.nextLine();

                    System.out.print("Enter Admin Password : ");
                    apasswd = sc.nextLine();

                    if (aname.equals(Admin_username) && apasswd.equals(passwd)) {
                        System.out.println("Admin Login Successfully ....");
                        adminpanel();
                    } else {
                        System.out.println("Invalid username and Password for admin ...");
                    }

                }
                case "2" -> bank_manager();
                case "3" -> {
                    String name;
                    System.out.println(" ============= Create Account for Customer =============");
                    System.out.println("Enter Customer name: ");
                    name = sc.nextLine();

                    if (name.isEmpty()) {
                        System.out.println("Customer name should not be empty...");
                        return;
                    }
                    System.out.println("Enter Password...");
                    String password = sc.nextLine();
                    if (!password.matches("\\d{4}")) {
                        System.out.println("Password Must be Exactly 4 letters...");
                        return;
                    }

                    customer_data_cust.put(name, password);
                    System.out.println("Customer created account Successfully....");
                    customer_menu();
                    // customer login
                }
                case "4" -> {
                    System.out.println("Thank you for using Bank Management System ");
                    return;
                }
                case "5" -> {
                    try {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
                default -> {
                    System.out.println("Invalid entry / choice");
                }
            }
        }
    }

    static void customer_menu() {
        System.out.println("Implementing Customer menu...");
        Account acc = null;
        String ch;
        while (true) {
            System.out.println("\n--- Customer Bank Menu ---");
            System.out.println("#. Re-Customer Login...");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("0. Exit");
            System.out.print("Enter your Choose: ");
            ch = sc.nextLine();

            switch (ch) {
                case "#" -> {
                    String name;
                    String password;

                    System.out.println("Welcome to customer login section : ");
                    System.out.println("Enter Username: ");
                    name = sc.nextLine();

                    System.out.print("Enter Password : ");
                    password = sc.nextLine();

                    if (customer_data_cust.containsKey(name) &&
                            customer_data_cust.get(name).equals(password)) {

                        System.out.println("Customer login successfully....");
                        acc = new SavingAccount(name);
                        System.out.println("Account successfully created ....");

                    } else {
                        System.out.println("Username or password does not match....");
                    }
                }

                case "1" -> {
                    // deposit...

                    if (acc == null) {
                        System.out.println("Create first account...");
                        return;
                    }
                    System.out.println("Enter Amount to be Deposit : ");
                    acc.deposit(sc.nextDouble());
                    sc.nextLine();

                }
                case "2" -> {
                    // withdraw
                    if (acc == null) {
                        System.out.println("Create first account...");
                        break;
                    }
                    System.out.println("Enter amount to Withdraw.. ");
                    acc.withdraw(sc.nextDouble());
                    sc.nextLine();
                }
                case "3" -> {
                    // check balance
                    if (acc != null) {
                        System.out.println("Current Account Balance :  "+acc.getbalance());
                        //acc.ShowAccountType();
                    } else {
                        System.out.println("create account first");
                    }

                }
                case "0" -> {
                    System.out.println("Thanks for using Bank Management Application.......");
                    return;
                }
                default -> {
                    System.out.println("Invalid Choices....");
                }
            }
        }
    }

    static void bank_manager() {
        // Bank Manager login
        String uname;
        String upassed;
        System.out.println("########## Welcome to Bank Manager Login Page ###########");
        System.out.print("Enter Username : ");
        uname = sc.nextLine();

        System.out.print("Enter Password: ");
        upassed = sc.nextLine();

        // authentication
        if (manager_data.containsKey(uname) && manager_data.containsValue(upassed)) {
            System.out.println("Bank Manager Login Successfully ....");
            manager_menu();
        } else {
            System.out.println("Invalid username or Password...");
        }

    }

    static void manager_menu() {
        String ch;
        System.out.println("=========== Welcome to Bank Manager pAnel Control System =============");
        System.out.println("1. Add Customer.");
        System.out.println("2. Remove Customer.");
        System.out.println("3. View All Customer.");
        System.out.println("4. LogOut.");
        System.out.println("5. Clear Screen..");

        System.out.println();
        System.out.println("Enter Your choice....");
        ch = sc.nextLine();
        switch (ch) {
            case "1" -> add_customer();
            case "2" -> remove_customer();
            case "3" -> view_customer();
            case "4" -> {
                System.out.println("You are successfully log-out...");
                return;
            }
            case "5" -> {
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    static void add_customer() {
        String name;
        String pin;
        int acc_number;
        String IFSC_code;
        String Branch;
        System.out.println("========== Add Customer data ======= ");

        System.out.print("Enter Customer Username: ");
        name = sc.nextLine();

        System.out.print("Enter PIN (4 digits): ");
        pin = sc.next();

        System.out.print("Enter Account number: ");
        acc_number = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter IFSC Code: ");
        IFSC_code = sc.nextLine();
        sc.nextLine();

        System.out.print("Enter Branch of Bank : ");
        Branch = sc.nextLine();

        // object bana

        Manager mn = new Manager(name, pin, acc_number, IFSC_code, Branch);

        customer_data.put(name, mn);
        System.out.println("Customer Detail Added Successfully!");
    }

    static void remove_customer() {
        System.out.println("Implementation of remove_customer is in undergoes....");

    }

    static void view_customer() {

        if (customer_data.isEmpty()) {
            System.out.println("No customer records found.");
            return;
        }

        int ct = 0;
        System.out.println("=========== Customer Details ===========");

        for (Map.Entry<String, Manager> e : customer_data.entrySet()) {
            ct++;
            Manager c = e.getValue();

            System.out.println("\nCustomer " + ct);
            System.out.println("Username       : " + c.name);
            System.out.println("Account Number : " + c.acc_number);
            System.out.println("IFSC Code      : " + c.IFSC_code);
            System.out.println("Branch         : " + c.Branch);
        }
    }

    static void adminpanel() {
        String str;
        while (true) {
            System.out.println();
            System.out.println("############### Welcome to Admin Panel ################");
            System.out.println();
            System.out.println("1. Add Bank Manager");
            System.out.println("2. Remove Bank Manager");
            System.out.println("3. View all Bank Manager");
            System.out.println("4. Add clerk");
            System.out.println("5. Remove clerk");
            System.out.println("6. View All clerk");
            System.out.println("61. Add Customer");
            System.out.println("7. Remove Customer");
            System.out.println("8. View All Customer");
            System.out.println("9. logOut");
            System.out.println("10. Clear the screen..");
            System.out.println();
            System.out.println("Enter your choice : ");
            str = sc.nextLine();

            switch (str) {
                case "4" -> {
                    System.out.println("Implementing Add Clerk...");
                }
                case "5" -> {
                    System.out.println("implementing Remove clerk...");
                }
                case "6" -> {
                    System.out.println("Implementing View all clerk...");
                }
                case "60" -> {
                    System.out.println("Implementing Add customer...");
                }
                case "7" -> {
                    System.out.println("Implementing Remove Customer...");
                }
                case "8" -> {
                    System.out.println("Implementin view all customer..");
                }

                case "1" -> {
                    // Add Bank Manager

                    System.out.println("Add Customer Details.");
                    System.out.print("Enter Bank Manager Username : ");
                    String mname = sc.nextLine();
                    if (mname.isEmpty()) {
                        System.out.println("Username should not be Empty");
                        return;
                    }
                    System.out.print("Enter Bank Manager Password : ");
                    String bpasswd = sc.nextLine();
                    if (!bpasswd.matches("\\d{4}")) {
                        System.out.println("Pin Exactly of 4 letters");
                        return;
                    }
                    manager_data.put(mname, bpasswd);
                    System.out.println("Bank Manager Added Successfully.......");

                }
                case "2" -> {
                    // Remove Bank Manager
                    System.out.println("Enter Name of Bank Managaer : ");
                    String bname = sc.nextLine();

                    if (manager_data.remove(bname) != null) {
                        System.out.println("Record remove successfully....");

                    } else {
                        System.out.println("Record not found ....");
                    }
                }
                case "3" -> {
                    // view all bank manager
                    for (Map.Entry<String, String> dt : manager_data.entrySet()) {
                        System.out.println(dt + " - ");
                    }

                }
                case "9" -> {
                    System.out.println("you logout from admin panel successfully.....");
                    return;
                }
                case "10" -> {
                    try {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }
}
