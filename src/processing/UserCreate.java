package processing;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class UserCreate {
	private LoginInfo userDetails;
	private CenteredDiscretization2D discr;
	private List<Point2D> points;
	private double tolerance;
	
	private static final String filepath = "pwd.txt";
	
	public UserCreate(String username, int selectedImg) throws UserException {
		if(!username.matches("[A-Za-z0-9]+") || username.equals("username")) {
			throw new UserException("Invalid User Name!", "inv");
		}
		else if (LoginInfo.userExists(username, filepath, null)) {
			// User already exists
			throw new UserException("User Already Exists!", "uniq");
		}
		else {
			userDetails = new LoginInfo(username);
			userDetails.setImageID(selectedImg);
		}
		
		discr = null;
		points = null;
		tolerance = 0.0;
	}
	// Keeps track of entered click points during password creation (via Grid class?)
	// Checks password length, validity(clicking on same point etc)
	// Undo/clear functions
	public LoginInfo getDetails() {
		return userDetails;
	}
	
	public void setPoints(List<Point2D> points) {
		this.points = points;
	}
	
	public void setTolerance(double r) {
		tolerance = r;
	}
	
	public void discretize() throws UserException {
		discr = new CenteredDiscretization2D(points, tolerance);
		if (points.size()<5) {
			// password too short
			throw new UserException("Password too short", "short");
		}
		// Prevents repeated grid click points: Tested security measure
		/*else if (!validPoints()) {
			// duplicate clicks
			throw new UserException("Duplicate click point detected", "duplicate");
		}*/
		else {
			// saves valid passwords to file
			store();
		}
	}
	
	private boolean validPoints() {
		List<Integer> indexes = discr.getIndexes();
		for (int i=0; i<indexes.size(); i+=2) {
			int i1 = indexes.get(i);
			int i2 = indexes.get(i+1);
			for (int j=i+2; j<indexes.size(); j+=2) {
				int j1 = indexes.get(j);
				int j2 = indexes.get(j+1);
				if (i1 == j1 && i2 == j2) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void store() {
		// hash discretized password and append it, along with username and imgID, to file
		String u = userDetails.getUser();
		int id = userDetails.getImageID();
		String d = discr.getOffsetString();
		String pw = discr.getPasswordString();
		
		// hash returns h(iX1, iY1, dX1, dY1 ... iXn, iYn, dYn, dYn) as String
		// pw = h(iX1, iY1, dX1, dY1 ... iXn, iYn, dYn, dYn) + " " + d(0...n)
		try {
			pw = PasswordHash.createHash(pw);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
		String result = u + " " + id + " " + d + " " + pw;
		// Test output
		/*
		System.out.println("New user added: ");
		System.out.println(result);
		*/
		
		File file = new File(filepath);
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
		    out.println(result);
		}catch (IOException e) {
			System.out.println("IOException- Could not write to file: ");
			e.printStackTrace();
		}
	}
	public class UserException extends Exception {
		private String reason;
		public UserException(String string, String cause) {
			super(string);
			reason = cause;
		}
		public String getReason() {
			return reason;
		}

	}
}
