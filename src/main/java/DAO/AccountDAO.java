/*You will need to design and create your own DAO classes from scratch. 
You should refer to prior mini-project lab examples and course material for guidance.  
*/ 
package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
 * TODO: register a new account and login a user
 */

public class AccountDAO {

    /*
     * TODO: check if an account is in database
     *     /**
     * TODO: register a new account
     * @return a message for success or failure of adding account
     *
     */
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if(account.getUsername() == ""){
            return null;
        }
        else if(account.getPassword().length() < 4){
            return null;
        }
        else{
            try {
                //Write SQL logic here
                String sql = "SELECT COUNT (*) FROM Account WHERE username = ?; ";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,account.getUsername());
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    return null;
                }

                //Write SQL logic here
                String sql1 = "INSERT INTO  Account VALUES (?, ?);";
                
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                preparedStatement1.setString(1,account.getUsername());
                preparedStatement1.setString(2,account.getPassword());
                ResultSet pkeyResultSet = preparedStatement1.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * TODO: check if the user is in database
     */
    public Account loginUserAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?; " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();;
            if(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

        /**
     * TODO: check if the user is in database by account ID
     */
    public Account getAccountByID(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "SELECT * FROM Account WHERE account_id = ?; " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,accountID);
            
            ResultSet rs = preparedStatement.executeQuery();;
            if(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}