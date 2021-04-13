package xyz.irohia;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public final class Main {
    public static void main(String[] args) {
        String token = "";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        System.out.println("Bot has logged in!");
        api.addListener(new Ping());
        api.addListener(new NSFW());
        api.addListener(new Ban());
    }
}
