/**
 * David BM 302518097
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private String   id;
    private double balance;
    private Lock balanceChangeLock;
    private Condition sufficientFundsCondition;

    /**
     *
     * @param id : the id of the account
     */
    public BankAccount (String id){
        this.id=id;
        balance=0;
        balanceChangeLock=new ReentrantLock();
        sufficientFundsCondition=balanceChangeLock.newCondition();
    }

    //getter
    public double getBalance(){
        return balance;
    }

    /**
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount){
        balanceChangeLock.lock();
        try{
            System.out.println(this.toString());
            double newBalance=balance +amount;
            balance=newBalance;
            System.out.println(this.toString()+ ", Depositing "+amount+ ", new balance is "+newBalance);
            sufficientFundsCondition.signalAll();
             }
        catch (Exception e){e.printStackTrace();}
        finally {
            balanceChangeLock.unlock();
        }
    }

    /**
     *
     * @param amount the amount to withdrew
     * @throws InterruptedException
     */

    public void withdraw(double amount) throws InterruptedException{
        balanceChangeLock.lock();
        try{
            while (balance<amount){
                sufficientFundsCondition.await();
            }
            double newBalance=balance-amount;
            balance=newBalance;
            System.out.println(this.toString()+ ", withdrawing "+amount+ ", new balance is "+newBalance);
            }
        catch (Exception e){e.printStackTrace();}
        finally {
            balanceChangeLock.unlock();
        }
    }
    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", balanceChangeLock=" + balanceChangeLock +
                ", sufficientFundsCondition=" + sufficientFundsCondition +
                '}';
    }
}

