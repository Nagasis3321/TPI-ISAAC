/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Controlador.Controlador;
import static java.awt.FlowLayout.CENTER;
import static java.awt.PageAttributes.MediaType.C;
import java.util.ArrayList;
/**
 *
 * @author Terleski Isaac
 */
public class CreadorObra extends JFrame{ 
   /* JPanel panelSuperior, panelInferior, panelIngreso, panelLogo, panelTituto;
    JLabel denominacion,financiacion,fechadeInicio,plazo,creadorObras,empresa,nrObra,logo,imagen,titVista;
    JButton crear,volver;
    
    JFrame  formulario;*/
    Controlador controlador;
    private javax.swing.JPanel General;
    private javax.swing.JPanel Panelinferior;
    private java.awt.Button buttonCrear;
    private java.awt.Button buttonVolver;
    private java.awt.Choice choiceFinanciacion;
    private javax.swing.JLabel imagen;
    private com.toedter.calendar.JDateChooser jDateChooserInicio;
    private com.toedter.calendar.JDateChooser jDateChooserPlazo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private java.awt.Label labelDenominacion;
    private java.awt.Label labelFechadeInicio;
    private java.awt.Label labelFinanciacion;
    private javax.swing.JLabel labelNumObra;
    private java.awt.Label labelPlazo;
    private javax.swing.JPanel panelIzquierdo;
    private javax.swing.JPanel panelLogo;
    private javax.swing.JPanel panelNumObra;
    private javax.swing.JPanel panelSuperior;
    private java.awt.TextField textFieldDenominacion;
    private java.awt.Label tigüi;
    private java.awt.Label titVista;
    private javax.swing.JButton buttonNewFinanza;
    public CreadorObra(Controlador control){
    /* 
     this.setLayout(new FlowLayout());
     
     formulario= new JFrame();
     panelIngreso = new JPanel();
     panelSuperior = new JPanel();
     panelInferior = new JPanel();
     this.panelLogo= new JPanel();
     denominacion = new JLabel();
     financiacion = new JLabel();
     fechadeInicio = new JLabel();
     plazo = new JLabel();
     this.logo= new JLabel();
     imagen= new JLabel();
     creadorObras = new JLabel();
     titVista = new JLabel();
     empresa = new JLabel();
     nrObra = new JLabel();
     crear = new JButton();
     volver = new JButton();
     
     */

     /*
     //Label Logo 
     this.logo.setText("Tigüi");
     logo.setFont(new java.awt.Font("Roboto", 3, 48));
     logo.setAlignmentX(CENTER);
     logo.setForeground(Color.black);
     logo.setBounds(150, 75, 100, 50);
     
     //Label Imagen
      imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo.png")));
      imagen.setBounds(50, 25, 100, 100);
      
   
      
        
    //Panel Logo
     panelLogo.setBounds(0, 0, 400, 100);
     panelLogo.setBackground(new java.awt.Color(69,44,44));
     panelLogo.add(imagen);
     panelLogo.add(logo);
     
       //Label titVista
     titVista.setText("Creador de OBRAS");
      titVista.setFont(new java.awt.Font("Roboto", 3, 50));
      titVista.setAlignmentX(CENTER);
      titVista.setForeground(Color.black);
      titVista.setBounds(450,50, 350,100);
     
     //Panel Superior
     panelSuperior.setBounds(0, 0,1280,200);
     panelSuperior.setBackground(new java.awt.Color(69,44,44));
     //panelSuperior.add(panelLogo);
     panelLogo.add(titVista);
     panelSuperior.setVisible(true);
         
     
    //Panel Inferior
     panelInferior.setBounds(0,200,1280,700);
     panelInferior.setVisible(true);
     panelInferior.setBackground(Color.white);
      
     
    
     this.formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.formulario.setVisible(true);  
     formulario.add(panelInferior);
     formulario.setBackground(Color.white);
      formulario.add(panelSuperior);
     formulario.setBounds(0, 0, 1280,720);*/
     
     
     
        this.controlador=control;

        General = new javax.swing.JPanel();
        Panelinferior = new javax.swing.JPanel();
        panelIzquierdo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelDenominacion = new java.awt.Label();
        textFieldDenominacion = new java.awt.TextField();
        jPanel3 = new javax.swing.JPanel();
        labelFinanciacion = new java.awt.Label();
        choiceFinanciacion = new java.awt.Choice();
        buttonNewFinanza = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        labelFechadeInicio = new java.awt.Label();
        jDateChooserInicio = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        labelPlazo = new java.awt.Label();
        jDateChooserPlazo = new com.toedter.calendar.JDateChooser();
        buttonVolver = new java.awt.Button();
        buttonCrear = new java.awt.Button();
        panelNumObra = new javax.swing.JPanel();
        labelNumObra = new javax.swing.JLabel();
        panelSuperior = new javax.swing.JPanel();
        panelLogo = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        tigüi = new java.awt.Label();
        titVista = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        General.setMinimumSize(new java.awt.Dimension(1280, 720));
        General.setName(""); // NOI18N
        General.setRequestFocusEnabled(false);
        General.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panelinferior.setBackground(new java.awt.Color(255, 255, 255));

        panelIzquierdo.setBackground(new java.awt.Color(255, 255, 255));
        panelIzquierdo.setLayout(new java.awt.GridLayout(4, 1, 100, 40));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        labelDenominacion.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        labelDenominacion.setText("Denominacion:");

        textFieldDenominacion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textFieldDenominacion.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        textFieldDenominacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDenominacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelDenominacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textFieldDenominacion, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(textFieldDenominacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(labelDenominacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelIzquierdo.add(jPanel4);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        labelFinanciacion.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        labelFinanciacion.setText("Financiacion:");

        buttonNewFinanza.setBackground(new java.awt.Color(69, 44, 44));
        buttonNewFinanza.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        buttonNewFinanza.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonNewFinanza.setLabel("+");
        buttonNewFinanza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewFinanzaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelFinanciacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choiceFinanciacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(buttonNewFinanza, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(338, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelFinanciacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonNewFinanza, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choiceFinanciacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        panelIzquierdo.add(jPanel3);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(872, 90));

        labelFechadeInicio.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        labelFechadeInicio.setText("Fecha de Inicio:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelFechadeInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelFechadeInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        panelIzquierdo.add(jPanel2);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(872, 90));

        labelPlazo.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        labelPlazo.setText("Plazo: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(labelPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooserPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(476, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelPlazo, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jDateChooserPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelIzquierdo.add(jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("");

        buttonVolver.setLabel("Volver");
        buttonVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonVolverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonVolverMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonVolverMouseExited(evt);
            }
        });
        buttonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVolverActionPerformed(evt);
            }
        });

