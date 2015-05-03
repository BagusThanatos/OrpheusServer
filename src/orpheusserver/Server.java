/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orpheusserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos (github.com/BagusThanatos)
 */
public class Server extends Thread{

    @Override
    public void run() {  
        ServerSocket s=null;
        try {
            s= new ServerSocket(10000);
            System.out.println(s.getInetAddress());
            while (true){
                Socket sock= s.accept();
                ServerThread st = new ServerThread(sock);
                st.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (s!=null){
                try {
                    s.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
