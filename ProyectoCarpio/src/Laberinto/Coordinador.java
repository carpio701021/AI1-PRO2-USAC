/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import Carro.Carro;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import pro2.pruebaEntorno;

/**
 *
 * @author javier
 */
public class Coordinador {

    Carro carro;
    Camino camino;
    Genetico algoritmo;

    //interfaz grafica
    JLabel jLabel2;
    JTextArea jTextArea1; //consolita que muestra los mensajes

    public Coordinador(JLabel jlabel, JTextArea jta) {
        this.jLabel2 = jlabel;
        this.jTextArea1 = jta;
    }

    public void setAsEntornoTomado() {
        this.entornoTomado = true;
    }

    private void actualizarImagen() {

        try {
            Image img = ImageIO.read(new File("grafica.png"));

            this.jLabel2.setIcon(null);
            //ImageIcon n = new ImageIcon("grafica.png");
            int min = ((jLabel2.getWidth() < jLabel2.getHeight()) ? jLabel2.getWidth() : jLabel2.getHeight());
            ImageIcon n = new ImageIcon(img.getScaledInstance(min, min, Image.SCALE_SMOOTH));

            n.getImage().flush();
            this.jLabel2.setIcon(n);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean entornoTomado = false;
    Thread miHilo = new Thread(new Runnable() {
        @Override
        public void run() {
            this.iniciarCiclo();
        }

        private void iniciarCiclo() {
            boolean termino = false;
            ArrayList<Genotipo> genotiposErroneos = new ArrayList();

            while (!termino) {
                //String test1, test2, test3, avg;
                //test1 = JOptionPane.showInputDialog("Please input mark for test 1: ");

                while (!entornoTomado) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(pruebaEntorno.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                entornoTomado = false;

                ///* vvvvvvvvvvvvvvvvvvvvvvvvvvv
                int[] entorno = carro.tomarEntorno();
                int procede = camino.setEstado(entorno);
                //while(!encontro){buscarEnCaminos}
                // */// ^^^^^^^^^^^^^^^^^^^^^^^^^^

                /*
                 int procede = camino.setEstado(
                        jToggleButton2.isSelected(),
                        jToggleButton5.isSelected(),
                        jToggleButton4.isSelected(),
                        jToggleButton3.isSelected());
                 */
                switch (procede) {
                    case 1:
                        genotiposErroneos.add(camino.getGenotipo());
                        System.out.println("Camino erroneo, volver a comenzar");
                        //patron de retorno
                        carro.volverAlInicio(camino.getGenotipo());
                        System.out.println("Carro está de nuevo en el inicio");
                        carro.moverANuevoIntento(camino.getIntentoNuevoGenotipo());
                        algoritmo.nuevoGenotipo(camino.getGenotipo());
                        //buscar nuevo camino y generar nuevo genotipo
                        //nodo inexplorado
                        break;
                    case 0:
                        int next = algoritmo.getNext();
                        camino.setNext(next);
                        carro.mover(next);
                        break;
                    //secuencia de salida, con instrucciones al rescatado
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        System.out.println("Se ha encontrado a la victima");
                        carro.volverAlInicioGuiando(camino.getGenotipo());
                        System.out.println("La victiva ha sido rescatada exitosamente");
                        termino = true;
                        break;
                    default:
                        break;
                }

                jTextArea1.append(camino.toString());
                //jTextArea1.setText(camino.toString());
                Graficador g = new Graficador();
                g.graphMaze(camino);
                actualizarImagen();
                actualizarImagen();
            }

        }
    });

    public void iniciarSolucion() {
        //Creamos un nuevo carro y un nuevo camino
        //Para este momento el carro debe estar en el primer cuadro dentro del laberinto
        carro = new Carro();
        camino = new Camino();
        //enviamos el carro y el camino para construir un algoritmo genético
        algoritmo = new Genetico(camino.getGenotipo());

        jTextArea1.setText("Iniciado" + "\n");
        this.miHilo.start();

    }

}
