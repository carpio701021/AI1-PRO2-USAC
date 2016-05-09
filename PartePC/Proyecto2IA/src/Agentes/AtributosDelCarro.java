/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agentes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Braulio
 */
public class AtributosDelCarro implements Serializable {
    
    private int delayRecto;
    private int delayGiro;
    private int pwmLeft;
    private int pwmRigth;
    
    public static AtributosDelCarro ReadInstance() 
    {
        try {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream("carro.bin")) ;
            return (AtributosDelCarro)file.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println(ex.toString());
        }
        return null;
    }
    public static void SaveInstance(AtributosDelCarro c)
    {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("carro.bin"));
            file.writeObject(c);
            System.out.println("Objecto Guardado correctamente");
        } catch (IOException ex) {
            Logger.getLogger(AtributosDelCarro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public AtributosDelCarro(int delayRecto, int delayGiro, int pwmLeft, int pwmRigth) {
        this.delayRecto = delayRecto;
        this.delayGiro = delayGiro;
        this.pwmLeft = pwmLeft;
        this.pwmRigth = pwmRigth;
    }
    
    public int getDelayRecto() {
        return delayRecto;
    }

    public int getDelayGiro() {
        return delayGiro;
    }

    public int getPwmLeft() {
        return pwmLeft;
    }

    public int getPwmRigth() {
        return pwmRigth;
    }

    public void setDelayRecto(int delayRecto) {
        this.delayRecto = delayRecto;
    }

    public void setDelayGiro(int delayGiro) {
        this.delayGiro = delayGiro;
    }

    public void setPwmLeft(int pwmLeft) {
        this.pwmLeft = pwmLeft;
    }

    public void setPwmRigth(int pwmRigth) {
        this.pwmRigth = pwmRigth;
    }
    
    
}
