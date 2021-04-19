package xyz.irohia;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public final class Main {
    public static void main(String[] args) {
        
        // init'ing the bot
        DiscordApi api = new DiscordApiBuilder().setToken(args[0]).login().join();
        api.setMessageCacheSize(1, 60*60); // 1 message per channel, deleted every hour
        System.out.println("Bot has logged in!");

        // adding extensions
        api.addListener(new Ping());
        api.addListener(new NSFW());
        api.addListener(new Administration());
        api.addListener(new Music());
        api.addListener(new Fun());
    }
}
