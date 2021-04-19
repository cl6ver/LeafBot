package xyz.irohia;

import java.util.Arrays;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Administration implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!ban")) {
            String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
            if(slicedArgs instanceof String[]){
                event.getServer().get().banUser(Long.parseLong(slicedArgs[0])).join();
                event.getApi().getUserById(slicedArgs[0]).join().openPrivateChannel().join().sendMessage("You have been banned from "+event.getServer().get().getName()+".");
            }
        }
        if (event.getMessageContent().startsWith("!kick")) {
            String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
            if(slicedArgs instanceof String[]){
                User userToKick = event.getServer().get().getMemberById(Long.parseLong(slicedArgs[0])).get();
                event.getServer().get().kickUser(userToKick);
                event.getApi().getUserById(slicedArgs[0]).join().openPrivateChannel().join().sendMessage("You have been kicked from "+event.getServer().get().getName()+".");
            }
        }
    }
}
