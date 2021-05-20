/**
 * David BM 302518097
 */

import java.util.Iterator;
import java.util.Map;


public class BankOfficer extends Thread {
    //fields
    private BankAccount[] accounts;
    private Iterator<Map.Entry<Integer, Transaction>> itr;



    //ctor

    /**
     *
     * @param accounts the accounts in the banks
     */
    public BankOfficer(BankAccount[] accounts) {
        this.accounts = accounts;
    }

    /**
     *
     * @param key the key of the current transaction
     */
    public void transaction(int key) {
        BankAccountThreadRunner.transactionLock.lock();
        try {
                BankAccount account = BankAccountThreadRunner.transactionMap.get(key).bankAccount;
                double amount = BankAccountThreadRunner.transactionMap.get(key).getAmount();
                if (amount >= 0)
                    account.deposit(amount);
                else
                    account.withdraw(amount);
            } catch (Exception e) {
            //interruptedException.printStackTrace();
        }
        finally {
            BankAccountThreadRunner.transactionLock.unlock();
        }
    }


    public void run() {
        while (!BankAccountThreadRunner.transactionMap.isEmpty()) {
                transaction(BankAccountThreadRunner.transactionMap.size()-1);
                BankAccountThreadRunner.transactionMap.remove(BankAccountThreadRunner.transactionMap.size()-1);
        }
    }
}
