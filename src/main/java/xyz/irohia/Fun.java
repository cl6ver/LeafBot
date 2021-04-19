package xyz.irohia;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Fun implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Random random = new Random();
        if(event.getMessageContent().startsWith("!roll")){
            String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
            if(!(slicedArgs[0].equals(null))){
                try {
                    int rollInteger = random.nextInt(Integer.parseInt(slicedArgs[0]));
                    while(rollInteger==0) rollInteger = random.nextInt(Integer.parseInt(slicedArgs[0]));
                    event.getChannel().sendMessage(""+rollInteger);
                }catch(NumberFormatException e){
                    event.getChannel().sendMessage("Dice number too large!");
                    event.getChannel().sendMessage("`NumberFormatException: "+e.getMessage()+"`");
                }
            }
        }
    }
}
