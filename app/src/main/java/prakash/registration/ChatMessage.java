package prakash.registration;

        import java.util.Date;

/**
 * Created by Navin on 29-03-2018.
 */

public class ChatMessage {
    private String messageText;
    private String messageuser;
    private long messagetime;

    public ChatMessage(String messageText,String messageuser)
    {
        this.messageText=messageText;
        this.messageuser=messageuser;

        messagetime= new Date().getTime();
    }
    public ChatMessage(){}
    public String getMessageText()
    {
        return messageText;
    }
    public void settMessageText(String messageText)
    {
        this.messageText=messageText;
    }
    public String getMessageuser()
    {
        return messageuser;
    }
    public void settMessageuser(String messageuser)
    {
        this.messageuser=messageuser;
    }
    public long getmessagetime()
    {
        return messagetime;
    }
    public void setMessagetime(long messagetime)
    {
        this.messagetime=messagetime;
    }
}
