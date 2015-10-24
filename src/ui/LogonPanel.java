package ui;
import io.ImageLoader;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import processing.Miscellaneous;
import processing.PasswordLogin;


/**
 * UI class containing user login and login creation elements
 */
public class LogonPanel extends JPanel{
	private Container content;
	private JPanel login;
	private PasswordEntryPanel loginPane;
	private NewUserPanel createPane;
	private JTextField user;
	private JButton enter, create, info;
	private JLabel padding;
	
	public LogonPanel(final Container content) {
		this.content = content;
		setOpaque(false);
		setLayout(new GridBagLayout());
		Dimension expectedDimension = new Dimension(800, 650);

        setPreferredSize(expectedDimension);
        setMaximumSize(expectedDimension);
        setMinimumSize(expectedDimension);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		login = new JPanel();
		login.setOpaque(false);
		
		user = new JTextField("username");
		user.setColumns(20);
		user.setBackground(new Color(180,200,220,255));
		user.setForeground(new Color(80,80,80,255));
		user.setFont(new Font("Serif", Font.PLAIN, 18));
		user.setToolTipText("Enter a valid username to login");
		user.addMouseListener(new Miscellaneous.ClearField());
		user.addActionListener(new UsernameEnter());
		login.add(user);
		
		enter = new JButton("Enter");
		enter.setBackground(new Color(27,46,52,255));
		enter.setForeground(new Color(180,180,180,255));
		enter.addActionListener(new UsernameEnter());
		login.add(enter);
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(100,0,0,0);
		c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		add(login, c);
		
		final Image[] thumbnailImages = loadThumbnailImages(10);
		create = new JButton("Create new Logon");
		create.setBackground(new Color(5,108,80,255));
		create.setForeground(new Color(180,180,180,255));
		create.setToolTipText("First create a new user name and password here!");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				CardLayout cardLayout = (CardLayout) content.getLayout();
				if (createPane != null) {
					content.remove(createPane);
				}
				createPane = new NewUserPanel(content, thumbnailImages);
		        content.add(createPane, "Register");
				content.revalidate();
				content.repaint();
				cardLayout.show(content, "Register");
			}
		});
		c.anchor = GridBagConstraints.CENTER;
		c.ipady = 20;
		c.ipadx = 80;
		c.weighty = 0.5;
		//c.weightx = 0.5;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 1;
		add(create, c);
		
		
		padding = new JLabel();
		c.weighty = 0;
		c.weightx = 0.7;
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;
		c.gridy = 4;
		add(padding, c);
		
		info = new JButton("Show Info Pane again  >>");
		info.setBackground(new Color(5,108,80,255));
		info.setForeground(new Color(180,180,180,255));
		info.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	/*
		    	info.setText("Hide Info Pane   >>");
		    	//info.setText("Show Info Pane   >>");*/
		    	CardLayout cardLayout = (CardLayout) content.getLayout();
		    	cardLayout.previous(content);
		    }
		});
		c.ipadx = 0;
		c.weighty = 0;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.LAST_LINE_END; //bottom of space
		//c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 2;
		c.gridy = 4;
		add(info, c);
	}
	
	private Image[] loadThumbnailImages(int numberOfImages) {
		Image[] images = new Image[numberOfImages];
		for (int i = 0; i<numberOfImages; i++) {
			String filepath = "imgs/" + i + ".jpg";
			ImageLoader il = new ImageLoader(filepath);
			BufferedImage imageBuffer = il.getImage();
			images[i] = imageBuffer.getScaledInstance(100, (int) (100*0.625), Image.SCALE_SMOOTH);
		}
		return images;
	}
	
	private class UsernameEnter implements ActionListener {
		public void actionPerformed(ActionEvent E) {
			CardLayout cardLayout = (CardLayout) content.getLayout();
			if (loginPane != null) {
				content.remove(loginPane);
			}
			PasswordLogin loginProcess = null;
			try {
				loginProcess = new PasswordLogin(user.getText());
				loginPane = new PasswordEntryPanel(content, loginProcess, false);
				content.add(loginPane, "Login");
				content.revalidate();
				content.repaint();
				cardLayout.show(content, "Login");
			} catch (PasswordLogin.LoginException e) {
				user.setText("");
				JOptionPane.showMessageDialog(content,
						"Enter a valid user name consisting of letters and numbers only. No special characters or spaces allowed.", e.getMessage(),
					    JOptionPane.ERROR_MESSAGE);
				user.requestFocus();
			}
		}
	}
}
