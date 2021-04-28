/*
 * Copyright 2016 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package fr.guedesite.wavefront;

import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.guedesite.wavefront.io.ObjWriter;
import fr.guedesite.wavefront.io.Writable;

/**
 * Represents a 3D Scene consisting of multiple {@link Obj}ects.
 * 
 * A Scene can be written to a file in Wavefront.obj format
 * to be viewed with an external 3D model viewer.
 * 
 * See file format description at
 * https://en.wikipedia.org/wiki/Wavefront_.obj_file
 * 
 * @author Christian Garbs &lt;mitch@cgarbs.de&gt;
 * @since 0.6.0
 */
public class Scene extends Container<Scene, Obj> implements Writable
{
	
	public static PrintStream obj, mtl;
	
	private static List<Scene> all;
	
	public static void add(Obj object, Color c) {
		for(int i = 0; i<all.size();i++) {
			if(all.get(i).getColor().equals(c)) {
				all.set(i, all.get(i).addObject(object));
				all.get(i).setColor(c);
				return;
			}
		}
		all.add(new Scene(object, c));
	}
	
	public static void init(String file) throws FileNotFoundException {
		obj = new PrintStream(new FileOutputStream(file+".obj"));
		mtl = new PrintStream(new FileOutputStream(file+".mtl"));
		all = new ArrayList<Scene>();
	}
	
	public static void writeAll()
	{
		List<Face> f = new ArrayList<Face>();
		for(Scene sc:all) {
			f.addAll(sc.faces());
		}
		
		new ObjWriter(f)
		.write(new Color(255,0,0));
	}
	
	private static final String SHORTHAND = "S";

	/**
	 * Creates a Scene consisting of the given Objects.
	 * 
	 * @param objects
	 *            the Objects of the Scene
	 * @since 0.6.0
	 */
	private Color c = new Color(255,255,255);
	
	public Scene(Obj... objects)
	{
		this(Arrays.asList(objects));
	}
	
	public Scene(Obj objects, Color col)
	{
		this(Arrays.asList(objects));
		this.setColor(col);
	}


	/**
	 * Creates a Scene consisting of the given Objects.
	 * 
	 * @param objects
	 *            the Objects of the Scene
	 * @since 0.6.0
	 */
	public Scene(List<Obj> objects)
	{
		super(SHORTHAND, objects);
	}

	/**
	 * Creates a copy of this Scene with an additional Object.
	 * 
	 * @param object
	 *            the Object
	 * @return the new Scene with the additional Object
	 * @since 0.6.0
	 */
	public Scene addObject(Obj object)
	{
		return getInstance(Stream //
				.concat(stream(), Stream.of(object)) //
				.collect(toList()));
	}

	@Override
	protected Scene getInstance(List<Obj> objects)
	{
		return new Scene(objects);
	}

	@Override
	public String toString()
	{
		return stream() //
				.map(Obj::toString) //
				.collect(Collectors.joining("\n  ", SHORTHAND + "{\n  ", "\n}"));
	}

	@Override
	public List<Face> faces()
	{
		return stream() //
				.flatMap(Obj::stream) //
				.collect(toList());
	}

	@Override
	public Color getColor() {
		return this.c;
	}
	
	@Override
	public void setColor(Color col) {
		this.c=col;
	}

}
