/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orpheusserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos (github.com/BagusThanatos)
 */
public class ServerThread extends Thread{
    private Socket s;
    
    public ServerThread(Socket s){
        super();
        this.s=s;
    }
    
    private void login(StringTokenizer st){
        try {
            String username=st.nextToken();
            String password = st.nextToken();System.out.println(username+password);
            ResultSet rs = Database.getInstance().getData("select * from user where email_user='"+username+
                    "' and pass_user='"+password+"'");
            System.out.println("here");
            PrintWriter p = new PrintWriter(s.getOutputStream(),true);
            if (rs.isBeforeFirst()) { 
                p.println(username);
            }
            else p.println("WRONG");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void getAlbumList(StringTokenizer st){
        try {
            ResultSet rs = Database.getInstance().getData("select * from album");
            if(rs.isBeforeFirst()){
                String s="";
                while(rs.next()){
                    s+=rs.getString("id_album")+" "+rs.getString("nama_album")+" "+rs.getString("tgl_rilis")+" "+rs.getString("harga")+" ";
                }
                PrintWriter p = new PrintWriter(this.s.getOutputStream(),true);
                p.println(s);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void getAlbumData(StringTokenizer st){
        try {
            ResultSet rs = Database.getInstance().getData("select * from album where id_album='"+st.nextToken()+"'");
            if(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void getUserData(StringTokenizer st){
        try {
            ResultSet rs= Database.getInstance().getData("select * from user where id_user='"+st.nextToken()+"'");
            if(rs.next()){
                String s = rs.getString("nama_user")+" "+rs.getString("alamat_user")+" "+rs.getString("money");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void userBuy(StringTokenizer st){
        Database.getInstance().query("insert into library_user ('"+st.nextToken()+"','"+st.nextToken()+"')");
    } 

    @Override
    public void run(){
        try {
            String string;
            InputStream is = s.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            while((string=in.readLine())!=null){
                StringTokenizer st = new StringTokenizer(string, " ");System.out.println(in.toString());
                String temp=st.nextToken();
                System.out.println(temp);
                if (temp.equals("LOGIN")) login(st);
                else if (temp.equals("LOGOUT")) break;
                else if (temp.equals("GETALBUMLIST")) getAlbumList(st);
                else if (temp.equals("GETALBUMDATA")) getAlbumData(st);
                else if (temp.equals("GETUSERDATA")) getUserData(st);
                else if (temp.equals("USERBUY")) userBuy(st);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if (s!=null) try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
