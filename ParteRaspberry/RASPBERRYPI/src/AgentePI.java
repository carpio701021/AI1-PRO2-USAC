
import Carro.Carro;
import Laberinto.Camino;
import Laberinto.Coordinador;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import com.pi4j.wiringpi.SoftPwm;
import jade.core.AID;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Braulio
 * @author Monzon
 */
public class AgentePI extends Agent {
      
    /**
     * Clase que establece el comportamiento del AgentePC
     *
     * @author Braulio Padilla
     */
    public class Comportamiento extends CyclicBehaviour {

        Carro car = new Carro();
        private boolean iniciar;
        public Comportamiento() {
            
        }
         
        @Override
        public void action() {
            String content = "";
            ACLMessage msg = myAgent.receive();
            if (msg != null ) {
                content = msg.getContent();
                if (content.contains("Giro")) {
                    String valor = content.replace("Giro", "");
                    car.setDelayGiro( Integer.parseInt(valor));
                } else if (content.contains("Recto")) {
                    String valor = content.replace("Recto", "");
                    car.setDelayRecto(Integer.parseInt(valor));
                } else if (content.contains("pwmizq"))
                {
                    String valor = content.replace("pwmizq", "");
                    car.setPwmizq(Integer.parseInt(valor));
                    System.out.println("pwm seteado " + valor);
                }
                else if (content.contains("pwmder"))
                {
                
                    String valor = content.replace("pwmder", "");
                    car.setPwmder(Integer.parseInt(valor));
                }
                else if (content.contains("empezar"))
                {
                    iniciar = true;
                }
                else {

                    switch (content) {
                        case "Forward":
                            car.MoveMotors("recto");
                            break;
                        case "Left":
                            car.MoveMotors("izquierda");
                            break;
                        case "Rigth":
                            car.MoveMotors("derecha");
                            break;
                    }
                }
            } else if (iniciar == true){
                // System.out.println("nada");
                Thread hilo = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                       while(true)
                       {
                            if(car.mensajeRescate.peek()!= null){
                                Notificacion("AgentePC99",String.valueOf( car.mensajeRescate.remove() ));
                            }
                            if(Coordinador.grafica.equals(""))
                            {
                                Notificacion("AgentePC2",String.valueOf( "grafica"+Coordinador.grafica));
                                Coordinador.grafica = "";
                            }
                       }
                    }
                    
                }
                );
                hilo.start();
                
            }

        }

        //AgentePC2 vs AgentePC99
        public synchronized void Notificacion(String Agente ,String contenido) {
            if (!contenido.equals("")) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID(Agente, AID.ISLOCALNAME));
                msg.setLanguage("English");
                msg.setOntology("Weather-forecast-ontology");
                msg.setContent(contenido);
                send(msg);
                System.out.println(contenido);
            }
        }
    }

    @Override
    protected void setup() {
        System.out.println(getAID().getName() + " Inicializado");
        addBehaviour(new Comportamiento());//300000 300 segundos o 5 minutos
    }

}

