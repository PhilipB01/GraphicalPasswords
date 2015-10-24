package ui;

import io.ImageLoader;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Primary UI class
 */
public class TopFrame extends JFrame{
	private JPanel welcome, home;
	PasswordEntryPanel password;
	private CardLayout layout;
	private Container content;
	
	public TopFrame() {
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Logon");
            // sets opening size
            setResizable(false);

            layout = new CardLayout();
            content = getContentPane();
            content.setPreferredSize(new Dimension(800,650));
            content.setLayout(layout);
            content.setBackground(new Color(25,83,104, 255));

            welcome = new WelcomePanel(content);
            content.add(welcome, "Welcome");

            home = new LogonPanel(content);
            content.add(home, "Home");    

                    pack(); // smallest size necessary
                setVisible(true);
            //This will center the JFrame in the middle of the screen
            setLocationRelativeTo(null);
	}
}
