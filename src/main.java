/* 
 * Class containing main method to start program
 */
import javax.swing.SwingUtilities;

import ui.TopFrame;



public class main {
	
	/**
     * 	Main method used to initially execute browser and create GUI
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TopFrame();
            }
        });
   }
}