/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Carro;

import Laberinto.Camino;
import Laberinto.Genotipo;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author javier
 */
public class Carro {

    private int direccionActual = Camino.NORTE;

    public void mover(int cardinalidad) {
        switch (cardinalidad) {
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

    public void moverNorte() {
        System.out.println("Carro se movio al norte");
    }

    public void moverSur() {
        System.out.println("Carro se movio al sur");
    }

    public void moverEste() {
        System.out.println("Carro se movio al este");
    }

    public void moverOeste() {
        System.out.println("Carro se movio al oeste");
    }

    public int[] tomarEntorno() {
        System.out.println("Toma de entorno");
        int[] res = new int[4];
        res[0] = 0; //norte
        res[1] = 1; //sur
        res[2] = 0; //este
        res[3] = 1; //oeste
        return res;
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
            this.mover(cromosomas[i]);
        }
    }

    //carro.volverAlInicio(camino.getGenotipo());
    public void volverAlInicioGuiando(Genotipo gt) {
        int[] cromosomas = gt.getCromosomas();
        Queue<Integer> q = new LinkedList();

        for (int i = cromosomas.length - 1; i >= 0; i--) {
            q.add(cromosomas[i]);

            this.mover(cromosomas[i]);

            if (i > 2) {
                enviarMensajeAGuiando(q.poll());
            }
        }

        while (q.size() > 0) {
            enviarMensajeAGuiando(q.poll());
        }
    }

    private void enviarMensajeAGuiando(int direccion) {
        switch (direccion) {
            case 0:
                //this.moverSur();
                //mensaje de mover al sur
                break;
            case 1:
                //this.moverNorte();
                //mensaje de mover al norte
                break;
            case 2:
                //this.moverOeste();
                //mensaje de mover al oeste
                break;
            case 3:
                //this.moverEste();
                //mensaje de mover al este
                break;
            default:
                System.out.println("Error en volverAlInicioGuiando()");
                break;
        }
    }

}
