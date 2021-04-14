package xyz.irohia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Music implements MessageCreateListener {
    public Map<String,AudioConnection> mapAC = new HashMap<String,AudioConnection>();
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!join")) {
            for(ServerVoiceChannel vc:event.getServer().get().getVoiceChannels()){
                try{
                    if(vc.getConnectedUserIds().toString().matches("(.*)"+event.getMessageAuthor().getIdAsString()+"(.*)")){
                        mapAC.put(event.getServer().get().getIdAsString(),vc.connect().join());
                    }
                }catch(AssertionError e){
                    System.out.print(""); // do not throw this error to the console
                }
            }
        }
        if (event.getMessageContent().startsWith("!play")) {
            for(ServerVoiceChannel vc:event.getServer().get().getVoiceChannels()){
                try{
                    if(vc.getConnectedUserIds().toString().matches("(.*)"+event.getMessageAuthor().getIdAsString()+"(.*)")){
                        String[] slicedArgs = Arrays.copyOfRange(event.getMessageContent().split(" "),1,event.getMessageContent().split(" ").length);
                        AudioConnection ac = mapAC.get(event.getServer().get().getIdAsString());
                        DiscordApi api = event.getApi();
                        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                        if(slicedArgs[0].matches("(.*)bandcamp(.*)")){
                            playerManager.registerSourceManager(new BandcampAudioSourceManager());
                        }else if(slicedArgs[0].matches("(.*)youtube(.*)")){
                            playerManager.registerSourceManager(new YoutubeAudioSourceManager());
                        }else if(slicedArgs[0].matches("(.*)vimeo(.*)")){
                            playerManager.registerSourceManager(new VimeoAudioSourceManager());
                        }else{
                            playerManager.registerSourceManager(new HttpAudioSourceManager());
                        }
                        AudioPlayer player = playerManager.createPlayer();
                        AudioSource source = new LavaplayerAudioSource(api, player);
                        ac.setAudioSource(source);
                        playerManager.loadItem(slicedArgs[0], new AudioLoadResultHandler() {
                            @Override
                            public void trackLoaded(AudioTrack track) {
                                player.playTrack(track);
                            }
                        
                            @Override
                            public void playlistLoaded(AudioPlaylist playlist) {
                                for (AudioTrack track : playlist.getTracks()) {
                                    player.playTrack(track);
                                }
                            }
                        
                            @Override
                            public void noMatches() {
                                event.getChannel().sendMessage("No matches found!");
                            }
                        
                            @Override
                            public void loadFailed(FriendlyException throwable) {
                                System.out.print("");
                            }
                        });
                    }
                }catch(AssertionError e){
                    System.out.print(""); // do not throw this error to the console
                }
            }
        }
        if(event.getMessageContent().equals("!leave")){
            for(ServerVoiceChannel vc:event.getServer().get().getVoiceChannels()){
                try{
                    if(vc.getConnectedUserIds().toString().matches("(.*)"+event.getMessageAuthor().getIdAsString()+"(.*)")){
                       mapAC.get(event.getServer().get().getIdAsString()).close();
                    }
                }catch(AssertionError e){
                    System.out.print(""); // do not throw this error to the console
                }
            }
        }
        if(event.getMessageContent().equals("!stop")){
            for(ServerVoiceChannel vc:event.getServer().get().getVoiceChannels()){
                try{
                    if(vc.getConnectedUserIds().toString().matches("(.*)"+event.getMessageAuthor().getIdAsString()+"(.*)")){
                        AudioConnection ac = mapAC.get(event.getServer().get().getIdAsString());
                        DiscordApi api = event.getApi();
                        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                        playerManager.registerSourceManager(new HttpAudioSourceManager());
                        AudioPlayer player = playerManager.createPlayer();
                        AudioSource source = new LavaplayerAudioSource(api, player);
                        ac.setAudioSource(source);
                    }
                }catch(AssertionError e){
                    System.out.print(""); // do not throw this error to the console
                }
            }
        }
    }
}