        buttonCrear.setLabel("Crear");
        buttonCrear.setName(""); // NOI18N
        buttonCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCrearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonCrearMouseExited(evt);
            }
        });
        buttonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCrearActionPerformed(evt);
            }
        });

        panelNumObra.setBackground(new java.awt.Color(69, 44, 44));

        labelNumObra.setBackground(new java.awt.Color(69, 44, 44));
        labelNumObra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelNumObraLayout = new javax.swing.GroupLayout(panelNumObra);
        panelNumObra.setLayout(panelNumObraLayout);
        panelNumObraLayout.setHorizontalGroup(
            panelNumObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumObraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNumObra, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNumObraLayout.setVerticalGroup(
            panelNumObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumObraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNumObra, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelinferiorLayout = new javax.swing.GroupLayout(Panelinferior);
        Panelinferior.setLayout(PanelinferiorLayout);
        PanelinferiorLayout.setHorizontalGroup(
            PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelinferiorLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIzquierdo, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelinferiorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelNumObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))
                    .addGroup(PanelinferiorLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(buttonCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))))
        );
        PanelinferiorLayout.setVerticalGroup(
            PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelinferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIzquierdo, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelNumObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(PanelinferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        General.add(Panelinferior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1280, 520));

        panelSuperior.setBackground(new java.awt.Color(69, 44, 44));

        panelLogo.setBackground(new java.awt.Color(69, 44, 44));
        panelLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo.png"))); // NOI18N

        tigüi.setFont(new java.awt.Font("Roboto", 3, 48)); // NOI18N
        tigüi.setForeground(new java.awt.Color(255, 255, 255));
        tigüi.setText("Tigüi");

        javax.swing.GroupLayout panelLogoLayout = new javax.swing.GroupLayout(panelLogo);
        panelLogo.setLayout(panelLogoLayout);
        panelLogoLayout.setHorizontalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(imagen)
                .addGap(30, 30, 30)
                .addComponent(tigüi, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panelLogoLayout.setVerticalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addGroup(panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLogoLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(imagen))
                    .addGroup(panelLogoLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(tigüi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        imagen.getAccessibleContext().setAccessibleName("");

        titVista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        titVista.setFont(new java.awt.Font("Roboto Black", 2, 75)); // NOI18N
        titVista.setText("Creador de Obras");

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addComponent(titVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(titVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        General.add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 150));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(General, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(General, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        
        
        
        
        
        
        //No modificar
        this.buttonNewFinanza.setForeground(Color.black);
        titVista.setForeground(Color.black);
        this.setExtendedState(6);
        this.setLayout(new FlowLayout());
        this.ListadoFinanzas(controlador);
        this.setVisible(true);
    }
    private void textFieldDenominacionActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
    }                                                     

    private void buttonVolverActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            
                                  
    private void buttonNewFinanzaActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void buttonCrearMouseEntered(java.awt.event.MouseEvent evt) {                                         
          buttonCrear.setBackground(new Color(69,44,44));
          buttonCrear.setForeground(Color.black);
    }                                        

    private void buttonCrearMouseExited(java.awt.event.MouseEvent evt) {                                        
       buttonCrear.setBackground(new Color(240,240,240));
    }                                       

    private void buttonCrearMouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void buttonVolverMouseExited(java.awt.event.MouseEvent evt) {                                         
          buttonVolver.setBackground(new Color(240,240,240));
    }                                        

    private void buttonVolverMouseEntered(java.awt.event.MouseEvent evt) {                                          
           buttonVolver.setBackground(new Color(69,44,44));
           buttonVolver.setForeground(Color.black);
    }                                         

    private void buttonVolverMouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }    
    private void buttonCrearActionPerformed(java.awt.event.ActionEvent evt) {                                            
            String d,i,f,p;
           d =this.textFieldDenominacion.getText();
           i= this.jDateChooserInicio.getDateFormatString();
           f= this.choiceFinanciacion.getSelectedItem();
           p=this.jDateChooserPlazo.getDateFormatString();
           controlador.crearObra(d,i,f,p);
    }                                               
        
    private void ListadoFinanzas(Controlador controlador){
         ArrayList<String> finanzas=controlador.Financiaciones();
        if(finanzas!=null){
           for (String cadena: finanzas){
                this.choiceFinanciacion.add(cadena);
            }  
        }
        
      }    
    }

  
    
    
    
    
    
    
    
    
    
    
    
    

