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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Braulio
 */
public class AgenteCHAT extends Agent{
    
    public static String message = "" ; 
    public static boolean turno;
    public static boolean iniciar;
    
     public class comportamiento extends CyclicBehaviour
    {
         
         
         private final String [] chat1 = {"Voy por tí",
                                           "Ayuda! Ayuda!"};
         
         private final String [] chat2 = {"Me estoy moviendo, ten fe!",
                                          "Porque tardas tanto?",
                                          "Mi algoritmo no es tan"
                 + "                       bueno, pero te encontrará"
         };         
         private final String [] chat3 = {"Me estoy quedando sin batería",
                                         "Casi te encuentro!", 
                                         "Aún no apareces"
         };
         private final String [] chat4 = {"Ya me perdi ",
                                        "No, tienes que ayudarme", 
                                        "Adios!! Orale!"
         };
         private final String [] chat5 = {"Se esta oscureciendo",
                                           "Gracias, por la ayuda...  mi camara sin flash te lo agradece.",
                                           "de nada, ja ja."
         };
         private final String [] chat6 = {"Te he encontrado!!!!", 
                                           "Gracias"
         };
         
         private ArrayList<String[]> chats ;
         boolean isInMessage;
         
         int  numeroChat;
         int lineaDelChat;


        public void IncrementarLineaDelChat() {
            if(lineaDelChat + 1 > this.chats.get(numeroChat).length-1)
            {
                this.lineaDelChat = 1;
                this.numeroChat = -1;
            }
            else
            {
                this.lineaDelChat++;
            }
        }
         
         public int GetNumeroChat()
         {
             if(numeroChat >= 0 ) return numeroChat;
             
             return -1;
         }
         
         public int GetLineaChat()
         {
             if(lineaDelChat > 1) return lineaDelChat;
             return 1;
         }
         
         public int NumeroBuscarChat(String msg)
         {
             int num = -1;
             for(int i = 0; i < this.chats.size() ; i++)
             {
                 if (chats.get(i)[0].equals(msg))
                 {
                     num = i;
                 }
             }
             return num;
         }
         private int GenerarRandomChat()
         {
             this.numeroChat = (int)(Math.random()*chats.size()-1 + 0 );
             this.lineaDelChat = 1;
             return numeroChat;
             
         }
         public void Chat(String msg)
         {

            if(turno)
            {
                this.numeroChat = (GetNumeroChat()>=0) ? GetNumeroChat() : GenerarRandomChat();
                SendMessage(this.chats.get(this.numeroChat)[this.lineaDelChat]);
                IncrementarLineaDelChat();

            }
            else
            {
                if(GetNumeroChat() == -1)
                {
                    this.numeroChat = NumeroBuscarChat(msg);
                    this.lineaDelChat = 2; 
                }
                else
                {
                    if (lineaDelChat + 1 < this.chats.get(numeroChat).length-1)
                    {
                        lineaDelChat++;
                    }
                    else
                    {
                        numeroChat = -1;
                        lineaDelChat = 1;
                    }
                }
                Gui.TEXTAREA = msg;
                turno = true;
                
            }
             
         }

        public comportamiento() {
            chats = new  ArrayList<>();
            chats.add(chat1);
            chats.add(chat2);
            chats.add(chat3);
            chats.add(chat4);
            chats.add(chat5);
            chats.add(chat6);
            
        }
         
        public void SendMessage(String mensaje)
        {
            //String mensaje = AgenteCHAT.message; // Interfaz.mensaje;
            if (!mensaje.equals(""))
            {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgentePC99",AID.ISLOCALNAME));
                msg.setLanguage("English"); 
                msg.setOntology("Weather-forecast-ontology"); 
                msg.setContent(mensaje );
                send(msg);
                //AgenteCHAT.message = "";
                System.out.println("Mensaje enviado " + mensaje);
            }
        }
        @Override
        public void action() {
             try {
                 if(iniciar){
                     if(turno)
                     {
                         Chat("");
                     }
                     else
                     {
                         ACLMessage msg = myAgent.receive();
                         if (msg != null)
                         {
                             System.out.println( " recibiendo " +msg.getContent() );
                             Chat(msg.getContent());
                             Thread.sleep(5000);
                         }
                         else
                         {
                             block();
                         }
                     }
                 }
             } catch (InterruptedException ex) {
                 Logger.getLogger(AgenteCHAT.class.getName()).log(Level.SEVERE, null, ex);
             }
        } 
    }
    
    @Override
    protected void setup() {
        
        System.out.println( getAID().getName() + " Inicializado");
        
        addBehaviour(new comportamiento());//300000 300 segundos o 5 minutos
    }
    
}
