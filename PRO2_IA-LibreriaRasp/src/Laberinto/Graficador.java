/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javier
 */
public class Graficador {

    public void graphMaze(Camino camino) {
        this.ejecutar(generarScript(camino));
    }
    
    public static String generarScript(Camino camino){
        String c = "digraph G{" + "\n";
        
        c+= "graph [ overlap=true];\n" +
                "edge[weight=0.1];\n";

        //c += "subgraph cluster0 {" + "\n";
        //c += "rankdir=LR;" + "\n";
        c += "node [shape=record];" + "\n";

        //mazeRun
        c += mazeRun(camino.getInicio(), -1, 0, "", 0, 0);
        c += mazeRunCaminoActual(camino.getGenotipo());
        //c += "}";//cierre cluster0

        c += "}";
        return c;
    }
    
    private static double distancia = 1;
    private static double nodoSize = 1;
    private static String propTamNodo = "width="+nodoSize+",height="+nodoSize+"";


    
    private static String mazeRunCaminoActual(Genotipo genotipo){
        Hijo temp = genotipo.getGenIni();
        String nodoCaminoProp = "width=1,height=1,shape=diamond,style=filled, color = skyblue";
        String t = "";
        double x = 0;
        double y = 0;
        for ( int i = 0 ; i < genotipo.cromosomas.size() ; i++ ){
            if(genotipo.cromosomas.get(i)== Camino.NORTE){
                temp=temp.getNorte();
                t += "\"nodeCamino_" + (x) + "_" + (y) + "\"["+ nodoCaminoProp
                    +",pos=\"" + (x) + "," + (y) + "!\",label = \" \"];" + "\n";
                y=y+distancia*2;
            }else if(genotipo.cromosomas.get(i)== Camino.SUR){
                temp=temp.getSur();
                t += "\"nodeCamino_" + (x) + "_" + (y) + "\"["+ nodoCaminoProp
                    +",pos=\"" + (x) + "," + (y) + "!\",label = \" \"];" + "\n";
                y=y-distancia*2;
            }else if(genotipo.cromosomas.get(i)== Camino.ESTE){
                temp=temp.getEste();
                t += "\"nodeCamino_" + (x) + "_" + (y) + "\"["+ nodoCaminoProp
                    +",pos=\"" + (x) + "," + (y) + "!\",label = \" \"];" + "\n";
                x=x+distancia*2;
            }else if(genotipo.cromosomas.get(i)== Camino.OESTE){
                temp=temp.getOeste();
                t += "\"nodeCamino_" + (x) + "_" + (y) + "\"["+ nodoCaminoProp
                    +",pos=\"" + (x) + "," + (y) + "!\",label = \" \"];" + "\n";
                x=x-distancia*2;
            }
        }
        return t;        
    }

