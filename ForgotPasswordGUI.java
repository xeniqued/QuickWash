import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;



/**
 *
 * @author aja_f
 */

//

 public class ForgotPasswordGUI extends JFrame{

    private WelcomeScreen2 thisws; //previous screeen

    public ForgotPasswordGUI(WelcomeScreen2 ws){

        thisws = ws;
        ws.setVisible(false);

        initComponents();

        //Initially hide password visibilty icons

        this.newhide.setVisible(false);
        this.confhide.setVisible(false);


    }

     /* ---VARIABLES--- */

    /* PANELS */

    private JPanel headingpanel;        //Panel containing logo
    private JPanel inputpanel;          //Panel containing input fields
    private JPanel buttonpanel;         //Panel for buttons

    /* LABELS */
    private JLabel headingpnlLabel;     // Label for heading logo
    private JLabel usernamelab;         // Label for username
    private JLabel idnumlab;            //Label for 
    private JLabel newpwrdlab;
    private JLabel confpwrdlab;

    private JLabel newshow;         //Label for new password show icon
    private JLabel newhide;         //Labl for new password hide icon
    private JLabel confshow;        //Label for confirm password icon
    private JLabel confhide;        //Labl for confirm password icon

    /* TEXT & PASSWORD FIELDS */


    private JTextField username;        //Textfield for username
    private JTextField idnum;
    private JPasswordField newpwrd;
    private JPasswordField confpwrd;


    /* BUTTONS */

    private JButton savebutton;         //Button to save changes
    private JButton cancelbutton;
    
    // Used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);

    

    private void initComponents(){

        setTitle("Reset Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {        
            // Setting up program icon 
            ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/pics/appicon.png");
            Image iconimg = icon.getImage();
            setIconImage(iconimg);
            System.out.println("QuickWash icon has loaded.");
        } 
        catch (NullPointerException e) {
            System.out.println("QuickWash icon did not load.");
        }

        headingpnlLabel = new JLabel();

        usernamelab = new JLabel();
        username = new JTextField();
        
        idnumlab = new JLabel();
        idnum = new JTextField();

        newpwrdlab = new JLabel();
        newpwrd = new JPasswordField();
      
        confpwrdlab = new JLabel();
        confpwrd = new JPasswordField();
       
        savebutton = new JButton();
        cancelbutton = new JButton();

        newshow = new JLabel();
        newhide = new JLabel();
        confshow = new JLabel();
        confhide = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //not sure
        setPreferredSize(new Dimension(1024, 720));

        /*  Window Layout */

        setLayout(new BorderLayout());

        /*  Panel Setup */

        headingpanel = new JPanel(new BorderLayout());
        headingpanel.setBackground(mainWhite);
        headingpanel.setPreferredSize(new Dimension(700, 160));

        headingpnlLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingpnlLabel.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/quickwashlogo3.png").getImage().getScaledInstance(190, 150, Image.SCALE_SMOOTH))); 
        headingpanel.add(headingpnlLabel,BorderLayout.CENTER);




        usernamelab.setText("Username :");
        usernamelab.setForeground(mainWhite);
        usernamelab.setFont(new Font(usernamelab.getFont().getFontName(), Font.BOLD, 15));
        username.setBackground(mainWhite);
        username.setForeground(mainBlue);
        username.setFont(new Font(username.getFont().getFontName(), Font.BOLD, 11));

      

        idnumlab.setText("ID Number :");
        idnumlab.setForeground(mainWhite);
        idnumlab.setFont(new Font(idnumlab.getFont().getFontName(), Font.BOLD, 15));
        idnum.setBackground(mainWhite);
        idnum.setForeground(mainBlue);
        idnum.setFont(new Font(idnum.getFont().getFontName(), Font.BOLD, 11));
    

        newpwrdlab.setText("New Password :");
        newpwrdlab.setForeground(mainWhite);
        newpwrdlab.setFont(new Font(newpwrdlab.getFont().getFontName(), Font.BOLD, 15));
        newpwrd.setBackground(mainWhite);
        newpwrd.setForeground(mainBlue);
        newpwrd.setFont(new Font(newpwrd.getFont().getFontName(), Font.BOLD, 11));
    

        confpwrdlab.setText("Confirm Password:");
        confpwrdlab.setForeground(mainWhite);
        confpwrdlab.setFont(new Font(confpwrdlab.getFont().getFontName(), Font.BOLD, 15));
        confpwrd.setBackground(mainWhite);
        confpwrd.setForeground(mainBlue);
        confpwrd.setFont(new Font(confpwrd.getFont().getFontName(), Font.BOLD, 11));
     

        newshow.setIcon(new ImageIcon((new ImageIcon(System.getProperty("user.dir") + "/pics/icons8-eye-20.png").getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING))));
        newshow.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                newshowMousePressed(evt);
            }});

        newhide.setIcon(new ImageIcon((new ImageIcon(System.getProperty("user.dir") + "/pics/icons8-invisible-20.png").getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING))));
        newhide.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                newhideMousePressed(evt);
            }
        });

        confshow.setIcon(new ImageIcon((new ImageIcon(System.getProperty("user.dir") + "/pics/icons8-eye-20.png").getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING))));
        confshow.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                confshowMousePressed(evt);
            }
        });

        confhide.setIcon(new ImageIcon((new ImageIcon(System.getProperty("user.dir") + "/pics/icons8-invisible-20.png").getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING))));
        confhide.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                confhideMousePressed(evt);
            }
        });



        inputpanel = new JPanel();
        inputpanel.setBackground(mainBlue);
        GroupLayout inputpanelLayout = new GroupLayout(inputpanel);
        inputpanel.setLayout(inputpanelLayout);
        inputpanelLayout.setHorizontalGroup(
            inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputpanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confpwrdlab)
                    .addComponent(newpwrdlab)
                    .addComponent(idnumlab)
                    .addComponent(usernamelab)
                    .addComponent(username)
                    .addComponent(idnum)
                    .addComponent(newpwrd)
                    .addComponent(confpwrd, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputpanelLayout.createSequentialGroup()
                        .addComponent(newshow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newhide))
                    .addGroup(inputpanelLayout.createSequentialGroup()
                        .addComponent(confshow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confhide)))
                .addContainerGap(400, Short.MAX_VALUE))
        );
        inputpanelLayout.setVerticalGroup(
            inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputpanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(usernamelab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(idnumlab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(newpwrdlab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputpanelLayout.createSequentialGroup()
                        .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newpwrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newshow))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(confpwrdlab))
                    .addComponent(newhide))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confhide)
                    .addGroup(inputpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(confpwrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(confshow)))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        
        buttonpanel = new JPanel();
        buttonpanel.setBackground(mainWhite);

        savebutton = new JButton("Save");
        savebutton.setBackground(mainBlue);
        savebutton.setForeground(mainWhite);
        savebutton.setText("Save");
        savebutton.addActionListener(new savebuttonActionListener());

        cancelbutton = new JButton("Cancel");
        cancelbutton.setBackground(mainBlue);
        cancelbutton.setForeground(mainWhite);
        cancelbutton.setText("Cancel");
        cancelbutton.addActionListener(new cancelbuttonListener());
      
        buttonpanel.add(savebutton);
        buttonpanel.add(cancelbutton);
        //addbuttons to panl

        add(headingpanel,BorderLayout.NORTH);
        add(inputpanel,BorderLayout.CENTER);
        add(buttonpanel,BorderLayout.SOUTH);


        pack();
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);




    
    }


    /* LISTENERS */

    //
    private void newshowMousePressed(MouseEvent evt) {                                     
       
        newhide.setVisible(true);
        newshow.setVisible(false);
        newpwrd.setEchoChar((char)0);
        
    }                                    

    private void newhideMousePressed(MouseEvent evt) {                                     
        newshow.setVisible(true);
        newhide.setVisible(false);
        newpwrd.setEchoChar('*');
        
    }                                    

    private void confshowMousePressed(MouseEvent evt) {                                      
         confhide.setVisible(true);
        confshow.setVisible(false);
        confpwrd.setEchoChar((char)0);
    }                                     

    private void confhideMousePressed(MouseEvent evt) {                                     
        confshow.setVisible(true);
        confhide.setVisible(false);
        confpwrd.setEchoChar('*');
        
        
    }   


    //Save button functionality

    private class savebuttonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){

            String userName = username.getText();
            String iD = idnum.getText();
            
           
            char [] newpassword = newpwrd.getPassword() ;
            char [] confirmedpassword = confpwrd.getPassword();
        
            String np = new String(newpassword);
            String cp = new String(confirmedpassword);

    
            if ((userName.isEmpty() || iD.isEmpty() || np.isEmpty()|| cp.isEmpty())){
                JOptionPane.showMessageDialog(null, "Please fill out all fields!",
                    "Error", JOptionPane.ERROR_MESSAGE);

                return;

            }
            int id;

            try{
                id = Integer.parseInt(idnum.getText());
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please enter a valid ID number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }


            
            if(!np.equals(cp)){
                JOptionPane.showMessageDialog(null, "Passwords do not match.Try Again!",
                "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            
            Database db = new Database();
            Database.updatePassword(id,np);

            JOptionPane.showMessageDialog(null, "Password reset successfully!",
                    "Password Reset Success", JOptionPane.INFORMATION_MESSAGE);

                setVisible(false);
                thisws.setVisible(true);
            }
        
        }

        

    
    private class cancelbuttonListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            //return to login screen
            setVisible(false);
            thisws.setVisible(true);
        }
    }













}
