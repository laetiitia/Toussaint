package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import algorithms.tools.Tools;

public class Graham {
	/**
	 * @param ArrayList<Point> : ensemble de points
	 * @return ArrayList<Point> : l'enveloppe convexe selon l'algorithme de Graham
	 */
	public static ArrayList<Point> getConvexHull(ArrayList<Point> points){
		if (points.isEmpty() ||points.size()<3) 
			return points;
		
		//On realise une élimination par pixel sur notre ensemble de point 
		ArrayList<Point> result = getTriPixel(points); 
		for (int i=1; i < result.size()+2; i++) {
			Point p = result.get((i-1)%result.size());
			Point q = result.get(i%result.size());
			Point r = result.get((i+1)%result.size());
			if (Tools.crossProduct(p,q,p,r) > 0) {
				result.remove(i%result.size());
				if (i==2) 
					i=1;
				if (i>2) 
					i-=2;
			}
		}
		return result;
	}
	
	/**
	 * @param ArrayList<Point> : ensemble de point initiale
	 * @return ArrayList<Point> : Apres une élimination par pixel, on obtient un ensemble de point potentiels pour l'enveloppe convexe
	 */
	 private static ArrayList<Point> getTriPixel(ArrayList<Point> points){
		 int xMax=points.get(0).x;		 
		 for (Point p: points) {
			 if (p.x > xMax) 
				 xMax=p.x;
		 }
		 //Création des tableaux supérieure et inférieure 
		 Point[] yMax = new Point[xMax+1];
		 Point[] yMin = new Point[xMax+1];
		 //Initialisation des tableaux
		 for (Point p: points) {
			 if (yMax[p.x]==null || p.y > yMax[p.x].y) 
				 yMax[p.x]=p;
			 if (yMin[p.x]==null || p.y < yMin[p.x].y) 
				 yMin[p.x]=p;
		 }
		 ArrayList<Point> result = new ArrayList<Point>();
		 //On recupere les points potentiels de l'enveloppe convexe de la partie inferieure (yMax)
		 for (int i=0; i < xMax+1; i++) {
			 if (yMax[i]!=null) 
				 result.add(yMax[i]);
		 }
		//On recupere les points potentiels de l'enveloppe convexe de la partie superieure (yMax)
		 for (int i=xMax; i>=0; i--) {
			 if (yMin[i]!=null && !result.get(result.size()-1).equals(yMin[i])) 
				 result.add(yMin[i]);
		 }
		 if (result.get(result.size()-1).equals(result.get(0))) 
			 result.remove(result.size()-1);
		 return result;
	 }
}
