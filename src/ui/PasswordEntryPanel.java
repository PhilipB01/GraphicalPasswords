package ui;

import io.ImageLoader;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import processing.LoginInfo;
import processing.PasswordLogin;
import processing.Miscellaneous.BackAction;
import processing.PasswordLogin.LoginException;



public class PasswordEntryPanel extends JPanel {
	
	private JLabel imageBox, userDisplay, clickCount;
	private JButton accept, undo, reset, back;
	private JPanel bottomBar, topBar, revertBar;
	private JLayeredPane center;
	private PasswordLogin loginProcess;
	private LoginInfo userDetails;
	private Grid grid;
	
	private final JPanel thisPanel;
	private static final String parent = "Home";
	private final static double gridSize = 20;
	
	public PasswordEntryPanel(final Container content, PasswordLogin process, Boolean gridDraw) {
		thisPanel = this;
		loginProcess = process;
		loginProcess.setTolerance(gridSize/2);
		userDetails = loginProcess.getDetails();
		
		setOpaque(false);
		setLayout(new BorderLayout());
		
		topBar = new JPanel();
		topBar.setOpaque(false);
		topBar.setLayout(new BorderLayout());
		topBar.setPreferredSize(new Dimension(content.getWidth(), 50));
		
		BufferedImage backIcon = null;
		try {
			backIcon = ImageIO.read(new File("imgs/BackIcon.png"));
			back = new JButton(new ImageIcon(backIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			back = new JButton("Back");
		}
		back.setBorder(BorderFactory.createEmptyBorder());
		back.setContentAreaFilled(false);
		BackAction backListener = new BackAction(content, parent);
		back.addActionListener(backListener);
		topBar.add(back, BorderLayout.WEST);
		
		userDisplay = new JLabel(userDetails.getUser(), JLabel.CENTER);
		userDisplay.setFont(new Font("Serif", Font.ITALIC, 24));
		userDisplay.setForeground(Color.WHITE);
		userDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		userDisplay.setVerticalAlignment(SwingConstants.CENTER);
		topBar.add(userDisplay, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);
		
        center = new JLayeredPane();
        center.setOpaque(false);
		imageBox = new JLabel(); //Make new label where image will be on printed.
		String imagePath = "imgs/" + userDetails.getImageID() + ".jpg";
		ImageLoader il = new ImageLoader(imagePath);
		BufferedImage imageBuffer = il.getImage();
		Image imageD = imageBuffer.getScaledInstance(content.getWidth(), (int) (content.getWidth()*0.625), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(imageD);
		imageBox.setBounds( 0, 0,  
                image.getIconWidth(),
                image.getIconHeight()); 
		imageBox.setIcon(image);
		center.add(imageBox, new Integer(0));
		
		clickCount = new JLabel("0");
		clickCount.setForeground(new Color(220,220,220,255));
		grid = new Grid(gridSize, image.getIconWidth(), image.getIconHeight(), gridDraw, clickCount);
		process.setPoints(grid.getPoints());
		
		center.add(grid, new Integer(1));
		add(center, BorderLayout.CENTER);
		
		
		bottomBar = new JPanel();
		bottomBar.setLayout(new BorderLayout());
		bottomBar.setOpaque(false);
		int height = content.getHeight() - image.getIconHeight() - topBar.getPreferredSize().height;
        bottomBar.setPreferredSize(new Dimension(content.getWidth(), height));
        
		accept = new JButton("ENTER");
		accept.setBackground(new Color(5,108,80,255));
		accept.setForeground(new Color(220,220,220,255));
		accept.setFont(new Font("Serif", Font.BOLD, 32));
        accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				try {
					if (loginProcess.check()) {
						// Test output
						//System.out.println("YES! Login SUCCESSFUL!");
						JOptionPane.showMessageDialog(content,
								"Yes! You have logged into your account!", "Authentication Succesful", JOptionPane.DEFAULT_OPTION);
						content.remove(thisPanel);
						CardLayout cardLayout = (CardLayout) content.getLayout();
						cardLayout.show(content, "Home");
					}
					else {
						// Test output
						//System.out.println("Login Failed. Either username or password is incorrect.");
						JOptionPane.showMessageDialog(content,
								"Either the username or password you entered is incorrect.", "Authentication Failed",
							    JOptionPane.ERROR_MESSAGE);
						grid.clearPoints();
					}
				} catch (LoginException e) {
					JOptionPane.showMessageDialog(content,
							"Please try again: A valid password consists of a minimum of 5 click points.", e.getMessage(),
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        JPanel confirm = new JPanel();
        confirm.setLayout(new FlowLayout(FlowLayout.CENTER));
        confirm.setBackground(new Color(90, 145, 160, 180));
        confirm.add(accept); 
        bottomBar.add(confirm, BorderLayout.NORTH);
        
        revertBar = new JPanel();
        revertBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        revertBar.setOpaque(false);
		undo = new JButton("Undo");
		undo.setBackground(new Color(40,120,150, 255));
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.removeLastPoint();
			}
		});
        revertBar.add(undo);
        
        reset = new JButton("Reset");
		//reset.setBackground(new Color(40,120,150, 255));
        reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.clearPoints();
			}
		});
        revertBar.add(reset);
        revertBar.add(clickCount);
        
        bottomBar.add(revertBar, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
	}
}
