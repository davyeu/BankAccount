/**
 * David BM 302518097
 */

public  class  Transaction   {

    //fields
    double amount;
    BankAccount bankAccount;
    boolean isDone=false;


    //ctor

    /**
     *
     * @param anAccount bank account object
     * @param anAmount the amount to deposit or withdraw
     */
    public Transaction(BankAccount anAccount,double anAmount) {
        bankAccount=anAccount;
        amount=anAmount;
    }

    //getter and setter


    public double getAmount() {
        return amount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
