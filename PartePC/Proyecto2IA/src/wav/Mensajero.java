/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wav;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javier
 */
public class Mensajero {
    
    private String ip;
    private int puerto;
    private String path = "RecordAudio.wav";
    
    public Mensajero(String ip,int puerto, String path){
        this.ip = ip;
        this.puerto = puerto;
        this.path = path;
    }
    
    public void Send() {

        Path path = Paths.get(this.path);
        byte[] array;
        try {
            array = Files.readAllBytes(path);
            Socket sock = new Socket(this.ip, this.puerto);
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            System.out.println("Array Length - " + array.length);
            dos.writeInt(array.length);
            dos.write(array);
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println(reader.readLine());

        } catch (IOException ex) {
        }

    }
}
