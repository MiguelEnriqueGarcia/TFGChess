/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.server;

import com.chess.general.ChessManager;
import com.connutils.chatlog.ChatLog;
import com.db.DatabaseManager;
import com.db.pojo.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.utils.ServerCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.connutils.Request;
import com.connutils.RequestBuilder;
import com.utils.ConfigReader;

/**
 *
 * @author migue
 */
public class Server {
    
    
    private static final int PORT = ConfigReader.getPuerto();
    
    public static Map<String, ClientConn> players = new HashMap<>();
    private static Map<String, Game> games = new HashMap<>();
    
    public static Map<String, ChatLog> logs = new HashMap<String, ChatLog>();
    
    public static void main(String[] args) {
         try {
             ServerSocket serverSocket = new ServerSocket(PORT);
             Socket actualSocket;
             
             for (;;) {
                actualSocket = serverSocket.accept();
                
                AccessController accessController = new AccessController(actualSocket);
                accessController.start();
                 
             }
         } catch (IOException ex) {
             Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public static Optional<ClientConn> getPlayer(String username){
        return Optional.ofNullable(players.getOrDefault(username, null));
    }
    
    public static int addPlayer(String username, Socket socket) {
        synchronized (players) {
            if (!players.containsKey(username) || !players.get(username).isConnected()) {
                ClientConn client = new ClientConn(username, socket);
                logs.put(username, new ChatLog());
                players.put(username, client);
                client.sendRequest(RequestBuilder.createRequest("loged").build());
                updateFriendsLists(username);
            } else {
                return ServerCode.ALREADY_CONNECTED_ACCOUNT;
            }
        }
        return ServerCode.OK;
    }
    
    public static void removePlayer(ClientConn con){
        try {
            con.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized (players) {
            con.leaveGame();
            logs.remove(con.getUsername());
            players.remove(con.getUsername());
            System.out.println("cerrada " + players.size());
        }
        updateFriendsLists(con.getUsername());
    }

    static boolean createGame(String username, Request request, ClientConn.CreateAction observer) {
        System.out.println("CREAR PARTIDA");
        ClientConn client;
        boolean ownerIsWhite;
        try {
            String name = request.get("name");
            if(games.containsKey(name)){
                observer.setErrorMessage("nombre de sala ya existente");
                return false;
            }
            ownerIsWhite = request.getOrDefault("ownerIsWhite", "true").equals("true");
            String password = request.get("password");
            client = players.get(username);
            Game game = new Game(name, password, client, ownerIsWhite, ChessManager.fromJSON(request.get("chess")));
            games.put(name, game);
            
            client.setGame(game);
            return true;
        } catch (JsonProcessingException ex) {
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
           return false;
        }
    }
    
    static void removeGame(Game game) {
        games.remove(game.getName());
    }

    static boolean joinGame(String username, Request request) {
        System.out.println("UNIRSE PARTIDA");
        ClientConn client;
        Game game;
        String name;
        
        name = request.get("name");
        if(name == null || !games.containsKey(name)) return false;
        client = players.get(username);
        game = games.get(name);
        if(!game.setSecondPlayer(client)) return false;
        client.setGame(game);
        return true;
    }

    private static void updateFriendsLists(String username) {
        for (Player player : DatabaseManager.getFriendsByUsername(username)) {
            Optional<ClientConn> optConn = getPlayer(player.getUsername());
            if (!optConn.isEmpty()) {
                optConn.get().sendFriends();
            }
        }
    }
}
