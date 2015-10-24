package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

/* Handles Grid Overlay on loginImage and coordinates, discretization etc
 * 
 */
public class Grid extends JLabel{
	private final static double pointRadius = 10;
	private double gridSquareSize;
	private double columns, rows;
	private Dimension size;
	private boolean gridOn;
	private List<Point2D> points;
	private int numberPoints = 0;
	private JLabel clickCounter;
	
	private static final Color gridColour = new Color(160, 165, 185, 120);
	private static final Color pointColour = new Color(30, 140, 90, 180);
	private static final Color textColour = new Color(240, 240, 240, 220);
	
	
	public Grid(double gridSize, int width, int height, Boolean grid, JLabel clickCounter) {
		gridOn = grid;
		points = new ArrayList<Point2D>();
		size = new Dimension(width, height);
		gridSquareSize = gridSize;
		columns = width/gridSquareSize;
		rows = height/gridSquareSize;
		this.clickCounter = clickCounter;
		
		setPreferredSize(size);
		setBackground(Color.WHITE);
		setBounds( 0, 0,  
                width,
                height);
		addMouseListener(new PointClick());
		//setOpaque(true);
	}
	
	public List<Point2D> getPoints() {
		return points;
	}	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(gridColour);
		super.paintComponent(g2);
		if (gridOn) {
			drawGrid(g2);
		}
		drawClickPoints(g2, pointRadius);
		drawLabels(g2);
	}

	private void drawGrid(Graphics2D g2) {
		int x = 0, y = 0;
		for (int i = 1; i < rows; i++) {
			y += gridSquareSize;
			g2.drawLine(0, y, (int)size.getWidth(), y);
		}
		for (int j = 1; j < columns; j++) {
			x += gridSquareSize;
			g2.drawLine(x, 0, x, (int)size.getHeight());
		}
	}
	
	private void drawClickPoints(Graphics2D g2, double r) {
		for (int i = 0; i<points.size(); i++) {
			Point2D p = points.get(i);
			double x = p.getX();
			double y = p.getY();
			drawCenteredCircle(g2, x, y, r*2);
			g2.setColor(new Color(20, 20, 20, 255));
			g2.fillRect((int)(x-0.5), (int)(y-0.5), 2, 2);
		}
	}
	
	public void drawCenteredCircle(Graphics2D g2, double d, double e, double di) {
		d = d-(di/2);
		e = e-(di/2);
		int x = (int) (d+0.5);
		int y = (int) (e+0.5);
		int diameter = (int) (di+0.5);
		g2.setColor(pointColour);
		g2.fillOval(x,y,diameter,diameter);
		g2.setColor(new Color(255, 255, 255, 100));
		g2.drawOval(x,y,diameter,diameter);
	}
	
	private void drawLabels(Graphics2D g2) {
		for (int i = 0; i<points.size(); i++) {
			Point2D p = points.get(i);
			int x = (int)(p.getX()+0.5);
			int y = (int)(p.getY()+0.5);
			if (p.getX() + 15 < size.getWidth()) {
				x = x + 5;
			}
			else {
				x = x - 12;
			}
			if (p.getY() - 15 > 0) {
				y = y - 8;
			}
			else {
				y = y + 18;
			}
			String n = Integer.toString(i+1);
		    g2.setColor(new Color(50, 50, 50, 220));
		    g2.drawString(n, ShiftWest(x, 1), ShiftNorth(y, 1));
		    g2.drawString(n, ShiftWest(x, 1), ShiftSouth(y, 1));
		    g2.drawString(n, ShiftEast(x, 1), ShiftNorth(y, 1));
		    g2.drawString(n, ShiftEast(x, 1), ShiftSouth(y, 1));
		    g2.setColor(textColour);
			g2.drawString(n, x, y);
		}
	}
	
	int ShiftNorth(int p, int distance) {
		return (p - distance);
	}
	int ShiftSouth(int p, int distance) {
		return (p + distance);
	}
	int ShiftEast(int p, int distance) {
		return (p + distance);
	}
	int ShiftWest(int p, int distance) {
		return (p - distance);
	}
	
	public void toggleGrid() {
		if(gridOn) {
			gridOn=false;
		} 
		else {
			gridOn=true;
		}
		repaint();
		revalidate();
	}
	public Boolean isGridEnabled() {
		return gridOn;
	}
	
	public int getNumberPoints() {
		return numberPoints;
	}
	
	public void removeLastPoint() {
		if (!points.isEmpty()) {
			points.remove(points.size()-1);
			numberPoints--;
			clickCounter.setText(Integer.toString(numberPoints));
		}
		repaint();
		revalidate();
	}
	
	public void clearPoints() {
		points.removeAll(points);
		numberPoints = 0;
		clickCounter.setText("0");
		repaint();
		revalidate();
	}
	
	private class PointClick extends MouseAdapter {
		Point2D p;
		/*public void mouseClicked(MouseEvent e) {
			Point2D p = e.getPoint();
			points.add(p);
			numberPoints++;
			clickCounter.setText(Integer.toString(numberPoints));
			repaint();
			revalidate();
		}*/
		public void mouseReleased(MouseEvent e) {
			Point2D p = e.getPoint();
			points.add(p);
			numberPoints++;
			clickCounter.setText(Integer.toString(numberPoints));
			repaint();
			revalidate();
		}
	}
}
