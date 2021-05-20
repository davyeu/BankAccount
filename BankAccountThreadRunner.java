/**
 * David BM 302518097
 *
 * this is multi-threading  bank account app.
 * there is fixed number of threads names "bank officer" (by default is 10)
 * that executing the transactions in the transaction array. (by default there are 20 transaction),
 * on fixed number of accounts(by default is 5).
 * the bank officer cannot withdrew amount if the account balance is decreasing from 0.
 * So they wait until the another thread deposit money to the account and release them.
 *
 * In this program is been use by locks and conditions wait function.
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountThreadRunner {

    static Map<Integer,Transaction> transactionMap; // design in order to keep the transactions
    static Lock transactionLock=new ReentrantLock(); //will be in use at BankOfficer class

    public static void  main (String []args){
        final int numOfTransactions=20;
        final int numOfAccounts=5;
        final int numOfBankOfficers=10;
        BankAccount[] accounts=new BankAccount[numOfAccounts];
        Transaction[] transactions= new Transaction[numOfTransactions];
        Thread[] bankOfficersThreads=new Thread[numOfBankOfficers];

        int max=1000; //max amount to deposit or withdraw
        int min=-1000; //max amount to deposit or withdraw
        Random rand = new Random();
        int amount,accountNumber;
        for(int i=0;i<5;i++)
            accounts[i]=new BankAccount(String.valueOf(i));

        //set random transactions
        for (int i=0; i<numOfTransactions;i++){
            accountNumber=rand.nextInt(5);
            amount= rand.nextInt(max - min) + min;
            transactions[i]= new Transaction(accounts[accountNumber],amount);
        }

        //copy the transactions to the map
       transactionMap=new HashMap<>();
        for(int i=0;i<transactions.length;i++)
            transactionMap.put(i,transactions[i]);

        //create 10 bankOfficer
        for(int i=0;i<numOfBankOfficers;i++){
            bankOfficersThreads[i]=new BankOfficer(accounts);
            bankOfficersThreads[i].start();
        }


    }
}

/**
 * example of output:
 * C:\Users\User\.jdks\openjdk-14.0.1\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.2\lib\idea_rt.jar=59821:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.2\bin" -Dfile.encoding=UTF-8 -classpath C:\myFiles\java\MMANIM\MAMAN15\Q1.1\out\production\Q1.1 BankAccountThreadRunner
 * BankAccount{id='1', balance=673.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}, withdrawing -673.0, new balance is 673.0
 * BankAccount{id='0', balance=0.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@438777a8[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@7c9efd01}
 * BankAccount{id='0', balance=426.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@438777a8[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@7c9efd01}, Depositing 426.0, new balance is 426.0
 * BankAccount{id='2', balance=0.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}
 * BankAccount{id='2', balance=444.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}, Depositing 444.0, new balance is 444.0
 * BankAccount{id='1', balance=1109.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}, withdrawing -436.0, new balance is 1109.0
 * BankAccount{id='2', balance=444.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}
 * BankAccount{id='2', balance=1359.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}, Depositing 915.0, new balance is 1359.0
 * BankAccount{id='1', balance=1440.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}, withdrawing -331.0, new balance is 1440.0
 * BankAccount{id='0', balance=426.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@438777a8[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@7c9efd01}
 * BankAccount{id='0', balance=498.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@438777a8[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@7c9efd01}, Depositing 72.0, new balance is 498.0
 * BankAccount{id='2', balance=1359.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}
 * BankAccount{id='2', balance=1566.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@34719659[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@6e30c9a3}, Depositing 207.0, new balance is 1566.0
 * BankAccount{id='4', balance=0.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}
 * BankAccount{id='4', balance=744.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}, Depositing 744.0, new balance is 744.0
 * BankAccount{id='1', balance=1440.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}
 * BankAccount{id='1', balance=2056.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}, Depositing 616.0, new balance is 2056.0
 * BankAccount{id='4', balance=1703.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}, withdrawing -959.0, new balance is 1703.0
 * BankAccount{id='4', balance=2496.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}, withdrawing -793.0, new balance is 2496.0
 * BankAccount{id='3', balance=0.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}
 * BankAccount{id='3', balance=177.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}, Depositing 177.0, new balance is 177.0
 * BankAccount{id='0', balance=581.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@438777a8[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@7c9efd01}, withdrawing -83.0, new balance is 581.0
 * BankAccount{id='1', balance=2056.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}
 * BankAccount{id='1', balance=2654.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@30433920[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@63ebf5af}, Depositing 598.0, new balance is 2654.0
 * BankAccount{id='3', balance=177.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}
 * BankAccount{id='3', balance=725.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}, Depositing 548.0, new balance is 725.0
 * BankAccount{id='3', balance=1262.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}, withdrawing -537.0, new balance is 1262.0
 * BankAccount{id='4', balance=2936.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}, withdrawing -440.0, new balance is 2936.0
 * BankAccount{id='3', balance=1262.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}
 * BankAccount{id='3', balance=1667.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@25f4c666[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@4de17eaa}, Depositing 405.0, new balance is 1667.0
 * BankAccount{id='4', balance=3884.0, balanceChangeLock=java.util.concurrent.locks.ReentrantLock@1672d9fa[Locked by thread Thread-0], sufficientFundsCondition=java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject@1d0f48c0}, withdrawing -948.0, new balance is 3884.0
 *
 * Process finished with exit code 0
 *
 */