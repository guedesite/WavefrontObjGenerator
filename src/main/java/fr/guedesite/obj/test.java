package fr.guedesite.obj;

public class test {

	public static void main(String[] arg) {
		
		ObjCreator obj = new ObjCreator("cube.obj", "cube.mtl");
		Material blue = new Material("blue_cube");
		blue.setRGBDiffuseColor(255, 0, 0);
		
		obj.enableLog(true);
		
		Shape cube0 = Shape.createShape(blue, 
				new VectorObj(0D,0D,0D),
				new VectorObj(1D,0D,0D),
				new VectorObj(1D,0D,1D),
				new VectorObj(0D,0D,1D));
		
		Shape cube1 = Shape.createShape(blue, 
				new VectorObj(1D,0D,0D),
				new VectorObj(1D,1D,0D),
				new VectorObj(1D,1D,1D),
				new VectorObj(1D,0D,1D));
		
		Shape cube2 = Shape.createShape(blue, 
				new VectorObj(0D,0D,1D),
				new VectorObj(0D,1D,1D),
				new VectorObj(1D,1D,1D),
				new VectorObj(1D,0D,1D));
		
		Shape cube3 = Shape.createShape(blue, 
				new VectorObj(0D,0D,0D),
				new VectorObj(1D,0D,0D),
				new VectorObj(1D,1D,0D),
				new VectorObj(0D,1D,0D));
		
		Shape cube4 = Shape.createShape(blue, 
				new VectorObj(0D,0D,0D),
				new VectorObj(0D,0D,1D),
				new VectorObj(0D,1D,1D),
				new VectorObj(0D,1D,0D));
		
		Shape cube5 = Shape.createShape(blue, 
				new VectorObj(0D,1D,0D),
				new VectorObj(1D,1D,0D),
				new VectorObj(1D,1D,1D),
				new VectorObj(0D,1D,1D));
		

		obj.addShape(cube0);
		obj.addShape(cube1);
		obj.addShape(cube2);
		obj.addShape(cube3);
		obj.addShape(cube4);
		obj.addShape(cube5);
		
		/*
		 *  Also:
		 *  obj.addShape(cube0, cube1, cube2, cube3, cube4, cube5);
		 * 
		 */
		
		obj.process();
		
	}
	
}
