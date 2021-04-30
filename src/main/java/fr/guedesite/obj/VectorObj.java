package fr.guedesite.obj;

public class VectorObj {
	
	public double x, y, z;
	public int index = -1;

	public VectorObj(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    VectorObj vec = (VectorObj) o;
	    return vec.x == this.x && vec.y == this.y && vec.z == this.z;
	}
	
	@Override
	public int hashCode() {
	    return (int) (this.x * this.y * this.z) * this.index;
	}
	
	public String toObj(double scale, int index) {
		this.index = index;
		return "v "+(x*scale)+" "+(y*scale)+" "+(z*scale)+"\n";
	}
	
	@Override
	public String toString() {
		return "v "+(x)+" "+(y)+" "+(z);
	}
}
