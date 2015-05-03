/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orpheusserver;

import java.net.Socket;

/**
 *
 * @author BagusThanatos (github.com/BagusThanatos)
 */
public class DownloadServerThread extends Thread{
    Socket s;
    
    public DownloadServerThread(Socket s){
        this.s=s;
    }
    @Override
    public void run(){
        
    }
    
}
