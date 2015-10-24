package ui;

import io.ImageLoader;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import processing.Miscellaneous;
import processing.UserCreate;
import processing.Miscellaneous.BackAction;


public class NewUserPanel extends JPanel{
	private Container content;
	private JLabel userLabel, previewImg, previewText, imageText;
	private JTextField userField;
	private JButton confirm, back;
	private JPanel imageGrid, preview, center, top, confirmPanel;
	private ThumbnailLabel img;
	private PasswordCreatePanel passwordPane;
	private UserCreate data;
	private int selectedImg = 0;
	
	private static final String parent = "Home";
	private static final Color topColour = new Color(90, 145, 160, 180);
	private static final Color textColour = new Color(220, 220, 220, 255);
	
	public NewUserPanel(final Container content, Image[] thumbnailImages) {
		this.content = content;
		setOpaque(false);
		setLayout(new BorderLayout());
		
		top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBackground(topColour);
		
		BufferedImage backIcon = null;
		try {
			backIcon = ImageIO.read(new File("imgs/BackIcon.png"));
			back = new JButton(new ImageIcon(backIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			back = new JButton("Back");
		}
		back.setBorder(BorderFactory.createEmptyBorder());
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		BackAction backListener = new BackAction(content, parent);
		back.addActionListener(backListener);
		top.add(back, BorderLayout.WEST);
		
		JLabel user = new JLabel();
		user.setLayout(new FlowLayout());
				
		userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
		userLabel.setForeground(new Color(190,190,190,255));
		user.add(userLabel);
		
		userField = new JTextField("username");
		userField.setColumns(20);
		userField.setForeground(new Color(120,100,100,255));
		userField.setFont(new Font("Serif", Font.PLAIN, 18));
		userField.setToolTipText("Enter a new username here");
		userField.addMouseListener(new Miscellaneous.ClearField());
		userField.addActionListener(new NewUsernameEnter());
		user.add(userField);
		user.setHorizontalAlignment(JLabel.CENTER);
		user.setVerticalAlignment(JLabel.CENTER);
		top.add(user, BorderLayout.CENTER);
		add(top, BorderLayout.NORTH);
		
		confirmPanel = new JPanel();
		confirmPanel.setBackground(topColour);
		confirmPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		confirm = new JButton("Continue to create password screen..."){
     	   public Dimension getPreferredSize() {
 		      return new Dimension(300, 40);
 		   };
 		};
 		confirm.setToolTipText("Proceed to password creation screen");
 		confirm.setBackground(new Color(10,120,60, 255));
 		confirm.setForeground(new Color(220,220,220, 255));
		confirm.addActionListener(new NewUsernameEnter());
		confirmPanel.add(confirm);
		add(confirmPanel, BorderLayout.SOUTH);
		
		center = new JPanel();
		center.setOpaque(false);
		
		preview = new JPanel();
		preview.setOpaque(false);
		preview.setLayout(new BoxLayout(preview, BoxLayout.PAGE_AXIS));
		preview.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent E) {
				JOptionPane.showMessageDialog(content,
						"Click on continue button below to proceed to new password entering screen once you have entered a user name and selected your preferred password image.", "Don't click here! This is only a preview image.",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		previewText = new JLabel("Selected Password Image");
		previewText.setForeground(textColour);
		preview.add(previewText);
		
		preview.setBackground(Color.GRAY);
		previewImg = new JLabel();
		showPreview();
		center.add(preview);
		
		imageText = new JLabel("Select a thumbnail to preview background image: ");
		imageText.setForeground(textColour);
		center.add(imageText);
		
		imageGrid = new JPanel();
		imageGrid.setOpaque(false);
		imageGrid.setLayout(new GridLayout(0, 5, 5, 5));
		showImgGrid(thumbnailImages);
		center.add(imageGrid);
		
		add(center, BorderLayout.CENTER);
	}
	
	void showPreview() {
		//imageBox.setSize(new Dimension(content.WIDTH, content.HEIGHT-100));
		String filepath = "imgs/" + selectedImg + ".jpg";
		ImageLoader il = new ImageLoader(filepath);
		BufferedImage imageBuffer = il.getImage();
		Image imageD = imageBuffer.getScaledInstance(600, (int) (600 * 0.625), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(imageD);

		previewImg.setIcon(image);
		preview.add(previewImg);
	}
	
	void showImgGrid(Image[] images) {
		for (int i = 0; i<images.length; i++) {
			//imageBox.setSize(new Dimension(content.WIDTH, content.HEIGHT-100));
			img = new ThumbnailLabel(i, images[i]);
			img.addMouseListener(new ImageSelect());
			imageGrid.add(img);
		}
	}
	
	private class ImageSelect extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
			previewSelection((ThumbnailLabel)e.getSource());
			}
		}
	}

	public void previewSelection(ThumbnailLabel selected) {
		selectedImg = selected.getID();
		showPreview();
	}
	
	private class NewUsernameEnter implements ActionListener {
		public void actionPerformed(ActionEvent E) {
			CardLayout cardLayout = (CardLayout) content.getLayout();
			if (passwordPane != null) {
				content.remove(passwordPane);
			}
			UserCreate data = null;
			String user = userField.getText();
			if (selectedImg == 9) {
				selectedImg = randomImg();
			}
			try {
				data = new UserCreate(user, selectedImg);
				passwordPane = new PasswordCreatePanel(content, data, true, user, selectedImg);
				content.add(passwordPane, "Create");
				content.revalidate();
				content.repaint();
				cardLayout.show(content, "Create");
			} catch (processing.UserCreate.UserException e) {
				userField.setText("");
				if (e.getReason().equals("uniq")) {
					JOptionPane.showMessageDialog(content,
							"Please enter a UNIQUE user name before proceeding.", e.getMessage(),
						    JOptionPane.ERROR_MESSAGE);
					userField.requestFocus();
				}
				else {
					JOptionPane.showMessageDialog(content,
							"Enter a valid user name consisting of letters and numbers only. No special characters or spaces allowed.", e.getMessage(),
						    JOptionPane.ERROR_MESSAGE);
					userField.requestFocus();
				}
			}
		}

		private int randomImg() {
			Random rand = new Random();
			return rand.nextInt((9 - 0) + 1) + 0;
		}
	}
}
