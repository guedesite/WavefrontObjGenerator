package fr.guedesite.obj;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjCreator extends Thread {

	private String fileObj, fileMtl;
	private List<Shape> allShape;
	private List<Material> allMaterial;
	private logObjCreator log;
	private double scale = 1D;
	private long timeInLog = 1000;
	private int ThreadPrio = Thread.NORM_PRIORITY;
	private boolean useThread = false;
	
	private VectorObj transform;
	
	public ObjCreator(String fileObj, String fileMtl) {
		this.fileMtl = fileMtl;
		this.fileObj = fileObj;
		this.allShape = new ArrayList<Shape>();
		this.allMaterial = new ArrayList<Material>();
	}
	
	public void applyTransform(VectorObj vector) {
		this.transform = vector;
	}
	
	public void useThread(boolean value) {
		this.useThread = true;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public void setThreadPriority(int priority) {
		this.ThreadPrio = priority;
	}
	
	public void setTimeBeforeLog(long TimeInMs) {
		this.timeInLog = TimeInMs;
	}
	
	public void enableLog(boolean value) {
		if(value) {
			this.log = new logObjCreator() {

				@Override
				public void log(String info, long index, long max) {
					System.out.println("[OBJCreator] "+info+" > "+index+"/"+max);
				}

				@Override
				public void log(String info) {
					System.out.println("[OBJCreator] "+info);
				}
			};
		} else {
			this.log = null;
		}
	}
	
	public void setLog(logObjCreator log) {
		this.log = log;
	}
	
	public void addShape(Shape c) {
		this.allShape.add(c);
		if(!this.allMaterial.contains(c.getMaterial())) {
			this.allMaterial.add(c.getMaterial());
		}
	}
	
	public void addShape(Shape... c) {
		this.allShape.addAll(Arrays.asList(c));
		for(Shape s:c) {
			if(!this.allMaterial.contains(s.getMaterial())) {
				this.allMaterial.add(s.getMaterial());
			}
		}

	}
	
	public void addShape(Collection<Shape> c) {
		this.allShape.addAll(c);
		for(Shape s:c) {
			if(!this.allMaterial.contains(s.getMaterial())) {
				this.allMaterial.add(s.getMaterial());
			}
		}

	}
	
	
	public void process() {
		if(useThread) {
			this.setPriority(this.ThreadPrio);
			this.setName("OBJ CREATOR MAIN");
			this.start();
		} else {
			this.run();
		}
	}
	
	private List<VectorObj> allV;
	private int indexFace = 0;
	private int indexLog;
	private long maxLog;
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		this.log("Process start");
		
		this.allV = new ArrayList<VectorObj>();
		
		Supplier<Stream<Shape>> shapes = () -> allShape.stream();
		Supplier<Stream<Material>> materials = () -> allMaterial.stream();
		
		this.log("Adding all shape ...");
		indexLog = 1;
		maxLog = shapes.get().count();
		if(this.transform != null) {
			shapes.get().forEach( x -> { this.allV.addAll(x.applyTransform(this.transform).collect(Collectors.toList())); 
			
			this.log("Add shape",indexLog,maxLog ); indexLog++;});
		} else {
			shapes.get().forEach( x -> { this.allV.addAll(x.getStream().collect(Collectors.toList())); 
			
			this.log("Add shape",indexLog,maxLog ); indexLog++;});
		}

		
		Supplier<Stream<VectorObj>> allVertice = () -> this.allV.stream().distinct();
		
		this.log("Save "+(this.allV.stream().count() - allVertice.get().count())+" vertice");
		
		final PrintStream obj = createPrintStream(this.fileObj);
		if(obj == null)
		{
			this.log("Process end, total time: "+((double)(System.currentTimeMillis() - startTime))/1000+"s");
			return;
		}
		
		
		indexFace = 1;
		StringBuilder builder = new StringBuilder();
		this.log("Adding all vertice ...");
		indexLog = 1;
		maxLog = allVertice.get().count();
		allVertice.get().forEach(x -> { builder.append(x.toObj(this.scale, indexFace)); indexFace++; this.log("Add vertice",indexLog,maxLog );indexLog++; });
		
		obj.print(builder.toString());
		
		this.log("Writing face ...");
		indexLog = 1;
		maxLog = shapes.get().count();
		

			materials.get().forEach( a -> { 
				StringBuilder bld = new StringBuilder();
				bld.append(a.toObj());
				shapes.get().filter(
						b -> b.getMaterial().equals(a)).forEach(c -> {
							bld.append("f");
							c.getStream().forEach(d -> bld.append(" "+d.index.index));
							bld.append("\n");
							this.log("Write face", indexLog, maxLog);
							indexLog++;
						});
				obj.print(bld.toString());
			});

		
		final PrintStream mtl = createPrintStream(this.fileMtl);
		if(mtl == null)
		{
			this.log("Process end, total time: "+((double)(System.currentTimeMillis() - startTime))/1000+"s");
			return;
		}
		
		this.log("Writing materials ...");
		indexLog = 1;
		maxLog = materials.get().count();
		
		materials.get().forEach( a -> { 
			this.log("Write material", indexLog, maxLog);
			indexLog++;
			mtl.print(a.toMtl());
		});
		
		this.log("Process end, total time: "+((double)(System.currentTimeMillis() - startTime))/1000+"s");
	}
	
	private PrintStream createPrintStream(String file) {
		try {
			return new PrintStream(new FileOutputStream(file));
		} catch(Exception e) {
			this.log("Can't create file "+file);
			e.printStackTrace();
			return null;
		}
	}
	
	private long lastLog = System.currentTimeMillis();
	private void log(String info) {
		if(this.log != null) {
			this.log.log(info);
		}
	}
	private void log(String info, long index, long max) {
		if(this.log != null) {
			if(index == max || lastLog < System.currentTimeMillis() - this.timeInLog) {
				this.log.log(info, index, max);
				lastLog = System.currentTimeMillis();
			}
		}
	}

	
}
