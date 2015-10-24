package processing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class Miscellaneous {

	// clears textfield
	public static class ClearField extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JTextField tf = (JTextField) e.getSource();
			String temp = tf.getText();
			if (temp.equals("username")) tf.setText(""); {
				tf.setForeground(new Color(0,0,0,255));
			}
	    }
	}
	
	public static class BackAction implements ActionListener {
		private Container content;
		private String parent;
		public BackAction(Container content, String parent) {
			this.content = content;
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) content.getLayout();
			cardLayout.show(content, parent);
		}
	}
}