package processing;

import io.FileIO;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/* Handles and defines password entity
 * Hashing function and comparisons
 * Works in tangent with Login class and certain UI elements
 */
public class PasswordLogin {
	private LoginInfo details;
	private List<Double> offsets;
	private String username;
	private List<Point2D> points;
	private double tolerance;
	private CenteredDiscretization2D discr;
	private Boolean found;
	
	private static String data = "";
	private static final String filepath = "pwd.txt";
	
	public PasswordLogin(String user) throws LoginException {
		if(!user.matches("[A-Za-z0-9]+") || user.equals("username")) {
			throw new LoginException("Invalid User Name!");
		}
		username = user;
		details = new LoginInfo(username);
		data = "";
		points = null;
		discr = null;
		tolerance = 0.0;
		found = LoginInfo.userExists(username, filepath, data);

		// test output: userExists
		//System.out.println("User " + username + " " + found);
		if (found) {
			loadUserData(data);
		}
		else {
			// choose random imageID
			details.setImageID(randomInt(0,8));
		}
	}

	private void loadUserData(String line) {
		// Load Login data from file
		offsets = new ArrayList<Double>();
		
		details.setImageID(line.substring(username.length()+1, username.length()+2));
			
		line = line.substring(username.length()+3);
		Scanner	scan = new Scanner(line);
		scan.useDelimiter("\t|,| ");
		while(scan.hasNextDouble())
		{
			double d = scan.nextDouble();
			   //System.out.println(d);
			   offsets.add(d);
		}
		
		String pwd = scan.next();
		// Test Output
		//System.out.println(pwd);
		details.setPassword(pwd);
		scan.close();
	}
	
	// if user name does not exist return random id
		private int randomInt(int min, int max) {
			return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public LoginInfo getDetails() {
		return details;
	}
	
	public void setPoints(List<Point2D> points) {
		this.points = points;
	}
	
	public void setTolerance(double r) {
		tolerance = r;
	}
	
	public static void setData(String s) {
		data = s;
	}
	
	public Boolean check() throws LoginException {
		Boolean loginSuccess = false;
		if (points.size() < 5) {
			//System.out.println("Please try again: At least 5 points are needed.");
			throw new LoginException("Password too short");
		}
		else if (found && (points.size()==offsets.size()/2)) {
			discr = new CenteredDiscretization2D(points, tolerance, offsets);
			String password = discr.getPasswordString();
			//System.out.println(password);
			String correctHash = details.getPassword();
			//System.out.println(correctHash);
			try {
				loginSuccess = PasswordHash.validatePassword(password, correctHash);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return loginSuccess;
	}
	public class LoginException extends Exception {
		public LoginException(String string) {
			super(string);
		}	
	}
}