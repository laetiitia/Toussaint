package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import algorithms.tools.Tools;
import supportGUI.Circle;
import supportGUI.Line;

public class Ritter {
	
	/** 
	 * @param ArrayList<Point> :  ensemble de points
	 * @return ArrayList<Line> : liste de paires antipodales
	 */
	private static ArrayList<Line> calculPairesAntipodales(ArrayList<Point> points) {
		ArrayList<Point> p = Graham.getConvexHull(points); // p est l'enveloppe convexe de points
		int n = p.size();
		ArrayList<Line> antipodales = new ArrayList<Line>();
		int k = 1;
		while (Tools.distance(p.get(k),p.get(n-1),p.get(0)) < Tools.distance(p.get((k+1)%n),p.get(n-1),p.get(0))) {
			k++;
		}
		int i = 0;
		int j = k;
		while (i<=k && j<n) {
			while (Tools.distance(p.get(j),p.get(i),p.get(i+1)) < Tools.distance(p.get((j+1)%n),p.get(i),p.get(i+1)) && j<n-1) {
				antipodales.add(new Line(p.get(i),p.get(j)));
				j++;
			}
			antipodales.add(new Line(p.get(i),p.get(j)));
			i++;
		}
		return antipodales;
	}


	/**
	 * @param ArrayList<Point> : ensemble de points
	 * @return Line : une paire de points de la liste, de distance maximum.
	 */
	public static Line calculDiametreOptimise(ArrayList<Point> points) {
		if (points.size()<2) 
			return null;
		ArrayList<Line> antipodales = calculPairesAntipodales(points);
		Point p=antipodales.get(0).getP();
		Point q=antipodales.get(0).getQ();
		for (Line a: antipodales) { 
			if (a.getP().distance(a.getQ())>p.distance(q)) {
				p=a.getP();
				q=a.getQ();
			}
		}
		return new Line(p,q);
	}


	/** 
	 * @param ArrayList<Point> : ensemble de points
	 * @return Circle : cercle minimun selon l'algorithme de Ritter
	 */
	public static Circle calculCercleMin(ArrayList<Point> points) {
		if (points.isEmpty() || points.size()<1)
			return null;
		//On clone l'ensemble des points car on va devoir supprimer des valeurs
		ArrayList<Point> copy = (ArrayList<Point>)points.clone();   
		//Etape 1
		//On recupere les deux points du diametre pour avoir une premiere approximation du cercle minimun
		Line diametre = calculDiametreOptimise(copy);
		Point p = diametre.getP();
		Point q = diametre.getQ();

		//Soit c le centre de PQ et de notre cercle
		double cX = 0.5*(p.x+q.x);
		double cY = 0.5*(p.y+q.y);
		double cRadius = 0.5*p.distance(q);

		//On enleve tout les points contenu dans ce cercle
		copy.remove(p);
		copy.remove(q);

		while (!copy.isEmpty()){
			Point s = copy.remove(0);
			double distanceFromCToS = Math.sqrt((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY));
			//Si le point s est contenu dans le cercle on passe au tour suivant car on la deja enlever
			if (distanceFromCToS <= cRadius) 
				continue;
			//Sinon on redefini le centre du point en incluant s
			double cPrimeRadius = 0.5*(cRadius+distanceFromCToS);
			double alpha = cPrimeRadius/(double)(distanceFromCToS);
			double beta = (distanceFromCToS-cPrimeRadius)/(double)(distanceFromCToS);
			double cPrimeX = alpha*cX+beta*s.x;
			double cPrimeY = alpha*cY+beta*s.y;
			cRadius = cPrimeRadius;
			cX = cPrimeX;
			cY = cPrimeY;
		}
		return new Circle(new Point((int)cX,(int)cY),(int)cRadius);
	}
}
