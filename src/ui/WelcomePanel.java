package ui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* UI class to handle initial welcome info screen displayed 
 * on program start-up
 */
public class WelcomePanel extends JPanel{
	private JButton ok;
	public WelcomePanel(final Container content){
		
        setOpaque(true);
        setBackground(new Color(70,125,90, 255));
        setLayout(new BorderLayout());
        Dimension expectedDimension = new Dimension(600, 400);

        setPreferredSize(expectedDimension);
        setMaximumSize(expectedDimension);
        setMinimumSize(expectedDimension);
        
        StringBuilder sb = new StringBuilder(64);
        sb.append("<html>Welcome User!<br><br>").
                        append("  This application is a prototype of a Graphical Password Authentication System I have implemented based on PassPoints. ").
                        append("  It allows you to both create a graphical password attached to a user name and thereafter use this data to login.<br><br><br>").
        				append("  To help facilitate testing of the program, please follow the following instructions:<br><br>").
        				append("  1. Proceed to the following screen and click on \"Create new Logon\" to begin the password creation process. Select a unique alphanumeric username and one of the available images. Then proceed to the password create screen by clicking on the \"Continue\" button. <br><br>").
        				append("  2. On the following screen, you can create your password consisting of click points you enter on the image you chose. The password should be at least 5 points in order to be considered reasonably secure. Utilise the features of the image chosen to help you remember what locations you clicked in the correct order. The back button can be used at any time to return to the previous screen. Undo and reset allow you to undo the last click point or all click points you've entered respectively.<br><br>").
        				append("  3. Once you feel confident that you can remember the points you've entered in the correct order along with the user name chosen click \"Create\" to finish the password creation process  and return to the home screen. The toggleable grid offers an indication of how much error is allowed in distance from original click when logging in and is approximately half the size of the grid. <br><br>").
        				append("  4. Now you should be able to type in your user name in the text field on the home screen to attempt to login. If you entered your user name correctly you should now have the corresponding image you had previously selected displayed before you. If that's not the case ensure your user name is correct. <br><br>").
        				append("  5. Enter your password again in order to login. This runs a check against the stored, securely hashed version of the password you made to see if they match. However, you only need to be accurate by a certain tolerance amount r pixels from the point you originally entered on creation.<br><br>").
        				append("  6. Should the login attempt succeed you will receive the appropriate message and automatically return to the home screen. Congratulations! You've succesfully remembered your password and reentered it correctly. Otherwise you may try to reenter your password again or click back to return to the home screen and create a new one again in case you've forgotten it.<br><br>").
        				append("  7. Ensure you feel comfortable remembering your password for a couple days before filling out the attached questionnaire form. After this please return the questionnaire form along with the \"pwd.txt\" file containing all the user name/password pairs you've created. Let me know which passwords you can still remember so any usernames or passwords you have forgotten can be removed.<br><br></html>");
        JLabel welcomeText = new JLabel(sb.toString());
        welcomeText.setForeground(new Color(230,230,230, 255));
        add(welcomeText, BorderLayout.NORTH);
        
        
        ok = new JButton("OK"){
        	   public Dimension getPreferredSize() {
        		      return new Dimension(50, 40);
        		   };
        		};
        ok.setToolTipText("Press ok to close");
        ok.setBackground(new Color(40,120,150, 255));
        ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				CardLayout cardLayout = (CardLayout) content.getLayout();
				cardLayout.next(content);
			}
		});
        add(ok, BorderLayout.SOUTH);
        ok.requestFocusInWindow();
        
	}
}
