package algorithms.test;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import algorithms.Graham;
import algorithms.Ritter;
import algorithms.Toussaint;
import algorithms.tools.ReadFiles;
import algorithms.tools.Tools;
import supportGUI.Circle;


public class TestSamples {

	public static void main(String[] args) {
		String outputFileName = "TestSamples";	
		ReadFiles readf = ReadFiles.getInstance();	
		
		//Initialisation des instances
		int cpt = 0;
		ArrayList<Point> points = null;
		ArrayList<Point> enveloppe = null;
		Circle circle = null;
		Point[] rectangle = null;
		double qualiteRect = 0.0, qualiteCircle = 0.0;
		double sumRect = 0.0, sumCircle = 0.0;
		double areaRect, areaCircle, areaPoly;
		
		//Temps d'execution
		long timeStartRectangle, timeEndRectangle;
		long timeStartCircle, timeEndCircle;
		long timeRectangle = 0, timeCircle = 0;

		try {		
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
			while ((points = readf.getNextFile()) != null) {
				enveloppe = Graham.getConvexHull(points);
				//Test Rectangle
				timeStartRectangle = System.nanoTime();
				rectangle = Toussaint.getRectMin(enveloppe);
				timeEndRectangle = System.nanoTime();
				//Test Circle
				timeStartCircle = System.nanoTime();
				circle = Ritter.calculCercleMin(points);
				timeEndCircle = System.nanoTime();

				//Calcule des aires
				areaRect = Tools.getRectArea(rectangle);
				areaPoly = Tools.getPolyArea(enveloppe);
				areaCircle = Tools.getCircleArea(circle.getRadius());

				//Test Qualite
				qualiteRect = Tools.getQualityPolyRect(enveloppe, rectangle);
				qualiteCircle = Tools.getQualityPolyCircle(enveloppe, circle);

				bw.write(readf.getCurrentFileName());
				bw.write(",");
				bw.write(String.valueOf(areaPoly));
				bw.write(",");
				bw.write(String.valueOf(areaRect));
				bw.write(",");
				bw.write(String.valueOf(qualiteRect));
				bw.write(",");
				bw.write(String.valueOf(timeEndRectangle - timeStartRectangle));
				bw.write(",");
				bw.write(String.valueOf(areaCircle));
				bw.write(",");
				bw.write(String.valueOf(qualiteCircle));
				bw.write(",");
				bw.write(String.valueOf(timeEndCircle - timeStartCircle));
				bw.write("\n");

				sumRect += qualiteRect;
				sumCircle += qualiteCircle;
				timeRectangle += (timeEndRectangle - timeStartRectangle);
				timeCircle += (timeEndCircle - timeStartCircle);
				cpt++;
			}
			System.out.println("RECTANGLE : " + sumRect / cpt + " " + ((double) timeRectangle / cpt) + " ns");
			System.out.println("CIRCLE : " + sumCircle / cpt + " " + ((double) timeCircle / cpt) + " ns");

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error BufferedWriter.");
		}
	}

}
