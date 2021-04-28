package fr.guedesite.wavefront;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import fr.guedesite.wavefront.primitive.Cube;

public class main {

	public static void main(String[] args) {
		
		
		try {
			Scene.init("cube");
			
			Scene.add(new Cube(new Coordinate(0,0,0), 1), new Color(0,0,255));
			Scene.add(new Cube(new Coordinate(0,2,2), 1), new Color(255,0,0));

			
			Scene.writeAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
