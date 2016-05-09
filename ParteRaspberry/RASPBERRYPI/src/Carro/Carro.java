/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Carro;

import Camara.Camara;
import Laberinto.Camino;
import Laberinto.Genotipo;
import com.pi4j.wiringpi.SoftPwm;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javier
 */


public class Carro {

    private int delayGiro = 30;
    private int delayRecto = 100;
    private int pwmizq = 30;
    private int pwmder = 30;
    private static final int PWM = 35;
    private Camara  camara = new Camara();
    public Queue<Integer> mensajeRescate = new LinkedList();

    private Queue<Integer> rescate = new LinkedList();

    private static final int NORTE = 0;
    private static final int SUR = 1;
    private static final int ESTE = 2;
    private static final int OESTE = 3;
    private int []  entorno = new int[4];

    private int cardinalidadActual = NORTE;

    public Carro() {
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
        SoftPwm.softPwmCreate(2, 0, 100);
        SoftPwm.softPwmCreate(0, 0, 100);
        SoftPwm.softPwmCreate(15, 0, 100);
        SoftPwm.softPwmCreate(1, 0, 100);
    }


    public int[] tomarEntorno() {
        analisis();
        return entorno;
    }

    public void MoveMotors(String direction) {
        System.out.println(direction);
        try {
            switch (direction) {
                case "recto":
                    SoftPwm.softPwmWrite(2, 0);
                    SoftPwm.softPwmWrite(0, PWM);
                    SoftPwm.softPwmWrite(15, PWM);
                    SoftPwm.softPwmWrite(1, 0);

                    Thread.sleep(delayRecto * 10);
                    break;
                case "izquierda":
                    SoftPwm.softPwmWrite(2, 0);
                    SoftPwm.softPwmWrite(0, pwmizq);
                    SoftPwm.softPwmWrite(15, 0);
                    SoftPwm.softPwmWrite(1, pwmizq);

                    Thread.sleep(delayGiro * 10);

                    break;
                case "derecha":
                    SoftPwm.softPwmWrite(2, pwmder);
                    SoftPwm.softPwmWrite(0, 0);
                    SoftPwm.softPwmWrite(15, pwmder);
                    SoftPwm.softPwmWrite(1, 0);

                    Thread.sleep(delayGiro * 10);
                    break;
            }
            ShutDownMotors();

        } catch (InterruptedException ex) {
            //Logger.getLogger(AgentePI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ShutDownMotors() {
        SoftPwm.softPwmWrite(2, 0);
        SoftPwm.softPwmWrite(0, 0);
        SoftPwm.softPwmWrite(15, 0);
        SoftPwm.softPwmWrite(1, 0);
    }

    public void mover(int cardinalidad) {

        if (cardinalidadActual == cardinalidad) rescate.add(0);

        switch (cardinalidadActual) {
            case NORTE:
                switch (cardinalidad) {
                    case OESTE:
                        //IZQUIERDA
                        MoveMotors("izquierda");
                        rescate.add(1);
                        cardinalidadActual = OESTE;
                        break;
                    case ESTE:
                        //DERECHA;
                        MoveMotors("derecha");
                        rescate.add(2);
                        cardinalidadActual = ESTE;
                        break;
                    default:
                        MoveMotors("derecha");
                        MoveMotors("derecha");
                        cardinalidadActual = SUR;
                    //ERROR
                }
                break;
            case ESTE:
                switch (cardinalidad) {
                    case NORTE:
                        //IZQUIERDA
                        MoveMotors("izquierda");
                        rescate.add(1);
                        cardinalidadActual = NORTE;
                        break;
                    case SUR:
                        //DERECHA
                        MoveMotors("derecha");
                        rescate.add(2);
                        cardinalidadActual = SUR;
                        break;
                    default:
                        MoveMotors("izquierda");
                        MoveMotors("izquierda");
                        cardinalidadActual = OESTE;
                        break;
                }
                break;
            case SUR:
                switch (cardinalidad) {
                    case ESTE:
                        //IZQUIERDA
                        MoveMotors("izquierda");
                        rescate.add(1);
                        cardinalidadActual = ESTE;
                        break;
                    case OESTE:
                        //DERECHA
                        MoveMotors("derecha");
                        rescate.add(2);
                        cardinalidadActual = OESTE;
                        break;
                    default:
                        MoveMotors("derecha");
                        MoveMotors("derecha");
                        cardinalidadActual = NORTE;
                        //ERROR
                        break;
                }
                break;
            case OESTE:
                switch (cardinalidad) {
                    case SUR:
                        //IZQUIERDA
                        MoveMotors("izquierda");
                        rescate.add(1);
                        cardinalidadActual = SUR;
                        break;
                    case NORTE:
                        //DERECHA
                        MoveMotors("derecha");
                        rescate.add(2);
                        cardinalidadActual = NORTE;
                        break;
                    default:
                        MoveMotors("izquierda");
                        MoveMotors("izquierda");
                        cardinalidadActual = ESTE;
                        //ERROR
                        break;
                }
        }
        MoveMotors("recto");
    }
    
    public void analisis(){
            switch(this.cardinalidadActual)
            {
                case NORTE:
                        MoveMotors("izquierda");
                        this.entorno[3]=camara.Tomar();
                        MoveMotors("derecha");
                        
                        MoveMotors("derecha");
                        this.entorno[2]=camara.Tomar();
                        MoveMotors("izquierda");
                        
                        this.entorno[0]=camara.Tomar();
                    break;
                case SUR:
                   
                        MoveMotors("izquierda");
                        this.entorno[3]=camara.Tomar();
                        MoveMotors("derecha");

                        MoveMotors("derecha");
                        this.entorno[2]=camara.Tomar();
                        MoveMotors("izquierda");

                        this.entorno[0]=camara.Tomar();
                    
                    break;
                case ESTE:
                    
                        MoveMotors("izquierda");
                        this.entorno[0]=camara.Tomar();
                        MoveMotors("derecha");

                        MoveMotors("derecha");
                        this.entorno[1]=camara.Tomar();
                        MoveMotors("izquierda");

                        this.entorno[3]=camara.Tomar();
                        
                    break;
                case OESTE:
                        MoveMotors("izquierda");
                        this.entorno[1]=camara.Tomar();
                        MoveMotors("derecha");

                        MoveMotors("derecha");
                        this.entorno[0]=camara.Tomar();
                        MoveMotors("izquierda");

                        this.entorno[3]=camara.Tomar();
                    break;
            }
        }

    public void moverANuevoIntento(Genotipo gt) {
        int[] cromosomas = gt.getCromosomas();
        //for (int i=cromosomas.length-1;i>=0;i--){
        for (int i = 0; i < cromosomas.length; i++) {
            this.mover(cromosomas[i]);
        }
    }

    //carro.volverAlInicio(camino.getGenotipo());
    public void volverAlInicio(Genotipo gt) {
        int[] cromosomas = gt.getCromosomas();
        for (int i = cromosomas.length - 1; i >= 0; i--) {
            this.mover(invertirCardinalidad(cromosomas[i]));
        }
    }

    //carro.volverAlInicio(camino.getGenotipo());
    public void volverAlInicioGuiando(Genotipo gt) {
        int[] cromosomas = gt.getCromosomas();

        for (int i = cromosomas.length - 1; i >= 0; i--) {


            this.mover(invertirCardinalidad( cromosomas[i] ) );

            if (i > 2) {
                this.mensajeRescate.add(rescate.poll());
            }
        }
        int t = rescate.size();
        for (int i = 0; i < t; i++) {
                this.mensajeRescate.add(rescate.poll());
        }
    }

    public int invertirCardinalidad(int cardin){
        return ((cardin==0)?1:((cardin==1)?0:((cardin==2)?3:((cardin==3)?2:(-1)))));
    }

    public void setDelayGiro(int delayGiro) {
        this.delayGiro = delayGiro;
    }

    public void setDelayRecto(int delayRecto) {
        this.delayRecto = delayRecto;
    }

    public void setPwmizq(int pwmizq) {
        this.pwmizq = pwmizq;
    }

    public void setPwmder(int pwmder) {
        this.pwmder = pwmder;
    }

    

}


