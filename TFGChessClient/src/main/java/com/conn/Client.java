/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.conn;

import com.connutils.Request;
import com.gui.principalframe.PrincipalFrame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.formdev.flatlaf.FlatDarkLaf;
import com.utils.ConfigReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author migue
 */
public class Client {
    private static final String SERVIDOR = ConfigReader.getHost();  // Dirección del servidor
    private static final int PUERTO = ConfigReader.getPuerto();  // El puerto al que se conecta

    private static Scanner sc = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    
    private static PrincipalFrame frame = null;
    
    private static String username;
    
    public static void main(String[] args) {
        
        try {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            frame = new PrincipalFrame();

        } catch (IOException e) {
            closeConnection();
        }
    }
    
    public static void gotRequest(Request request){
        if (frame != null) {
            frame.gotRequest(request);
        }
    }
    
    public static void login(String username, String password) throws JsonProcessingException, IOException{
        
        connect();
        
        Request login = new Request("login");
//            Request login = new Request("register");
        login.put("username", username);
        login.put("password", password);

        Client.username = username;
        
        System.out.println("enviando: " + login);

        sendRequest(login);
    }
    
    public static void register(String username, String password) throws JsonProcessingException, IOException{
        
        connect();
        
        Request register = new Request("register");
        register.put("username", username);
        register.put("password", password);

        Client.username = username;
        
        System.out.println("enviando: " + register);

        sendRequest(register);
    }
    
    public static void sendRequest(Request request) throws JsonProcessingException{
        System.out.println("enviando: " + request);
        out.println(request.toJSON());

        out.flush();
    }

    public static void closeConnection() {
        if (out != null) {
            out.close();
            out = null;
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            in = null;
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket = null;
        }
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }

    public static void connect() throws IOException {
        if(socket == null || socket.isClosed()){
            socket = new Socket(SERVIDOR, PUERTO);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            HearThread hearThread = new HearThread(socket);
            hearThread.start();
        }
    }
    
    public static PrintWriter getWriter() throws IOException{
        if (socket.isClosed()) return null;
        if (out != null) return out;
        out = new PrintWriter(socket.getOutputStream(), true);
        return out;
    }
    
    public static BufferedReader getReader() throws IOException{
        if (socket.isClosed()) return null;
        if (in != null) return in;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in;
    }

    public static String getUsername() {
        return username;
    }
    
    
}
