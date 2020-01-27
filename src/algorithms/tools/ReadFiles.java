package algorithms.tools;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class ReadFiles {

	private static ReadFiles instance;
	private int currentIndex;
	private File directory;
	private String currentFileName;

	/**
	 * Constructeur ReadFile sur les samples (Singleton) 
	 */
	private ReadFiles() {
		directory = new File("samples/");
		currentIndex = 0;
	}
	
	/**
	 * Accesseur au singleton
	 * @return ReadFiles
	 */
	public static ReadFiles getInstance() {
		if (instance == null)
			instance = new ReadFiles();
		return instance;
	}
	
	/**
	 * @param String : filename
	 * @return ArrayList<Point> : la liste des points décrit dans le fichier filename
	 */
	public ArrayList<Point> getPointsFromFile(String filename) {
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			BufferedReader buffReader = new BufferedReader(new FileReader(filename));
			String line;
			String[] point; // contient les coordonnées du point
			while ((line = buffReader.readLine()) != null) {
				point = line.split(" ");
				points.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);}
		return points;
	}
	
	/**
	 * @return ArrayList<Point> : la liste des points du fichier suivant
	 */
	public ArrayList<Point> getNextFile() {
		currentIndex = (currentIndex + 1);
		if (currentIndex >= directory.listFiles().length)
			return null;
		while (!(directory.listFiles()[currentIndex].isFile())) {
			currentIndex = (currentIndex + 1);
			if (currentIndex >= directory.listFiles().length)
				return null;
		}
		currentFileName = directory.listFiles()[currentIndex].getName();
		return getPointsFromFile("samples/" + directory.listFiles()[currentIndex].getName());
	}
	
	/**
	 * @return String : le nom du fichier courrant
	 */
	public String getCurrentFileName() {
		return currentFileName;
	}
}
