/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import java.io.Serializable;

/**
 *
 * @author javier
 */
public class Camino  {

    public static int NORTE = 0;
    public static int SUR = 1;
    public static int ESTE = 2;
    public static int OESTE = 3;
    
    
    private Hijo inicio;
    private Hijo actual;
    private Genotipo genotipo;

    public Camino() {
        this.inicio = new Hijo();
        this.inicio.setAsInicio();
        this.actual = this.inicio;
        this.genotipo = new Genotipo(inicio, actual);
    }

    public Hijo getInicio() {
        return this.inicio;
    }

    private void resetGenotipo() {
        this.actual = inicio;
        this.genotipo = new Genotipo(inicio, actual);
    }

    public Genotipo getIntentoNuevoGenotipo() {
        resetGenotipo();
        //this.genotipo = nuevoGenotipo;

        //Genotipo ngeno = generarNuevoGenotipo(this.inicio, -1, this.genotipo);
        Hijo temp = this.inicio;
        while (temp.isExplorado()) {
            int next = Genetico.reemplazo(temp, genotipo);
            switch (next) {
                case 0:
                    temp = temp.getNorte();
                    break;
                case 1:
                    temp = temp.getSur();
                    break;
                case 2:
                    temp = temp.getEste();
                    break;
                case 3:
                    temp = temp.getOeste();
                    break;
                default:
                    System.out.println("Algo raro pasó en next: " + next);
                    break;
            }
            if (next == -1) {
                break;
            }
        }

        this.actual = temp;
        //this.actual.setExplorado();
        return this.genotipo;
    }

    public void setNext(int direccion) {
        if (direccion == NORTE && this.actual.getNorte() != null) {
            this.actual = this.actual.getNorte();
        } else if (direccion == SUR && this.actual.getSur() != null) {
            this.actual = this.actual.getSur();
        } else if (direccion == ESTE && this.actual.getEste() != null) {
            this.actual = this.actual.getEste();
        } else if (direccion == OESTE && this.actual.getOeste() != null) {
            this.actual = this.actual.getOeste();
        }

    }

    public int setEstado(int[] es) {
        int caminos = 0;
        /**
         * 0: hay obstaculo, 1: hay camino, >2: hay rescatado
         */
        if (es[0] == 2 || es[1] == 2 || es[2] == 2 || es[3] == 2) {
            return this.actual.addRescatando(es);
        } else {
            return setEstado(es[0] == 1, es[1] == 1, es[2] == 1, es[3] == 1);
        }
    }

    public int setEstado(boolean norte, boolean sur, boolean este, boolean oeste) {
        int caminos = 0;

        caminos = ((norte) ? 1 : 0) + ((sur) ? 1 : 0) + ((este) ? 1 : 0) + ((oeste) ? 1 : 0);
        if (caminos <= 1 && !(this.genotipo.cromosomas.size() <= 0)) {
            this.actual.setAsCaminoErroneo();
            setErroneos(this.inicio, -1);
            return 1;
        } else {
            int size = this.genotipo.cromosomas.size();
            int ultimo = (size > 0) ? this.genotipo.cromosomas.get(size - 1) : -1;
            switch (ultimo) {
                case 0:
                    sur = false;
                    break;
                case 1:
                    norte = false;
                    break;
                case 2:
                    oeste = false;
                    break;
                case 3:
                    este = false;
                    break;
                default:
                    break;
            }
            this.actual = this.actual.addHijos(norte, sur, este, oeste);
            return 0;
        }
        //genotipo.addCromosoma(norte, sur, este, oeste);        
    }

    private int setErroneos(Hijo h, int anterior) {
        if (h == null || !h.isExplorado()) {
            return 0;
        }

        int x = 0;
        if (!(Camino.NORTE == anterior)) {
            x += setErroneos(h.getNorte(), Camino.SUR);
        }
        if (!(Camino.SUR == anterior)) {
            x -= setErroneos(h.getSur(), Camino.NORTE);
        }
        if (!(Camino.ESTE == anterior)) {
            x += setErroneos(h.getEste(), Camino.OESTE);
        }
        if (!(Camino.OESTE == anterior)) {
            x += setErroneos(h.getOeste(), Camino.ESTE);
        }

        int caminos = ((h.getNorte() == null || h.getNorte().isCaminoErroneo()) ? 1 : 0)
                + ((h.getSur() == null || h.getSur().isCaminoErroneo()) ? 1 : 0)
                + ((h.getEste() == null || h.getEste().isCaminoErroneo()) ? 1 : 0)
                + ((h.getOeste() == null || h.getOeste().isCaminoErroneo()) ? 1 : 0);

        if ((caminos >= 3 && h.isExplorado()) && !h.isEntradaSalida()) {
            //if (caminos >= 3 ) {
            h.setAsCaminoErroneo();
        }

        return x;
    }

    public Genotipo getGenotipo() {
        return genotipo;
    }

    @Override
    public String toString() {
        int[][] matriz = new int[100][100];
        String vacio = "◦";
        String obstaculo = "█";
        String obstaculo2 = "■█" + "░▓▒";
        String txt = "";
        //txt += obstaculo  + vacio + obstaculo + vacio + obstaculo +"\n";
        //txt += vacio + obstaculo + vacio + obstaculo + vacio +"\n";
        //txt += "Profundidad x: " + tamanioN(this.inicio, -1) + "\n";
        txt += "Genotipo: " + genotipo.toString() + "\n";
        return txt;
    }

    private int tamanioN(Hijo h, int anterior) {
        if (h == null) {
            return 0;
        }
        int x = 0;
        if (h.getNorte() != null) {
            x += 1;
        }
        if (!(Camino.NORTE == anterior)) {
            x += tamanioN(h.getNorte(), Camino.SUR);
        }
        if (!(Camino.SUR == anterior)) {
            x -= tamanioN(h.getSur(), Camino.NORTE);
        }
        if (!(Camino.ESTE == anterior)) {
            x += tamanioN(h.getEste(), Camino.OESTE);
        }
        if (!(Camino.OESTE == anterior)) {
            x += tamanioN(h.getOeste(), Camino.ESTE);
        }
        return x;
    }

    /**
     * Comunicación Adelante = 0 Derecha = 1 Izquierda = 2
     */
}
