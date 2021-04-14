package xyz.irohia;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public final class Main {
    public static void main(String[] args) {
        
        // init'ing the bot
        String token = args[0];
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        System.out.println("Bot has logged in!");

        // adding extensions
        api.addListener(new Ping());
        api.addListener(new NSFW());
        api.addListener(new Administration());
        api.addListener(new Music());
    }
}
