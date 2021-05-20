# BankAccount

this is multi-threading  bank account app.
 * there is fixed number of threads names "bank officer" (by default is 10)
 * that executing the transactions in the transaction array. (by default there are 20 transaction),
 * on fixed number of accounts(by default is 5).
 * the bank officer cannot withdrew amount if the account balance is decreasing from 0.
 * So they wait until the another thread deposit money to the account and release them.
 *
 * In this program is been use by locks and conditions wait function.