    private static String mazeRun(Hijo h, int anterior, int nivel, String npadre, double x, double y) {
        String t = "";
        if (h == null) {
            return t;
        }
        String nombreNodo = nivel + "_" + (anterior + 1) + "_" + npadre;

        String propColorNodoObstaculo = "style=filled, color = gray";
        String propColorNodoCamino = "style=filled, color = blue";
        if(h.isCaminoErroneo()){
            propColorNodoCamino = "style=filled, color = red";
        }
    
        //terreno inexplorado
        if (!(Camino.NORTE == anterior) && h.getNorte() == null) {
            t += "\"nodeM_" + (x) + "_" + (y + distancia) + "\"["+propTamNodo+","+propColorNodoObstaculo
                    +",pos=\"" + (x) + "," + (y + distancia) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.SUR == anterior) && h.getSur() == null) {
            t += "\"nodeM_" + (x) + "_" + (y - distancia) + "\"["+propTamNodo+","+propColorNodoObstaculo
                    +",pos=\"" + (x) + "," + (y - distancia) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.ESTE == anterior) && h.getEste() == null) {
            t += "\"nodeM_" + (x + distancia) + "_" + (y) + "\"["+propTamNodo+","+propColorNodoObstaculo
                    +",pos=\"" + (x + distancia) + "," + (y) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.OESTE == anterior) && h.getOeste() == null) {
            t += "\"nodeM_" + (x - distancia) + "_" + (y) + "\"["+propTamNodo+","+propColorNodoObstaculo
                    +",pos=\"" + (x - distancia) + "," + (y) + "!\",label = \" \"];" + "\n";
        }

        //asdf
        //if(h.getNorte()!=null) x += ""+1;
        if (!(Camino.NORTE == anterior)) {
            t += mazeRun(h.getNorte(), Camino.SUR, nivel + 1, nombreNodo, x, y + distancia*2);
        }else{
            t += "\"nodeM_" + (x) + "_" + (y + distancia) + "\"["+propTamNodo+","+propColorNodoCamino
                    +",pos=\"" + (x) + "," + (y + distancia) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.SUR == anterior)) {
            t += mazeRun(h.getSur(), Camino.NORTE, nivel + 1, nombreNodo, x, y - distancia*2);
        }else{
            t += "\"nodeM_" + (x) + "_" + (y - distancia) + "\"["+propTamNodo+","+propColorNodoCamino
                    +",pos=\"" + (x) + "," + (y - distancia) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.ESTE == anterior)) {
            t += mazeRun(h.getEste(), Camino.OESTE, nivel + 1, nombreNodo, x + distancia*2, y);
        }else{
            t += "\"nodeM_" + (x + distancia) + "_" + (y) + "\"["+propTamNodo+","+propColorNodoCamino
                    +",pos=\"" + (x + distancia) + "," + (y) + "!\",label = \" \"];" + "\n";
        }
        if (!(Camino.OESTE == anterior)) {
            t += mazeRun(h.getOeste(), Camino.ESTE, nivel + 1, nombreNodo, x - distancia*2, y);
        }else{
            t += "\"nodeM_" + (x - distancia) + "_" + (y) + "\"["+propTamNodo+","+propColorNodoCamino
                    +",pos=\"" + (x - distancia) + "," + (y) + "!\",label = \" \"];" + "\n";
        }
        //x += "node_" + nombreNodo + "[label = \"<f0> " + t.valor + "|<f1> \"];" + "\n";
        String txtCamino = ((h.isEntradaSalida())?"E/S":(h.isExplorado())?"+":"?");
        
        String dir = (anterior == -1) ? "inicio" : ((anterior == 0) ? "S" : (anterior == 1) ? "N" : (anterior == 2) ? "O" : "E");
        t += "node_" + nombreNodo + "["+propTamNodo+","+propColorNodoCamino+",pos=\"" + x + "," + y + "!\",label = \"" + txtCamino + "\"];" + "\n";
        //if(npadre != "") t += "node_" + npadre + "-> node_" + nombreNodo + "\n";
        return t;
    }

    private void ejecutar(String codigo) {
        //String dotPath = "C:\\Program Files (x86)\\Graphviz2.32\\bin\\dot.exe";
        String fileInputPath = "sourceGraphviz.dot";
        ProcessBuilder pbuilder;
        String fileOutputPath = "grafica.png";
        //String tParam = "-Tpng";
        //String tOParam = "-o";
        File dot = new File(fileInputPath);
        File jpg = new File(fileOutputPath);
        if (dot.exists()) {
            dot.delete();
        }
        if (jpg.exists()) {
            jpg.delete();
        }
        FileWriter fw;
        try {
            fw = new FileWriter(dot);
            fw.write(codigo);
            fw.close();
            //Process a = new ProcessBuilder(dotPath, tParam, tOParam, fileOutputPath, fileInputPath).start();
            Process a = new ProcessBuilder("dot", "-Kfdp", "-n", "-Tpng", "-o", fileOutputPath, fileInputPath).start();
            //pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", fileOutputPath, fileInputPath);

            //pbuilder = new ProcessBuilder("dot", "-Kfdp ", "-n", "-Tpng", "-o", fileOutputPath, fileInputPath);
            //pbuilder.redirectErrorStream(true);
            //Ejecuta el proceso
            //pbuilder.start();
            //System.out.println("Dot: ");
            a.waitFor();

        } catch (IOException ex) {
            Logger.getLogger(Graficador.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (InterruptedException ex) {
            Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
