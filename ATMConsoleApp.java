import java.util.Scanner;

// Strategy Pattern
interface TransactionStrategy {
    void execute(double amount);
}

class WithdrawalStrategy implements TransactionStrategy {
    @Override
    public void execute(double amount) {
        System.out.println("Withdrawing cash: " + amount);
    }
}

class DepositStrategy implements TransactionStrategy {
    @Override
    public void execute(double amount) {
        System.out.println("Depositing cash: " + amount);
    }
}

// Command Pattern for Transactions
interface ATMCommand {
    void execute();
}

abstract class TransactionCommand implements ATMCommand {
    private TransactionStrategy strategy;
    double amount;

    public TransactionCommand(TransactionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute() {
        strategy.execute(amount);
    }

    abstract double getAmount();
}

class WithdrawCommand extends TransactionCommand {
    public WithdrawCommand(double amount) {
        super(new WithdrawalStrategy());
        setAmount(amount);
    }

    private void setAmount(double amount) {
        if (amount > 0 && amount <= 1000) {
            this.amount = amount;
        } else {
            System.out.println("Invalid withdrawal amount. Please enter an amount between 0 and 1000.");
        }
    }

    @Override
    double getAmount() {
        return amount;
    }
}

class DepositCommand extends TransactionCommand {
    public DepositCommand(double amount) {
        super(new DepositStrategy());
        setAmount(amount);
    }

    private void setAmount(double amount) {
        if (amount > 0 && amount <= 1000) {
            this.amount = amount;
        } else {
            System.out.println("Invalid deposit amount. Please enter an amount between 0 and 1000.");
        }
    }

    @Override
    double getAmount() {
        return amount;
    }
}

// Command Pattern for ATM Operations
interface ATMOperationCommand {
    void execute(ATM atm);
}

class InsertCardCommand implements ATMOperationCommand {
    @Override
    public void execute(ATM atm) {
        atm.insertCard();
    }
}

class EjectCardCommand implements ATMOperationCommand {
    @Override
    public void execute(ATM atm) {
        atm.ejectCard();
    }
}

class EnterPINCommand implements ATMOperationCommand {
    @Override
    public void execute(ATM atm) {
        atm.enterPIN();
    }
}

// Observer Pattern
interface Observer {
    void update(String message);
}

class ATMUserInterface implements Observer {
    @Override
    public void update(String message) {
        System.out.println("User Interface: " + message);
    }
}

// Factory Method Pattern
abstract class TransactionFactory {
    abstract ATMCommand createTransaction(double amount);
}

class WithdrawFactory extends TransactionFactory {
    @Override
    ATMCommand createTransaction(double amount) {
        return new WithdrawCommand(amount);
    }
}

class DepositFactory extends TransactionFactory {
    @Override
    ATMCommand createTransaction(double amount) {
        return new DepositCommand(amount);
    }
}

// ATM Context
class ATM {
    private ATMState currentState;
    private Observer userInterface;

    public ATM() {
        this.currentState = new IdleState();
        this.userInterface = new ATMUserInterface();
    }

    public void setState(ATMState state) {
        this.currentState = state;
        notifyUserInterface("ATM State: " + state.getClass().getSimpleName());
    }

    public void insertCard() {
        System.out.println("Card inserted");
        // Prompt user for PIN (you can hardcode it or fetch it from a secure source)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter PIN: ");
        int enteredPIN = scanner.nextInt();

        // For simplicity, let's assume the hardcoded PIN is 1234
        if (enteredPIN == 1234) {
            setState(new PINEnteredState());
        } else {
            System.out.println("Incorrect PIN. Card ejected.");
            ejectCard();
        }
    }

    public void ejectCard() {
        currentState.ejectCard();
        setState(new IdleState());
    }

    public void enterPIN() {
        currentState.enterPIN();
        setState(new PINEnteredState());
    }

    public void withdrawCash(double amount) {
        currentState.withdrawCash(amount);
    }

    public void depositCash(double amount) {
        currentState.depositCash(amount);
    }

    public void checkBalance() {
        currentState.checkBalance();
    }

    public void addObserver(Observer observer) {
        this.userInterface = observer;
    }

    public void executeOperation(ATMOperationCommand operationCommand) {
        operationCommand.execute(this);
    }

    private void notifyUserInterface(String message) {
        userInterface.update(message);
    }
}

// State Pattern
interface ATMState {
    void insertCard();

    void ejectCard();

    void enterPIN();

    void withdrawCash(double amount);

    void depositCash(double amount);

    void checkBalance();
}

class PINEnteredState implements ATMState {

