package ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ThumbnailLabel extends JLabel{
	private int ID;
	
	public ThumbnailLabel(int id, Image image) {
		ID = id;
		setIcon(new ImageIcon(image));
	}
	
	public int getID() {
		return ID;
	}
}
