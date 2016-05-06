/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agentes;

import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import wav.Grabadora;

/**
 *
 * @author Braulio
 */
public class Gui extends JFrame implements ActionListener {
    public boolean grabando;
    public Thread thread;
    public Thread hilo;
    public JProgressBar progress;
    public JButton record;
    public JButton stopRecord;
    public boolean detener;
    
    public JButton iniciar;
    public JButton detenerAccion;
    
    public JTextField TextGiro ;
    public JTextField TextRecto ;
    public JTextField TextPwmIzq;
    public JTextField TextPwmDer;
    public static final String FORWARD = "Forward";
    public static final String BACK = "Back";
    public static final String RIGTH ="Rigth";
    public static final String LEFT = "Left";
    public static final String STOP = "Stop";
    public static final String RECORD = "record";
    public static final String STOPRECORD ="stop record";
    
    private Carro car;
    public static String message = "";
    public static String TEXTAREA = "";
    
    public void InitCarProperties()
    {
        car = Carro.ReadInstance();
        if(car == null)
        {
            car = new Carro(100,70,45,44);
        }
        
        this.TextRecto.setText(car.getDelayRecto() + "");
        this.TextGiro.setText(car.getDelayGiro() + "");
        this.TextPwmDer.setText(car.getPwmRigth() + "");
        this.TextPwmIzq.setText(car.getPwmLeft() + "");
    }
    
