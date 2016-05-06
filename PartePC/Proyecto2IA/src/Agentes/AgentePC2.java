/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Braulio
 */
public class AgentePC2 extends Agent{
    
  /**
     * Clase que establece el comportamiento del AgentePC
     * @author Braulio Padilla
     */
    public class comportamiento extends CyclicBehaviour
    {
        private comportamiento() {
        }
        
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) 
            {
               // String [] vec = msg.getContent().split(",");
                System.out.println( " recibiendo " +msg.getContent() );
                
            }
            else
            {
                    block();
            }        
        }
    }
    
    @Override
    protected void setup() {
        Gui app = new Gui();
        app.setVisible(true);
        System.out.println( getAID().getName() + " Inicializado");
        addBehaviour(new comportamiento());//300000 300 segundos o 5 minutos
    }
}