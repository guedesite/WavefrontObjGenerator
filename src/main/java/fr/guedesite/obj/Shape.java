package fr.guedesite.obj;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Shape {

	private List<VectorObj> VectorObj;
	private Material material;
	
	private Shape(List<VectorObj> VectorObj, Material  material) {
		this.VectorObj = VectorObj;
		this.material = material;
	}
	
	public Stream<VectorObj> getStream() {
		return this.VectorObj.stream();
	}
	
	public List<VectorObj> getList() {
		return this.VectorObj;
	}
	
	public Supplier<Stream<VectorObj>> getStreamSupplier() {
		return () -> this.VectorObj.stream();
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public static Shape createShape(Material material,VectorObj... vector) {
		return new Shape(Arrays.asList(vector),material);
	}
	
	public Stream<VectorObj> applyTransform(VectorObj vec) {
		this.VectorObj.forEach(v->v.applytransform(vec));
		return this.getStream();
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
