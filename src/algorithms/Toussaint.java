package algorithms;

import java.awt.Point;
import java.util.ArrayList;


public class Toussaint {
	
	/** L'algortihme de Toussaint permet d'obtenir un rectangle d'aire minimum 
	 * @param  ArrayList<Point> : l'enveloppe convexe
	 * @return Point [] : constituant le rectangle minimun
	 */
	public static Point[] getRectMin(ArrayList<Point> enveloppe){
		if(enveloppe.isEmpty() || enveloppe.size() < 4)
			return null;	 
		 
		 //ETAPE 1:
		 //On cherche les points d'abscisse minimun, maximum et d'ordonnee minimun, maximum:
		 int xMin=0, xMax=0, yMin=0, yMax=0, taille = enveloppe.size();
		 for(int i=1; i < taille; i++) {
			 Point p = enveloppe.get(i);
			 if(p.x < enveloppe.get(xMin).x) 
				 xMin = i;
			 if(p.x > enveloppe.get(xMax).x) 
				 xMax = i;
			 if(p.y < enveloppe.get(yMin).y) 
				 yMin = i;
			 if(p.y > enveloppe.get(yMax).y) 
				 yMax = i;
		 }
		 
		 Point i = enveloppe.get(xMin);
		 Point k = enveloppe.get(xMax);
		 Point j = enveloppe.get(yMax);
		 Point l = enveloppe.get(yMin);
		 
		 //ETAPE 2:
		 //On construit les points d'intersection des droites i, j ,k ,l:
		 Point A = new Point (i.x, j.y);
		 Point B = new Point (k.x, j.y);
		 Point C = new Point (k.x, l.y);
		 Point D = new Point (i.x, l.y);
		 
		 //Initialisation de l'aire du rectangle minimun
		 Point[] rect = new Point[]{A,B,C,D};
		 double aireMin = B.distance(A) * A.distance(D);
		 
		 //ETAPE 3:
		 //Pour calculer l'angle minimum on calcule le cosinus entre les deux droites grace à leurs vecteurs directeurs
		 //Le cosinus maximum represente notre angle minimum
		 double angleK, angleI, angleL, angleJ, aireTmp, cosMax;
		 int index = -1, pi = xMin, pl = yMin, pk = xMax, pj = yMax;
		 boolean iMove = false, kMove = false, lMove = false, jMove=false, fini = false;
		 
		 while(!fini) {		 		 
			 cosMax = Double.MIN_VALUE;
			 angleI = getCosinus(i, A, i, enveloppe.get((pi+1)%taille));	 
			 if(angleI > cosMax) {
				 cosMax = angleI;
				 index = pi;
			 }
			 
			 angleJ = getCosinus(j, B, j, enveloppe.get((pj+1)%taille));	 
			 if(angleJ > cosMax) {
				 cosMax = angleJ;
				 index = pj;
			 }
			 angleK = getCosinus(k, C, k, enveloppe.get((pk+1)%taille));
			 if(angleK > cosMax) {
				 cosMax = angleK;
				 index = pk;
			 }
			 
			 angleL = getCosinus(l, D, l, enveloppe.get((pl+1)%taille));
			 if(angleL > cosMax) {
				 cosMax = angleL;
				 index = pl;
			 }
			 
			 //Etape 4
			 //En prenant le coté de l'enveloppe ayant l'angle minimum, on créér le nouveau rectangle 
			 Point tmp;
			 if (index == pi) {
				 tmp = enveloppe.get((pi+1)%taille);
				 
				//Pas  de changement par rapport au rectangle minimun si cosMax =1.0, mise à jour des points
				 if(cosMax!=1.0) {
					 //On cherche le point d'intersection de la droite perpendiculaire à l'enveloppe convexe passant par le point j
					 A = getPerpendicular(i, tmp, j);
					 //On cherche le point d'intersection de la droite perpendiculaire à l'enveloppe convexe passant par le point l
					 D = getPerpendicular(i, tmp, l);
					 //On fait la même chose pour trouver les autres points d'intersection définisant notre rectangle
					 B = getPerpendicular(A, j, k);
					 C = getPerpendicular(D, l, k);
				 }

				 //Mise à jour:
				 pi = (pi + 1)%taille;
				 iMove=true;
				 i=tmp;
			 }else if (index == pl) {
				 tmp = enveloppe.get((pl+1)%taille);
				 if(cosMax!=1.0) {
					 D = getPerpendicular(l, tmp, i);
					 C = getPerpendicular(l, tmp, k);
					 A = getPerpendicular(D, i, j);
					 B = getPerpendicular(C, k, j);
				 }

				 //Mise à jour:
				 pl = (pl + 1)%taille;
				 lMove=true;
				 l=tmp;
			 }else if (index == pk) {
				 tmp = enveloppe.get((pk+1)%taille);
				 if(cosMax!=1.0) {
					 C = getPerpendicular(k, tmp, l);
					 B = getPerpendicular(k, tmp, j);
					 D = getPerpendicular(C, l, i);
					 A = getPerpendicular(B, j, i);
				 }

				 //Mise à jour:
				 pk = (pk + 1)%taille;
				 kMove=true;
				 k=tmp;
			 }else { 
				 tmp = enveloppe.get((pj+1)%taille);
				 if(cosMax!=1.0) {
					 A = getPerpendicular(j, tmp, i);
					 B = getPerpendicular(j, tmp, k);
					 C = getPerpendicular(B, k, l);
					 D = getPerpendicular(A, i, l);
				 }

				 //Mise à jour:
				 pj = (pj + 1)%taille;
				 jMove=true;
				 j=tmp;
			 }
			 
			 //Etape 5
			 //On calcule l'aire de ce nouveau rectangle et on met à jour le rectangle minimum si besoin
			 aireTmp = B.distance(A) * A.distance(D);
			 if(aireTmp>0.0 && aireTmp < aireMin ) {
				 aireMin = aireTmp;
				 rect = new Point[]{A,B,C,D};
			 }

			 //Condition d'arret: lorsqu'on a fait le tour de l'enveloppe convexe, on aura testé tout les cotés possibles
			 if ( (iMove && pi == xMin) || (jMove && pj == yMax) || (kMove && pk == xMax) || (lMove && pl == yMin)) 
				 fini=true;
		 }
		 
		return rect;
	}

