/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


     

/**
 *
 * @author javier
 */
public class Genetico {
    
    Genotipo genotipo;
    
    public Genetico(Genotipo genotipo){
        this.genotipo = genotipo;
    }
    
    public void nuevoGenotipo(Genotipo genotipo){
        this.genotipo = genotipo;
    }
    
    public int getNext(){
        return mutacion();
    }
    
    public void inicio(){
        
    }
    
    private Hijo seleccion(){
        Hijo temp = genotipo.getGenIni();
        for ( int i = 0 ; i < genotipo.cromosomas.size() ; i++ ){
            if(genotipo.cromosomas.get(i)== Camino.NORTE){
                temp=temp.getNorte();
            }else if(genotipo.cromosomas.get(i)== Camino.SUR){
                temp=temp.getSur();
            }else if(genotipo.cromosomas.get(i)== Camino.ESTE){
                temp=temp.getEste();
            }else if(genotipo.cromosomas.get(i)== Camino.OESTE){
                temp=temp.getOeste();
            }
        }
        return temp;        
    }
    
    private void cruzamiento(){
        
        
    }
    
    private int mutacion(){
        Hijo padre = seleccion();
        int nuevoHijo = reemplazo(padre,this.genotipo);
        return nuevoHijo;
    }
    
    public static int reemplazo(Hijo padre, Genotipo genotipo){
        Random rnd = new Random();
        ArrayList<Integer> croms = genotipo.cromosomas;
        int ultimoMov = ((croms.size()>0)? croms.get(croms.size()-1):-1);
        int contrarioUltimoMov = ((ultimoMov==0)?1:((ultimoMov==1)?0:((ultimoMov==2)?3:((ultimoMov==3)?2:-1))));
        int contadorError = 0;
        while(contadorError < 500){
            contadorError++;
            int gen = (int)(rnd.nextDouble() * 4 + 0);
            if(gen == contrarioUltimoMov)continue;
            if( gen == Camino.NORTE && !(padre.getNorte() == null || padre.getNorte().isCaminoErroneo())){
                genotipo.addCromosoma(gen,padre.getNorte());
                return gen;
            }else if( gen == Camino.SUR && !(padre.getSur() == null || padre.getSur().isCaminoErroneo())){
                genotipo.addCromosoma(gen,padre.getSur());
                return gen;
            }else if( gen == Camino.ESTE && !(padre.getEste() == null || padre.getEste().isCaminoErroneo())){
                genotipo.addCromosoma(gen,padre.getEste());
                return gen;
            }else if( gen == Camino.OESTE && !(padre.getOeste() == null || padre.getOeste().isCaminoErroneo())){
                genotipo.addCromosoma(gen,padre.getOeste());
                return gen;
            }
        }
        return -1;
    }
    
    
}