    private boolean cardEjected;
    private double accountBalance = 1000.0; // Initial account balance for demonstration purposes

    @Override
    public void insertCard() {
        System.out.println("Card is already inserted");
    }

    @Override
    public void ejectCard() {
        if (!cardEjected) {
            System.out.println("Card ejected");
            cardEjected = true;
            // Transition to the IdleState
        } else {
            System.out.println("Card is already ejected");
        }
    }

    @Override
    public void enterPIN() {
        System.out.println("PIN is already entered");
    }

    @Override
    public void withdrawCash(double amount) {
        if (!cardEjected) {
            if (amount > 0 && amount <= accountBalance) {
                System.out.println("Withdrawing cash: " + amount);
                accountBalance -= amount;
                System.out.println("Remaining balance: " + accountBalance);
            } else {
                System.out.println("Invalid withdrawal amount or insufficient funds");
            }
        } else {
            System.out.println("Card has been ejected. Please insert the card again.");
        }
    }

    @Override
    public void depositCash(double amount) {
        if (!cardEjected) {
            if (amount > 0) {
                System.out.println("Depositing cash: " + amount);
                accountBalance += amount;
                System.out.println("Updated balance: " + accountBalance);
            } else {
                System.out.println("Invalid deposit amount");
            }
        } else {
            System.out.println("Card has been ejected. Please insert the card again.");
        }
    }

    @Override
    public void checkBalance() {
        if (!cardEjected) {
            System.out.println("Checking balance...");
            System.out.println("Current balance: " + accountBalance);
        } else {
            System.out.println("Card has been ejected. Please insert the card again.");
        }
    }

}

class IdleState implements ATMState {
    @Override
    public void insertCard() {
        System.out.println("Card inserted");
    }

    public void ejectCard() {
        System.out.println("No card to eject");
    }

    @Override
    public void enterPIN() {
        System.out.println("Please insert a card before entering PIN");
    }

    @Override
    public void withdrawCash(double amount) {
        System.out.println("Please insert a card and enter PIN");
    }

    @Override
    public void depositCash(double amount) {
        System.out.println("Please insert a card and enter PIN");
    }

    @Override
    public void checkBalance() {
        System.out.println("Please insert a card and enter PIN");
    }

}


class CardInsertedState implements ATMState {

    private boolean pinEntered;
    private double accountBalance = 1000.0; // Initial account balance for demonstration purposes

    @Override
    public void insertCard() {
        System.out.println("Card is already inserted");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card ejected");
        pinEntered = false;
    }

    @Override
    public void enterPIN() {
        if (!pinEntered) {
            System.out.println("PIN entered");
            pinEntered = true;
        } else {
            System.out.println("PIN is already entered");
        }
    }

    @Override
    public void withdrawCash(double amount) {
        if (pinEntered) {
            if (amount > 0 && amount <= accountBalance) {
                System.out.println("Withdrawing cash: " + amount);
                accountBalance -= amount;
                System.out.println("Remaining balance: " + accountBalance);
            } else {
                System.out.println("Invalid withdrawal amount or insufficient funds");
            }
        } else {
            System.out.println("Please enter PIN before withdrawing cash");
        }
    }

    @Override
    public void depositCash(double amount) {
        if (pinEntered) {
            if (amount > 0) {
                System.out.println("Depositing cash: " + amount);
                accountBalance += amount;
                System.out.println("Updated balance: " + accountBalance);
            } else {
                System.out.println("Invalid deposit amount");
            }
        } else {
            System.out.println("Please enter PIN before depositing cash");
        }
    }

    @Override
    public void checkBalance() {
        if (pinEntered) {
            System.out.println("Checking balance...");
            System.out.println("Current balance: " + accountBalance);
        } else {
            System.out.println("Please enter PIN before checking balance");
        }
    }
}

public class ATMConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();
        atm.addObserver(new ATMUserInterface());

        System.out.println("ATM Simulator. Assalamualaikum");
        System.out.println("1. Insert Card");
        System.out.println("2. Eject Card");
        System.out.println("3. Withdraw Cash");
        System.out.println("4. Deposit Cash");
        System.out.println("5. Check Balance");
        System.out.println("0. Exit");

        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.executeOperation(new InsertCardCommand());
                    break;
                case 2:
                    atm.executeOperation(new EjectCardCommand());
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdrawCash(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.depositCash(depositAmount);
                    break;
                case 5:
                    atm.checkBalance();
                    break;
                case 0:
                    System.out.println("Exiting ATM. JajakAllah Khairun");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
