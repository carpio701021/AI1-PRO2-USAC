/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camara;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Braulio
 */
public class Camara {
        Runtime rt = Runtime.getRuntime();
        int increment;
        public String fileName;
        FastRGB convertidor;
        int[][] result;
        public double frente;
        public double derecha;
        public double izquierda;
        public boolean color;//true verde, false negro
        public Camara() {

            result = null;
        }

        public int Tomar() {
            try {
                System.out.println("tomando foto");
                fileName = "/home/ia/Desktop/imagen.jpg";
                Process p = rt.exec("raspistill -t 800 -o " + fileName);
                p.waitFor();
                Thread.sleep(2000);
                return Analizar();

            } catch (Exception e) {
                System.out.println("Exception:" + e.getMessage());
                return 0;
            }
        }

        public int Analizar() {

            BufferedImage hugeImage;
            String val = "";
            try {
                hugeImage = ImageIO.read(new File(fileName));
                convertidor = new FastRGB(hugeImage);
                System.out.println("");
                int[][] result = null;
                System.out.println("Resultados");

                result = convertTo2DUsingGetRGB(hugeImage);
                System.out.println(result.length + ", " + result[0].length);

                frente = RegionFront(result);
                derecha = RegionDer(result);
                izquierda = RegionIzq(result);
                
                
                if (derecha >= 0.5&&izquierda>=0.5&&frente>=0.5) {
                   
                    System.out.println("frente");
                    if(color=true){
                        return 2;
                    }else{
                        return 1;
                    }
                }

            } catch (IOException ex) {
                System.out.println("error: " + ex.toString());
                val = "error";
            }

            return 0;
        }

        boolean verNegro(float r, float g, float b ){
            
            if(r<160 &&g<160 && b<160&&Math.abs((r-g))<3){
                if(Math.abs((r-b))<3){
                    
                    return true;
                }
            }
            return false;
        }
        
        boolean verVerde(float r, float g, float b )
        {
            
            return b <= g/ 1.4 && r <= g / 1.4;
        }
        
        double RegionDer(int[][] result) {
            /*
       *
       *
       *
             */
            double cont = 0;
            double negros = 0;
            double verdes=0;
            for (int i = (convertidor.getWidth() - 400); i < (convertidor.getWidth()); i++) {
                for (int j = (1200); j < (convertidor.getHeight()); j++) {

                    Color color = new Color(result[i][j]);
                    if (verNegro(color.getRed(),color.getGreen(),color.getBlue())) {
                        negros++;
                    }else if(verVerde(color.getRed(),color.getGreen(),color.getBlue())){
                        verdes++;
                    }
                    cont++;
                }

            }
            //System.out.print("verdes:"+verdes+", total: "+cont+" % "+(verdes/cont)+"");
            if(negros>verdes){
                this.color=false;
                 System.out.print(" % " + (negros / cont) + "");

                 return (negros / cont);
            }else{
                this.color=true;
                 System.out.print(" verde % " + (verdes / cont) + "");

                 return (verdes/ cont);
            }
        }

        double RegionIzq(int[][] result) {
            /*
       *
       *
       *
             */
            double cont = 0;
            double negros = 0;
            double verdes = 0;
            for (int i = 0; i < (400); i++) {
                for (int j = (1200); j < (convertidor.getHeight()); j++) {
                    Color color = new Color(result[i][j]);
                    if (verNegro(color.getRed(),color.getGreen(),color.getBlue())) {
                        negros++;
                    }
                    else if(verVerde(color.getRed(),color.getGreen(),color.getBlue())){
                        verdes++;
                    }
                    cont++;
                }

            }
            
             if(negros>verdes){
                this.color=false;
                 System.out.print(" % " + (negros / cont) + "");

                 return (negros / cont);
            }else{
                this.color=true;
                 System.out.print(" verde % " + (verdes / cont) + "");

                 return (verdes/ cont);
            }

        }

        double RegionFront(int[][] result) {
            /*
       *
       *
       *
             */
            double cont = 0;
            double negros = 0;
            double verdes=0;
            for (int i = (convertidor.getWidth() / 2) - 100; i < (convertidor.getWidth() / 2) + 100; i++) {
                for (int j = (convertidor.getHeight() / 2); j < (convertidor.getHeight()); j++) {

                    Color color = new Color(result[i][j]);
                    if (verNegro(color.getRed(),color.getGreen(),color.getBlue())) {
                        negros++;
                    }
                    else if(verVerde(color.getRed(),color.getGreen(),color.getBlue())){
                        verdes++;
                    }
                    cont++;

                }

            }
            
             if(negros>verdes){
                this.color=false;
                 System.out.print(" % " + (negros / cont) + "");

                 return (negros / cont);
            }else{
                this.color=true;
                 System.out.print(" verde % " + (verdes / cont) + "");

                 return (verdes/ cont);
            }
            
            
            //System.out.println("verdes:"+verdes+", total: "+cont+" % "+(verdes/cont)+"");
           
        }

        private int[][] convertTo2DUsingGetRGB(BufferedImage image) {
            int width = image.getWidth();
            int height = image.getHeight();

            int[][] result = new int[width][height];

            for (int col = 0; col < width; col++) {

                for (int row = 0; row < height; row++) {
                    result[col][row] = convertidor.getRGB(col, row);
                }
            }
            return result;
        }

    }