package xyz.irohia;

import org.javacord.api.entity.message.*;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Ping implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!ping")) {
            double result_ms=0.0;
            long millisBefore = System.currentTimeMillis();
            Message message = event.getChannel().sendMessage("Pong!").join();
            long millisAfter = System.currentTimeMillis();
            result_ms=millisAfter-millisBefore;
            message.edit("Pong! (in "+result_ms+"ms)").join();
        }
    }
}
