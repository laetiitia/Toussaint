package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import algorithms.tools.Tools;
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

	/**
	 * @param ArrayList<Point> : ensemble de points
	 * @return Line : une paire de points de la liste, de distance maximum.
	 */
	public Line calculDiametre(ArrayList<Point> points) {
		if (points.size()<2)  
			return null;
		Point p=points.get(0);
		Point q=points.get(1);
		for(int i=0; i < points.size();i++) {
			Point s = points.get(i);
			for(int j=(i+1); j< points.size();j++ ) {
				if(s.distance(points.get(j)) > p.distance(q)) {
					p=s;
					q=points.get(j); 
				}
			}
		}
		return new Line(p,q);
	}

	/**
	 * @param ArrayList<Point> : ensemble de points
	 * @return Line : une paire de points de la liste, de distance maximum.
	 */
	public Line calculDiametreOptimise(ArrayList<Point> points) {
		return Ritter.calculDiametreOptimise(points);
	}


	//----------------------------------------- EXERCICE 2 ---------------------------------------------------------------
	/** 
	 * @param ArrayList<Point> : ensemble de points
	 * @return Circle : cercle minimun selon l'algorithme de Ritter
	 */
	public Circle calculCercleMin(ArrayList<Point> points) {
		return Ritter.calculCercleMin(points);
	}

//----------------------------------------- EXERCICE 1 ---------------------------------------------------------------

  /** 
   * @param ArrayList<Point> : ensemble de points
   * @return ArrayList<Point> : le rectangle minimun selon l'algorithme de Toussaint
   */
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
    ArrayList<Point> enveloppe = Graham.getConvexHull(points);
    Point[] rect = Toussaint.getRectMin(enveloppe);
	 ArrayList<Point> rectMin = new ArrayList<Point>();
	 for(Point p : rect)
		 rectMin.add(p);
    return rectMin;
    
    //Normalement:
    //return enveloppe;
  }
  
}