/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import java.util.ArrayList;

/**
 *
 * @author javier
 */
public class Genotipo {
    
    private Hijo inicio;
    private Hijo fin;
    
    ArrayList<Integer> cromosomas;
    
    
    public Genotipo(Hijo ini,Hijo fin){
        this.inicio = ini;
        this.fin = fin;
        cromosomas = new ArrayList();
    }
    
    public Hijo getGenIni(){
        return inicio;
    }
    
    public Hijo getGenFin(){
        return fin;
    }
        
    public void setGenIni(Hijo h){
        this.inicio = h;
    }
    
    public void setGenFin(Hijo h){
        this.fin = h;
    }
    
    public Hijo addCromosoma(int direccion,Hijo h){
        cromosomas.add(direccion);
        this.fin = h;
        this.fin.setExplorado();
        return h;
    }
    
    public int[] getCromosomas(){
        int[] arreglo = new int[this.cromosomas.size()];
        for(int i=0;i<arreglo.length;i++){
            arreglo[i] = this.cromosomas.get(i);
        }
        return arreglo;
    }
    
    @Override
    public String toString(){
        String genotipo = "";
        for(int i=0;i<this.cromosomas.size();i++){
            genotipo+=""+this.cromosomas.get(i);
        }
        return genotipo;
    }
}
