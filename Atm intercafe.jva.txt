public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    private HashMap<String, User> users;
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        users = new HashMap<>();
        scanner = new Scanner(System.in);
        // Add some sample users
        users.put("user1", new User("user1", "1234", 5000));
        users.put("user2", new User("user2", "5678", 3000));
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            if (authenticate(userId, pin)) {
                showMenu();
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }
    }

    private boolean authenticate(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.validatePin(pin)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    private void showMenu() {
        while (true) {
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    handleWithdraw();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleWithdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        if (currentUser.withdraw(amount)) {
            System.out.println("Withdraw successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void handleDeposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        currentUser.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private void handleTransfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        User recipient = users.get(recipientId);
        if (recipient != null && currentUser.transfer(amount, recipient)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check recipient ID or balance.");
        }
    }
}
import java.util.ArrayList;

public class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public boolean validatePin(String pin) {
        return this.pin.equals(pin);
    }

    public void showTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public boolean transfer(double amount, User recipient) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            recipient.addTransaction(new Transaction("Transfer from " + this.userId, amount));
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getUserId() {
        return userId;
    }
}
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String type;
    private double amount;
    private String date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }
}
// This class can be extended for more advanced account management functionalities
public class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
