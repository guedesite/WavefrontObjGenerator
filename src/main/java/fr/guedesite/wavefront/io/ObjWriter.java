package fr.guedesite.wavefront.io;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import fr.guedesite.wavefront.Face;
import fr.guedesite.wavefront.Scene;
import fr.guedesite.wavefront.V;

public class ObjWriter
{
	final List<V> uniqueVertices;
	final List<Face> faces;

	public ObjWriter(List<Face> faces)
	{
		this.faces = faces;
		this.uniqueVertices = getUniqueVertices();
	}

	public void write(Color c)
	{
		PrintStream ps = Scene.obj;
		PrintStream mt = Scene.mtl;
		
		writeMaterial(mt,c);
		writeVertices(ps, c);
		writeFaces(ps, c);

	}

	private List<V> getUniqueVertices()
	{
		return faces.stream() //
				.flatMap(Face::vertices) //
				.sorted() //
				.distinct() //
				.collect(Collectors.toList());
	}

	private void writeMaterial(PrintStream mt,Color c) {
		mt.print("newmtl color_"+c.getRed()+"_"+c.getGreen()+"_"+c.getBlue()+"\n");
		mt.print("Kd "+(((double)c.getRed())/255)+" "+(((double)c.getGreen())/255)+" "+(((double)c.getBlue())/255)+"\n");
	}
	
	private void writeVertices(PrintStream ps,Color c)
	{
		ps.print("o cube_"+c.getRed()+"_"+c.getGreen()+"_"+c.getBlue()+"\n");
		Formatter formatter = new Formatter(Locale.ENGLISH);
		uniqueVertices.forEach((v) -> formatter.format("v %.0f %.0f %.0f\n", v.getX(), v.getY(), v.getZ()));
		ps.print(formatter.toString());
		formatter.close();
		
	}

	private void writeFaces(PrintStream ps, Color c)
	{
		
		ps.print("usemtl color_"+c.getRed()+"_"+c.getGreen()+"_"+c.getBlue()+"\n");
		ps.print("s off\n");
		
		faces.stream() //
				.map( //
						(face) -> face.vertices() //
								.map((vertex) -> uniqueVertices.indexOf(vertex) + 1) //
								.map(String::valueOf) //
								.collect(Collectors.joining(" "))) //
				.sorted() //
				.forEach((vertices) -> ps.printf("f %s\n", vertices));
	}
}
