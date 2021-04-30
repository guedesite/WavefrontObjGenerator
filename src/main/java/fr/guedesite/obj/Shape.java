package fr.guedesite.obj;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Shape {

	private Supplier<Stream<VectorObj>> VectorObj;
	private Material material;
	
	private Shape(Supplier<Stream<VectorObj>> VectorObj,Material material) {
		this.VectorObj = VectorObj;
		this.material = material;
	}
	
	public Stream<VectorObj> getStream() {
		return this.VectorObj.get();
	}
	
	public Supplier<Stream<VectorObj>> getStreamSupplier() {
		return this.VectorObj;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public static Shape createShape(Material material,VectorObj... vector) {
		return new Shape(() -> Arrays.stream(vector),material);
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o)
	        return true;
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    Shape face = (Shape) o;
	    return this.VectorObj.equals(face.getStream()) && this.material.equals(face.getMaterial());
	}
}
