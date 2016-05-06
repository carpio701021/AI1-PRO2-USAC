/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Carro;

import Laberinto.Camino;
import Laberinto.Genotipo;

/**
 *
 * @author javier
 */
public class Carro {
    
    private int direccionActual = Camino.NORTE;
    
    public void mover(int cardinalidad){
        switch(cardinalidad){
            case 0:
                moverNorte();
                break;
            case 1:
                moverSur();
                break;
            case 2:
                moverEste();
                break;
            case 3:
                moverOeste();
                break;
            default:
                break;
        }
    }
    
    public void moverNorte(){
        System.out.println("Carro se movio al norte");
    }
    
    public void moverSur(){
        System.out.println("Carro se movio al sur");
    }
        
    public void moverEste(){
        System.out.println("Carro se movio al este");
    }
        
    public void moverOeste(){
        System.out.println("Carro se movio al oeste");
    }
    
    public boolean[] tomarEntorno(){
        System.out.println("Toma de entorno");
        boolean[] res = new boolean[4];
        res[0]=true; //norte
        res[1]=false; //sur
        res[2]=true; //este
        res[3]=false; //oeste
        return res;
    }
    
    public void moverANuevoIntento(Genotipo gt){
        int[] cromosomas = gt.getCromosomas();
        //for (int i=cromosomas.length-1;i>=0;i--){
        for (int i=0;i<cromosomas.length;i++){
            switch(cromosomas[i]){
                case 0:
                    this.moverNorte();
                    continue;
                case 1:
                    this.moverSur();
                    continue;
                case 2:
                    this.moverEste();
                    continue;
                case 3:
                    this.moverOeste();
                    continue;
                default:
                    System.out.println("Error en moverANuevoIntento");
                break;
            }
        }
    }
    
    //carro.volverAlInicio(camino.getGenotipo());
    public void volverAlInicio(Genotipo gt){
        int[] cromosomas = gt.getCromosomas();
        for (int i=cromosomas.length-1;i>=0;i--){
            switch(cromosomas[i]){
                case 0:
                    this.moverSur();
                    continue;
                case 1:
                    this.moverNorte();
                    continue;
                case 2:
                    this.moverOeste();
                    continue;
                case 3:
                    this.moverEste();
                    continue;
                default:
                    System.out.println("Error en volverAlInicio()");
                break;
            }
        }
    }
    
}
