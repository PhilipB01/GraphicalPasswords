package processing;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CenteredDiscretization2D {

	// var number of points
	private static double r;
	private static List<Double> offsets;
	private static List<Integer> gridSquareIndex;
	

	public CenteredDiscretization2D(List<Point2D> points, double tolerance) {
		r = tolerance;
		discretizePoints(points);
	}
	
	public CenteredDiscretization2D(List<Point2D> points, double tolerance, List<Double> offsets) {
		r = tolerance;
		this.offsets = offsets;
		gridSquareIndex = new ArrayList<Integer>(points.size()*2);
		discretizeCheck(points);
	}
	
	private void discretizePoints(List<Point2D> points) {
		int numOfPoints = points.size();
		offsets = new ArrayList<Double>(numOfPoints*2);
		gridSquareIndex = new ArrayList<Integer>(numOfPoints*2);
		
		for (int i=0; i<numOfPoints; i++) {
			Point2D p = points.get(i);
			addPoint(p.getX(), p.getY());
		}
	}
	
	// method takes a point x,y and r val
	// adds ix, iy and dx, dy to respective lists
	private void addPoint(double x, double y) {

		int iX = (int) Math.floor((x - r) / (2*r));
		int iY = (int) Math.floor((y - r) / (2*r));
		
		double dX = ((((x - r) % (2*r)) + (2*r)) % (2*r));
		double dY = ((((y - r) % (2*r)) + (2*r)) % (2*r));
		
		gridSquareIndex.add(iX);
		gridSquareIndex.add(iY);
		offsets.add(dX);
		offsets.add(dY);
	}
	
	public void discretizeCheck(List<Point2D> points) {
		int j=0;
		for (int i=0; i<points.size(); i++) {
			Point2D p = points.get(i);
			double d1 = offsets.get(j);
			double d2 = offsets.get(j+1);
			int iX = (int) Math.floor((p.getX() - d1) / (2*r));
			int iY = (int) Math.floor((p.getY() - d2) / (2*r));
			
			gridSquareIndex.add(iX);
			gridSquareIndex.add(iY);
			j+=2;
		}
	}
	
	public List<Integer> getIndexes() {
		return gridSquareIndex;
	}
	
	public String getPasswordString() {
		String s = "";
		for (int i=0; i<offsets.size(); i+=2) {
			DecimalFormat df = new DecimalFormat("#.##");
			double d1 = offsets.get(i);
			double d2 = offsets.get(i+1);
			int i1 = gridSquareIndex.get(i);
			int i2 = gridSquareIndex.get(i+1);
			
			s += df.format(d1) + "," + df.format(d2) + ","  + i1 + "," + i2;
			if (i<offsets.size()-2) {
				 s += ",";
			}
		}
		return s;
	}
	
	public String getOffsetString() {
		String s = "";
		for (int i=0; i<offsets.size(); i++) {
			double d = offsets.get(i);
			DecimalFormat df = new DecimalFormat("#.##");
			s += df.format(d);
			if (i<offsets.size()-1) {
			 s += ",";
			}
		}
		return s;
	}
}
