package Controller;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your  own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should 
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;
    /**
     * tasks:
     * register user on the endpoint POST localhost:8080/register
     * login user: verify my login on the endpoint POST localhost:8080/login
     * Create message: new post on the endpoint POST localhost:8080/messages
     * Get all messages: GET request on the endpoint GET localhost:8080/messages
     * get one message given message id GET localhost:8080/messages/{message_id}
     * delete a message given message id  endpoint DELETE localhost:8080/messages/{message_id}
     * update a message given message id endpoint PATCH localhost:8080/messages/{message_id}
     * get all messages from user given account id GET localhost:8080/accounts/{account_id}/messages
     * 
     */
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public void startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler); 
        app.post("/login", this::loginAccountHandler); 
        app.post("/messages", this::createMessageHandler); 
        app.get("/messages", this::getAllMessagesHandler); 
        app.get("/messages/{message_id}", this::getAMessageByMessageIDHandler); 
        app.delete("/messages/{message_id}", this::deleteAMessageByMessageIDHandler); 
        app.patch("/messages/{message_id}", this::updateMessageByMessageIDHandler); 
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler);  

        app.start(8080);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account userAccount = accountService.loginAccount(account);
        if(userAccount!=null){
            ctx.json(mapper.writeValueAsString(userAccount));
        }else{
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Account account = accountService.getAccountByID(message.getPosted_by());
        if(account!=null){
            Message newMessage = messageService.createMessage(message);
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    private void getAMessageByMessageIDHandler(Context context) {
        context.json(messageService.getMessageByID(Integer.parseInt(context.pathParam("message_id"))));
    }

    private void deleteAMessageByMessageIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(mapper.readValue(ctx.body(), String.class));
        Message deletedMessage = messageService.deleteMessage(messageID);
        if(deletedMessage!=null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }else{
            ctx.json(mapper.writeValueAsString(deletedMessage)); 
        }
    }

    private void updateMessageByMessageIDHandler(Context ctx) throws JsonProcessingException {
        
        int ID = Integer.parseInt(ctx.pathParam("message_ID"));
        String message = ctx.pathParam("message_text");
        Message updatedMessage = messageService.updateMessage(ID, message);
        if(updatedMessage!=null){
            ctx.json(updatedMessage);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesByAccountHandler(Context ctx) {
        ctx.json(messageService.getAllMessagesByAccount(Integer.parseInt(ctx.pathParam("account_ID"))));
    }

}