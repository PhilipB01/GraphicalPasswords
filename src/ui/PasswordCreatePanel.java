package ui;

import io.ImageLoader;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import processing.UserCreate;
import processing.Miscellaneous.BackAction;
import processing.UserCreate.UserException;



	public class PasswordCreatePanel extends JPanel {
		
		private JLabel imageBox, userDisplay, clickCount;
		private JButton accept, undo, reset, gridButton, back;
		private JPanel bottomBar, topBar, revertBar;
		private JLayeredPane center;
		private UserCreate createProcess;
		private Grid grid;
		
		private final PasswordCreatePanel thisPanel = this;
		private static final String parent = "Register";
		private static final double gridSize = 20;
		
		public PasswordCreatePanel(final Container content, UserCreate process, Boolean gridDraw, String user, int selectedImg) {
			createProcess = process;
			process.setTolerance(gridSize/2);
			
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
			
			userDisplay = new JLabel(user, JLabel.CENTER);
			userDisplay.setFont(new Font("Serif", Font.ITALIC, 24));
			userDisplay.setForeground(Color.WHITE);
			userDisplay.setHorizontalAlignment(SwingConstants.CENTER);
			topBar.add(userDisplay, BorderLayout.CENTER);
	        add(topBar, BorderLayout.NORTH);
			
	        center = new JLayeredPane();
	        center.setOpaque(false);
			imageBox = new JLabel(); //Make new label where image will be on printed.
			String imagePath = "imgs/" + selectedImg + ".jpg";
			ImageLoader il = new ImageLoader(imagePath);
			BufferedImage imageBuffer = il.getImage();
			Image imageD = imageBuffer.getScaledInstance(content.getWidth(), (int) (content.getWidth()*0.625), Image.SCALE_SMOOTH);
			ImageIcon image = new ImageIcon(imageD);
			imageBox.setIcon(image);
			imageBox.setBounds( 0, 0,  
	                image.getIconWidth(),
	                image.getIconHeight());
			center.add(imageBox, new Integer(0));
			
			clickCount = new JLabel("0");
			clickCount.setForeground(new Color(180,180,180,255));
			grid = new Grid(gridSize, image.getIconWidth(), image.getIconHeight(), gridDraw, clickCount); 
			process.setPoints(grid.getPoints());
			
			center.add(grid, new Integer(1));
			add(center, BorderLayout.CENTER);
			
			bottomBar = new JPanel();
			bottomBar.setLayout(new BorderLayout());
			bottomBar.setOpaque(false);
			int height = content.getHeight() - image.getIconHeight() - topBar.getPreferredSize().height;
	        bottomBar.setPreferredSize(new Dimension(content.getWidth(), height));
			
			accept = new JButton("CREATE");
			accept.setBackground(new Color(5,108,80,255));
			accept.setForeground(new Color(220,220,220,255));
			accept.setFont(new Font("Serif", Font.BOLD, 32));
	        accept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent E) {
					try {
						createProcess.discretize();
						JOptionPane.showMessageDialog(content,
								"User has been created.", "Registration Succesful", JOptionPane.DEFAULT_OPTION);
						content.remove(thisPanel);
						CardLayout cardLayout = (CardLayout) content.getLayout();
						cardLayout.show(content, "Home");
					} catch (UserException e) {
						if (e.getReason().equals("short")) {
							JOptionPane.showMessageDialog(content,
									"Enter at least 5 click points; Points entered: " + grid.getNumberPoints(), e.getMessage(),
								    JOptionPane.ERROR_MESSAGE);
							//grid.clearPoints();
						}
						else if (e.getReason().equals("duplicate")) {
							JOptionPane.showMessageDialog(content,
									"Each click must be unique to ensure a secure password", e.getMessage(),
								    JOptionPane.ERROR_MESSAGE);
							//grid.clearPoints();
						}
					}
				}
			});
	        JPanel confirm = new JPanel();
	        confirm.setLayout(new FlowLayout(FlowLayout.CENTER));
	        confirm.setBackground(new Color(90, 145, 160, 180));
	        confirm.add(accept); 
	        bottomBar.add(confirm, BorderLayout.NORTH);
	        
	        revertBar = new JPanel();
	        revertBar.setOpaque(false);
			undo = new JButton("Undo");
			undo.setBackground(new Color(40,120,150, 255));
			undo.setForeground(new Color(220,220,220,255));
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
	        
	        gridButton = new JButton("Grid Off");
			//reset.setBackground(new Color(40,120,150, 255));
	        gridButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent E) {
					grid.toggleGrid();
					if (!grid.isGridEnabled()) {
						gridButton.setText("Grid On");
					} else
					gridButton.setText("Grid Off");
				}
			});
	        revertBar.add(gridButton);
	        revertBar.add(clickCount);
	        
	        bottomBar.add(revertBar, BorderLayout.CENTER);
	        add(bottomBar, BorderLayout.SOUTH);
		}
}

