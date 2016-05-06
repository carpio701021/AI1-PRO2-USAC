/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Agente que sera ejecutado en la computadora
 * @author Braulio
 */
public class AgentePC extends Agent  {

    /**
     * Clase que establece el comportamiento del AgentePC
     * @author Braulio Padilla
     */
    public static class prueba implements Serializable
    {
        int y = 10;
        int x = 20;
        String cad = "20";
    }
    public class comportamiento extends CyclicBehaviour
    {


        @Override
        public void action() {
            prueba a = new prueba();
            String mensaje = Gui.message; ;// Interfaz.mensaje;
            if (!mensaje.equals(""))
            {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("AgentePC2",AID.ISLOCALNAME));
                    msg.setLanguage("English");
                    msg.setOntology("Weather-forecast-ontology");
                    msg.setContentObject(a);
                    //msg.setContent(mensaje );
                    send(msg);
                    Gui.message = "";
                    System.out.println("Mensaje enviado " + mensaje);
                } catch (IOException ex) {
                    Logger.getLogger(AgentePC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }
    
    @Override
    protected void setup() {
        
        System.out.println( getAID().getName() + " Inicializado");
        addBehaviour(new comportamiento());//300000 300 segundos o 5 minutos
    }
    
}