    public Gui() {
        // 
        //Buttons panel Area
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.setPreferredSize(new Dimension(280,100));
        
        JButton buttonForward = new JButton(FORWARD);
        
        JButton buttonRight = new JButton(RIGTH);
        JButton buttonLeft = new JButton(LEFT);
        
        detenerAccion = new JButton("Stop");
        detenerAccion.addActionListener((ActionEvent e) -> {
            AgenteCHAT.iniciar = false;
            iniciar.setEnabled(true);
            detenerAccion.setEnabled(false);
        });
        
        iniciar = new JButton("Start");
        iniciar.addActionListener((ActionEvent e) -> {
            AgenteCHAT.iniciar = true;
            iniciar.setEnabled(false);
            detenerAccion.setEnabled(true);
        });


        
        
        JComboBox comboBox = new javax.swing.JComboBox<>();
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Heroe", "Victima" }));
        comboBox.addActionListener((ActionEvent e) -> {
            if(iniciar.isEnabled())
            {
                String texto = (String)comboBox.getSelectedItem();
                AgenteCHAT.turno = texto.equals("Heroe");
            }
        });
        
        JPanel IniciarJuego = new JPanel();
        IniciarJuego.setLayout(new GridLayout(1,3));
        IniciarJuego.add(comboBox);
        detenerAccion.setEnabled(false);
        IniciarJuego.add(iniciar);
        IniciarJuego.add(detenerAccion);
        

        buttonForward.setPreferredSize(new Dimension(30,100));
        

        buttonForward.addActionListener(this);
        buttonRight.addActionListener(this);
        buttonLeft.addActionListener(this);

        buttons.add(IniciarJuego,BorderLayout.NORTH);
        
        buttons.add(buttonRight,BorderLayout.EAST);
        buttons.add(buttonLeft,BorderLayout.WEST);
        buttons.add(buttonForward,BorderLayout.CENTER);

        //Area de butones
        
        JButton ButtonGiro = new JButton("Giro");
        TextGiro = new JTextField();
        JButton ButtonRecto = new JButton("Recto");
        TextRecto = new JTextField();
        
        JButton Buttonpwmizq = new JButton("IZQ");
        TextPwmIzq = new JTextField();
        JButton Buttonpwmder = new JButton("DER");
        TextPwmDer = new JTextField();
        
        Buttonpwmizq.addActionListener(this);
        Buttonpwmder.addActionListener(this);
        
        ButtonGiro.addActionListener(this);
        ButtonRecto.addActionListener(this);
        
        GridLayout Grid = new GridLayout(2,3);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100,150));
        panel.setLayout(Grid);
        
        panel.add(ButtonGiro);
        panel.add(TextGiro);
        panel.add(ButtonRecto);
        panel.add(TextRecto);
        panel.add(Buttonpwmizq);
        panel.add(TextPwmIzq);
        panel.add(Buttonpwmder);
        panel.add(TextPwmDer);
        
        buttons.add(panel,BorderLayout.SOUTH);
        
        //Carpio Panel Area
        JPanel carpioPanel = new JPanel();
        //panel1.setLayout(new GridLayout(20,20));

        
        //Record audio area
        JPanel records = new JPanel();
        
        
        
        record = new JButton(RECORD);
        stopRecord = new JButton(STOPRECORD);
        progress = new JProgressBar();
        stopRecord.setEnabled(false);
        record.addActionListener(this);
        stopRecord.addActionListener(this);
        record.setPreferredSize(new Dimension(375,50));
        stopRecord.setPreferredSize(new Dimension(375,50));
        progress.setPreferredSize(new Dimension(525,50));

        records.setLayout(new FlowLayout());
        records.add(record);
        records.add(progress);
        records.add(stopRecord);

        //Chat Box Area
        JTextArea text;
        JScrollPane scroll = new JScrollPane();
        text = new JTextArea(); 
        text.setColumns(20);
        text.setRows(5);
        text.setText("Notificaciones");
        scroll.setViewportView(text);
        
        Thread cambiarTexto = new Thread( new Runnable(){
            @Override
            public void run() {
                try {
                    if(!AgenteCHAT.message.equals(""))
                    {
                        String oldText = AgenteCHAT.message + "\n" + text.getText();
                        text.setText(oldText);
                        AgenteCHAT.message = "";
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        cambiarTexto.start();
        // Setting initial configuration of aplication
        setLayout(new BorderLayout());
        this.add(carpioPanel,BorderLayout.CENTER);
        this.add(scroll,BorderLayout.WEST);
        this.add(records,BorderLayout.SOUTH);
        this.add(new JLabel("LABERINTO",JLabel.CENTER),BorderLayout.NORTH);
        this.add(buttons,BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(1300,750);
        this.setTitle("Proyecto IA");
        
        this.InitCarProperties();
        
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Carro.SaveInstance(car);
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Carro.SaveInstance(car);
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand(); 
        if(null != comando )
        switch (comando) {
            case Gui.RECORD:
                String ngrabacion = "RecordAudio.wav";
                Grabadora grabadora = new Grabadora(ngrabacion);
                // creates a new thread that waits for a specified
                // of time before stopping
                grabando = true;
                int tiempo = 2 ;//Integer.parseInt(jTextField3.getText());
                hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hilo iniciado");
                        while (grabando) {
                            
                            try {
                                Thread dg = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            System.out.println("Grabando " + tiempo + "s");
                                            Thread.sleep(tiempo * 1000);
                                            grabadora.finish();
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });                                    
                                Thread tbarra = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 0; i < 100; i++) {
                                            progress.setValue(i);
                                            try {
                                                Thread.sleep(tiempo * 10);
                                            } catch (InterruptedException ex) {
                                            }
                                        }
                                        
                                    }
                                });
                                
                                dg.start();
                                tbarra.start();
                                grabadora.start();
                                progress.setValue(0);
                                
                                Recognizer rec;
                                rec = new Recognizer(Recognizer.Languages.SPANISH_GUATEMALA, "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
                                GoogleResponse out = rec.getRecognizedDataForWave(ngrabacion);
                                System.out.println("Google reconoce: " + out.getResponse());
                                if(out.getResponse().equals("inicio") || out.getResponse().equals("parar"))
                                {
                                    System.out.println("Inicio o parar ? " + out.getResponse());
                                    //Runtime r = Runtime.getRuntime();
                                    //r.exec("aplay /home/ia/sonidos/poder.wav");
                                    //mensaje = out.getResponse();
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, "Error en el Gui", ex);
                            }

                        }
                        System.out.println("Hilo terminado");
                    }

                }); 
                hilo.start();
                record.setEnabled(false);
                stopRecord.setEnabled(true);
                break;
            case Gui.STOPRECORD:
                if (hilo.isAlive())
                {
                    grabando = false;
                }   
                record.setEnabled(true);
                stopRecord.setEnabled(false);
                break;
            case Gui.STOP:
                if(detener)
                {
                    //mensaje = "inicio";
                    detener = false;
                }
                else
                {
                    //mensaje = "parar";
                    detener = true;
                }   
                break;
            case "Giro":
                message = "Giro"+this.TextGiro.getText();
                car.setDelayGiro(Integer.parseInt(this.TextGiro.getText()));
                break;
            case "Recto":
                message = "Recto"+this.TextRecto.getText();
                car.setDelayRecto(Integer.parseInt(this.TextRecto.getText()));
                break;
            case "IZQ":
                message = "pwmizq"+this.TextPwmIzq.getText();
                car.setPwmLeft(Integer.parseInt(this.TextPwmIzq.getText()));
                break;
            case "DER":
                message = "pwmder"+this.TextPwmDer.getText();
                car.setPwmRigth(Integer.parseInt(this.TextPwmDer.getText()));
                break;
            default:
                message = comando;
                break;
        }
            
            
            
    }
}
