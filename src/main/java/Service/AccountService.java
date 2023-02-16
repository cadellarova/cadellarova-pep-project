package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.*;
/*
 * TODO: register a new account and login a user
 */

public class AccountService{
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
   
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    /**
     * TODO: Use the AccountDAO to add an account.
     *
     * @return true if successful
     */

    public Account registerAccount(Account account) {
        return accountDAO.registerAccount(account);
    }
    /**
     * TODO: check if the user exists to log them in
     *
     * 
     * @return account if account exists
     */
    public Account loginAccount(Account account) {
        return accountDAO.loginUserAccount(account);
    }

    public Account getAccountByID(int accountID) {
        return accountDAO.getAccountByID(accountID);
    }
}