	/***************************** METHODES ************************************/
	
	/**
	 * @param Point : start, end
	 * @return double[]{x, y} : le vecteur directeur de la droite passant par start et end 
	 */
	 private static double[] getVectorDirector(Point start, Point end) {
		 return new double[] { 1.0*(end.x - start.x), 1.0*(end.y - start.y)};		
	 }
	 
	 
	 /**
	  * Droite d1 passe part start1 et end1 tandis que d2 passe part start2 et end2
	  * @param Point : start1, end1, start2, end2
	  * @return double : le cosinus de d1 et d2 
	  */
	 public static double getCosinus(Point start1, Point end1, Point start2, Point end2) {
		 double[] v1 = getVectorDirector(start1, end1);
		 double[] v2 = getVectorDirector(start2, end2);
		 double norme1 = Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1]);
		 double norme2 = Math.sqrt(v2[0] * v2[0] + v2[1] * v2[1]);
		 double dotProduct = v1[0] * v2[0] + v1[1] * v2[1];
		 double cos = dotProduct / (norme1 * norme2);
		 return Math.abs(cos);
	 }


	 /**
	  * Soit AB une droite et C quelconque
	  * @param Point : A, B, C
	  * @return Point : D appartenant à AB et dont la droite DC est perpendiculaire à AB 
	  */
	 public static Point getPerpendicular(Point A, Point B, Point C){
		    double vx = B.x-A.x; //x du vecteur AB 
		    double vy = B.y-A.y; //y du vecteur AB
		    double ab2 = vx*vx + vy*vy; //norme au carré de AB
		    double u = ((C.x - A.x) * vx + (C.y - A.y) * vy) / (ab2);
		    return new Point ((int)Math.round(A.x + u * vx), (int)Math.round(A.y + u * vy)); //D appartient à AB
		}


}
