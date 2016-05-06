
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
       public class Carro
    {
        private int delayGiro = 30;
        private int delayRecto = 100;
        private int pwmizq = 30;
        private int pwmder = 30;
        private static final int PWM = 35;        
        
        private static final String NORTE = "1";
        private static final String SUR = "2";
        private static final String ESTE = "3";
        private static final String OESTE = "4";
        
        
        private String position = NORTE;
        
        public Carro() {
            com.pi4j.wiringpi.Gpio.wiringPiSetup();
            SoftPwm.softPwmCreate(2, 0, 100);
            SoftPwm.softPwmCreate(0, 0, 100);
            SoftPwm.softPwmCreate(15, 0, 100);
            SoftPwm.softPwmCreate(1, 0, 100);
        }
        public boolean[] tomarEntorno(){
            System.out.println("Toma de entorno");
            boolean[] res = new boolean[4];
            res[0]=true; //norte
            res[1]=false; //sur
            res[2]=true; //este
            res[3]=false; //oeste
            return res;
        }
        public void MoveMotors(String direction) {
            System.out.println(direction);
            try {
                switch (direction) {
                    case "recto":
                        SoftPwm.softPwmWrite(2, 0);
                        SoftPwm.softPwmWrite(0, PWM  );
                        SoftPwm.softPwmWrite(15, PWM);
                        SoftPwm.softPwmWrite(1, 0);
                        
                        Thread.sleep(delayRecto*10);
                        break;
                    case "izquierda":
                        SoftPwm.softPwmWrite(2, 0);
                        SoftPwm.softPwmWrite(0, pwmizq);
                        SoftPwm.softPwmWrite(15, 0);
                        SoftPwm.softPwmWrite(1, pwmizq);
                        
                        Thread.sleep(delayGiro*10);
                        
                        break;
                    case "derecha":
                        SoftPwm.softPwmWrite(2, pwmder);
                        SoftPwm.softPwmWrite(0, 0);
                        SoftPwm.softPwmWrite(15, pwmder);
                        SoftPwm.softPwmWrite(1, 0);
                        
                        Thread.sleep(delayGiro*10);
                        break;
                }
                ShutDownMotors();

            } catch (InterruptedException ex) {
                Logger.getLogger(AgentePI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void ShutDownMotors() {
            SoftPwm.softPwmWrite(2, 0);
            SoftPwm.softPwmWrite(0, 0);
            SoftPwm.softPwmWrite(15, 0);
            SoftPwm.softPwmWrite(1, 0);
        }
        
        public void MoveCarr(String mov) {
            switch (position) {
                case NORTE:
                    switch (mov) {
                        case OESTE:
                            //IZQUIERDA
                            MoveMotors("izquierda");
                            position = OESTE;
                            break;
                        case ESTE:
                            //DERECHA;
                            MoveMotors("derecha");
                            position = ESTE;
                            break;
                        case NORTE:
                            //RECTO
                            MoveMotors("recto");
                            position = NORTE;
                            break;
                        default:
                        //ERROR
                    }
                    break;
                case ESTE:
                    switch (mov) {
                        case NORTE:
                            //IZQUIERDA
                            MoveMotors("izquierda");
                            position = NORTE;
                            break;
                        case SUR:
                            //DERECHA
                            MoveMotors("derecha");
                            position = SUR;
                            break;
                        case ESTE:
                            //RECTO
                            MoveMotors("recto");
                            position = ESTE;
                            break;
                        default:
                            //ERROR
                            break;
                    }
                    break;
                case SUR:
                    switch (mov) {
                        case ESTE:
                            //IZQUIERDA
                            MoveMotors("izquierda");
                            position = ESTE;
                            break;
                        case OESTE:
                            //DERECHA
                            MoveMotors("derecha");
                            position = OESTE;
                            break;
                        case SUR:
                            //RECTO
                            MoveMotors("recto");
                            position = SUR;
                            break;
                        default:
                            //ERROR
                            break;
                    }
                    break;
                case OESTE:
                    switch (mov) {
                        case SUR:
                            //IZQUIERDA
                            MoveMotors("izquierda");
                            position = SUR;
                            break;
                        case NORTE:
                            //DERECHA
                            MoveMotors("derecha");
                            position = NORTE;
                            break;
                        case OESTE:
                            //RECTO
                            MoveMotors("recto");
                            position = OESTE;
                            break;
                        default:
                            //ERROR
                            break;
                    }
            }
        }
        
        public void setDelayGiro(int delayGiro) {
            this.delayGiro = delayGiro;
        }

        public void setDelayRecto(int delayRecto) {
            this.delayRecto = delayRecto;
        }

        public void setPwmizq(int pwmizq) {
            this.pwmizq = pwmizq;
        }

        public void setPwmder(int pwmder) {
            this.pwmder = pwmder;
        }

    }
    /**
     * Clase que establece el comportamiento del AgentePC
     *
     * @author Braulio Padilla
     */
    public class Comportamiento extends CyclicBehaviour {

        Carro car = new Carro();

        public Comportamiento() {
            
        }
         
        @Override
        public void action() {
            String content = "";
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                content = msg.getContent();
                if (content.contains("Giro")) {
                    String valor = content.replace("Giro", "");
                    car.delayGiro = Integer.parseInt(valor);
                } else if (content.contains("Recto")) {
                    String valor = content.replace("Recto", "");
                    car.delayRecto = Integer.parseInt(valor);
                } else if (content.contains("pwmizq"))
                {
                    String valor = content.replace("pwmizq", "");
                    car.pwmizq = Integer.parseInt(valor);
                    System.out.println("pwm seteado " + valor);
                }
                else if (content.contains("pwmder"))
                {
                
                    String valor = content.replace("pwmder", "");
                    car.pwmder = Integer.parseInt(valor);
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
            } else {
                // System.out.println("nada");
            }

        }


        public void Notificacion(String contenido) {
            if (!contenido.equals("")) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgentePC2", AID.ISLOCALNAME));
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

