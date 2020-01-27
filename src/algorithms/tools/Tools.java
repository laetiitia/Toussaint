package algorithms.tools;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;


public class Tools {
	
	/**
	 *  Point[] --> ArrayList<Point>
	 */
	public static ArrayList<Point> tabToArray(Point[] points){
		ArrayList<Point> res = new ArrayList<>();
		for(Point p : points)
			res.add(p);
		return res;
	}
	
	
	/**
	 * @param  double : radius
	 * @return double : aire d'un cercle
	 */
	public static double getCircleArea(double radius){
		return Math.PI*(radius)*(radius);
	}
	
	/**
	 * @param Point[] : les 4 points d'un rectangle
	 * @return double : aire d'un rectangle
	 */
	public static double getRectArea(Point[] rectangle) {
		Point A = rectangle[0];
		Point B = rectangle[1];
		Point C = rectangle[2];
		return A.distance(B) * B.distance(C);
	}
	
	/**
	 * @param ArrayList<Point> : les points de l'enveloppe convexe du polygone
	 * @return double : aire d'un polygone
	 */
	public static double getPolyArea(ArrayList<Point> points){
		double area = 0.0;
		int taille = points.size();
		for (int i = 0; i < taille; i++) {
				area = area + ( points.get(i).x * points.get((i+1)%taille).y) - (points.get(i).y * points.get((i+1)%taille).x);
		}
		return Math.abs(0.5*area);
	}

	/**
	 * @param ArrayList<Point> : l'enveloppe convexe du polygone
	 * @param Circle : le cercle
	 * @return double : le rapport de qualité
	 */
	public static double getQualityPolyCircle(ArrayList<Point> polygone , Circle circle){ 
		return ( getCircleArea(circle.getRadius()) / getPolyArea(polygone) ) - 1 ;
	}
	
	/**
	 * @param ArrayList<Point> : l'enveloppe convexe du polygone
	 * @param Point[] : les 4 points d'un rectangle
	 * @return double : le rapport de qualité 
	 */
	public static double getQualityPolyRect(ArrayList<Point> polygone , Point[] rectangle){ 
		return ( (getRectArea(rectangle)) / getPolyArea(polygone) ) - 1 ;
	}
	
	
	/**
	 * 
	 * @param int : le nombre de points voulu
	 * @return ArrayList<Point>
	 */
	public static ArrayList<Point> generatePoints(int nb){
		ArrayList<Point> res = new ArrayList<Point>();
		int x,y;
		for(int i=0; i<nb ;i++) {
			x = 100 + (int)(Math.random() * 1000);
			y = 100 + (int)(Math.random() * 1000);
			res.add(new Point(x,y));
		}
		
		return res;
	}
	
	public static double crossProduct(Point p, Point q, Point s, Point t){
		return ((q.x-p.x)*(t.y-s.y)-(q.y-p.y)*(t.x-s.x));
	}
	public static double distance(Point p, Point a, Point b) {
		return Math.abs(crossProduct(a,b,a,p));
	}



}
