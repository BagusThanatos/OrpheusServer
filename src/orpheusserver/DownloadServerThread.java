/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orpheusserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        PrintWriter p= null;
        BufferedInputStream bos=null;
        BufferedReader br=null;
        OutputStream os=null;
        try {
            os=s.getOutputStream();
            br= new BufferedReader(new InputStreamReader(s.getInputStream()));
            String string= br.readLine();
            StringTokenizer st = new StringTokenizer(string," ");
            String user= st.nextToken(), album= st.nextToken();
            
            File file= new File("/album/"+album+".zip");
            
            
            byte[] fileByteArray = new byte[(int)file.length()];
            os.write((int)file.length());
            
            bos = new BufferedInputStream(new FileInputStream(file));
            bos.read(fileByteArray,0,fileByteArray.length);
            
            //don't forget to wait for client to send something as a confirmation.
            s.getInputStream().read();
            
            os.write(fileByteArray, 0, fileByteArray.length);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(DownloadServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (os!=null) os.close();
                if (s!=null)
                    s.close();
                
            } catch (IOException ex) {
                Logger.getLogger(DownloadServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
