package xyz.irohia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class NSFW implements MessageCreateListener {
    final HttpClient http = HttpClient.newBuilder().build();
    Gson gson = new Gson();
    Random random = new Random();
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!e621")) {
            if(event.getServerTextChannel().get().isNsfw()){
                String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
                NSFW nsfw = new NSFW();
                String result = nsfw.search_e621(String.join("%20",slicedArgs));
                JsonArray root = JsonParser.parseString(result).getAsJsonObject().get("posts").getAsJsonArray();
                int numberToGet = random.nextInt(root.size()+1);
                event.getChannel().sendMessage(root.get(numberToGet).getAsJsonObject().get("sample").getAsJsonObject().get("url").getAsString());
            }
        }
    }
    public String search_e621(String arg){
        try{
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://e621.net/posts.json?tags=-scat%20"+arg)).setHeader("User-Agent", "KuroBot-Java / Contact cl6ver#1312 if misbehaving").build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            return(response.body());
        }catch(IOException|InterruptedException e){
            e.printStackTrace();
            return("null");
        }
    }
}
