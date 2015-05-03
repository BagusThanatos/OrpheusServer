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
public class DownloadServer extends Thread{
    @Override
    public void run(){
        ServerSocket ss=null;
        try {
            ss = new ServerSocket(10001);
            while (true){
                Socket s = ss.accept();
                DownloadServerThread d = new DownloadServerThread(s);
                d.start();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(DownloadServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if (ss!=null) try {
                ss.close();
            } catch (IOException ex) {
                Logger.getLogger(DownloadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
