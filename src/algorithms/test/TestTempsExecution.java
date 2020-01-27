package algorithms.test;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import algorithms.Graham;
import algorithms.Ritter;
import algorithms.Toussaint;
import algorithms.tools.Tools;
import supportGUI.Circle;

public class TestTempsExecution {

	public static void main(String[] args) {		
		//Initialisation des instances
		int cpt = 0;
		ArrayList<Point> points = null;
		ArrayList<Point> enveloppe = null;
		Circle circle = null;
		Point[] rectangle = null;
		
		//Temps d'execution
		long timeStartRectangle, timeEndRectangle;
		long timeStartCircle, timeEndCircle;
		long timeRectangle = 0, timeCircle = 0;
		int instance=1000;
		
		try {		
			BufferedWriter bw = new BufferedWriter(new FileWriter("ExecutionTime"));
			while(instance<52000) { 
				points = Tools.generatePoints(instance);;
				enveloppe = Graham.getConvexHull(points);
				//Test Rectangle
				timeStartRectangle = System.nanoTime();
				rectangle = Toussaint.getRectMin(enveloppe);
				timeEndRectangle = System.nanoTime();
				//Test Circle
				timeStartCircle = System.nanoTime();
				circle = Ritter.calculCercleMin(points);
				timeEndCircle = System.nanoTime();
				
				bw.write(String.valueOf(instance));
				bw.write(",");
				bw.write(String.valueOf(timeEndRectangle - timeStartRectangle));
				bw.write(",");
				bw.write(String.valueOf(timeEndCircle - timeStartCircle));
				bw.write("\n");
				
				timeRectangle += (timeEndRectangle - timeStartRectangle);
				timeCircle += (timeEndCircle - timeStartCircle);
				instance+=1000;
				cpt++;
			}
			
			bw.close();
			
			System.out.println("RECTANGLE : " + ((double) timeRectangle / cpt) + " ns");
			System.out.println("CIRCLE : " + ((double) timeCircle / cpt) + " ns");

		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error BufferedWriter.");
		}

	}

}
