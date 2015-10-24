/**
 * 
 */
package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Phil
 *
 */
public class FileIOTest {
	private static String filename;
	private static String wrongFilename;
	private static String contents;
	private static String tmpFile = "password.txt";
	/**
	 * @throws IOException 
	 * @throws java.lang.Exception
	 */

	@BeforeClass
	public static void setUp() throws IOException {
		filename = "pwd.txt";
		wrongFilename = "password1.txt";
		//tmpFile = new File("password.txt");
		//tmpFile.createNewFile();
		contents = "userinfo";
	}
	
	@AfterClass
	public static void tearDown() {
		//tmpFile.delete();
	}

	/**
	 * Test method for {@link io.FileIO#readFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testFileExists() throws IOException {
		//assertTrue("password file password.txt exists", tmpFile.exists());
	}
	
	/**
	 * Test method for {@link io.FileIO#readFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test(expected = IOException.class)
	public void testIOException() throws IOException {
		assertNull("IOException: password.txt file not found", FileIO.readFile(wrongFilename));
	}
	
	/**
	 * Test method for {@link io.FileIO#writeFile(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testReadFile() throws IOException {
		assertNotNull(("content can be read from file " + filename), FileIO.readFile(filename));
       //System.out.println(FileIO.readFile(filename));
	}
	
	/**
	 * Test method for {@link io.FileIO#readFile(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testWriteFile() throws IOException {
		FileIO.writeFile("password.txt", contents);
		System.out.println(contents);
		System.out.println(FileIO.readFile("password.txt"));
		assertEquals("String written to test file is equal to String read from test file", contents, FileIO.readFile("password.txt"));
	}

}
