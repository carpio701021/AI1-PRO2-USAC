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
/**
 * Agente que sera ejecutado en la computadora
 * @author Braulio
 */
public class AgentePC extends Agent  {

    /**
     * Clase que establece el comportamiento del AgentePC
     * @author Braulio Padilla
     */
    public class comportamiento extends CyclicBehaviour
    {


        @Override
        public void action() {
            String mensaje = Gui.message; ;// Interfaz.mensaje;
            if (!mensaje.equals(""))
            { 
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("AgentePI",AID.ISLOCALNAME));
                    msg.setLanguage("English");
                    msg.setOntology("Weather-forecast-ontology");
                    //msg.setContentObject(a);
                    msg.setContent(mensaje );
                    send(msg);
                    Gui.message = "";
                    System.out.println("Mensaje enviado " + mensaje);
            }
        } 
    }
    
    @Override
    protected void setup() {
        
        System.out.println( getAID().getName() + " Inicializado");
        addBehaviour(new comportamiento());//300000 300 segundos o 5 minutos
    }
    
}

