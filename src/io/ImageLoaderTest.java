package io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ImageLoaderTest {
	private String filename;
	
	public ImageLoaderTest(int testParameter) {
	    this.filename = "imgs/" + testParameter + ".jpg";
	  }
	
	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9} };	
		return Arrays.asList(data);
	}

	@Test
	public void testImageLoader(){
		ImageLoader il = new ImageLoader(filename);
		assertNotNull("Buffered image should not be null", il.getImage());
	}
	
	@AfterClass
	public static void numImagesTest(){
	}

}
