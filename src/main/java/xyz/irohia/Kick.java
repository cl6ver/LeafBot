package xyz.irohia;

import java.util.Arrays;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Kick implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!ban")) {
            String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
            if(slicedArgs instanceof String[]){
                User userToKick = event.getServer().get().getMemberById(Long.parseLong(slicedArgs[0])).get();
                event.getServer().get().kickUser(userToKick);
            }
        }
    }
}
