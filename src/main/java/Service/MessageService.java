package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.*;
/*
 * TODO:
 * get all messages from a given user given account id
 * update a message given message id
 * delete a message given message id
 * get one message given message id
 * get all messages
 * create a new message
 */

public class MessageService {
    private MessageDAO messageDAO;
    /**
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }
   
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
   
    public Message getMessageByID(int ID){
        return messageDAO.getMessageByID(ID);
    }

    public Message deleteMessage(int ID){
        return messageDAO.deleteMessage(ID);
    }

    public Message updateMessage(int ID, String message){
        if(message.length() >= 255 && message != ""){
            return messageDAO.updateMessage(ID, message);
        }
        else{
            return null;
        }
    }
    public List<Message> getAllMessagesByAccount(int ID){
        return messageDAO.getAllMessagesByAccount(ID);
    }
}
