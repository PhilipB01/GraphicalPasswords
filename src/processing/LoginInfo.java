package processing;

import io.FileIO;

import java.io.IOException;
import java.util.Scanner;


public class LoginInfo {
	private String username;
	private String password;
	private int imageID;
	
	public LoginInfo() {
		username = "";
		password = "";
		imageID = 0;
	}
	public LoginInfo(String user) {
		username = user;
		password = "";
		imageID = 0;
	}

	public String getUser() {
		return username;
	}
	public void setUser(String user) {
		username = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String pw) {
		password = pw;
	}
	
	public int getImageID() {
		return imageID;
	}
	
	public void setImageID(int id) {
		imageID = id;
	}
	public void setImageID(String idString) throws NumberFormatException{
		imageID = Integer.parseInt(idString);
	}
	
	// search for user in file
	public static Boolean userExists(String username, String filepath, String data) {
		//int userLength = username.length();
		String content = null;
		Scanner s = null;
		
		try {
			content = FileIO.readFile(filepath);
			s = new Scanner(content);
			String line = "";
			while (s.hasNextLine()) {
				line = s.nextLine();
				//System.out.println(line);
				Scanner nextString = new Scanner(line);
				String nextUser = "";
				if (nextString.hasNext()) {
					nextUser = nextString.next();
				}
				nextString.close();
				
				//System.out.println(nextUser);
				if (username.equals(nextUser)) {
					if (data != null) {
						PasswordLogin.setData(line);
					}
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("the password file does not exist : " + false);
		} finally {
			s.close();
		}
		return false;
	}
	
	//input: user name respective text box
	
	
	// checks if user name exists in password file
		// if true -- load correct image (how to map user name to image in storage?)
		// else load random image (Image Loading Class)
	
	// UI: Password Enter screen
	// After user has selected certain click points on image/grid and selected enter
		// Do following: 
			// Use grid discretization to transform click coordinates into correct grids
			// Hash discretized grid coordinates and compare with stored hash
	
	// If stored hash equals hashed grids then authentication success
		// Show page indicating login success and record attempts
	// Else login fail notification and record statistic
	
	
	
	// Output: successful authentication - true or false
}
