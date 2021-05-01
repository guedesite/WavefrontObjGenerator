package fr.guedesite.obj;

public class VectorObj {
	
	public double x, y, z;
	public Index index;

	public VectorObj(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.index=new Index();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    VectorObj vec = (VectorObj) o;

	    if(vec.x == this.x && vec.y == this.y && vec.z == this.z) {
	    	this.index = ((VectorObj) o).index;
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	class Index {
		public int index = -1;
	}
	
	@Override
	public int hashCode() {
	    return (int) (this.x * this.y * this.z);
	}
	
	public String toObj(double scale, int index) {
		this.index.index = index;
		return "v "+(x*scale)+" "+(y*scale)+" "+(z*scale)+"\n";
	}
	
	@Override
	public String toString() {
		return "v "+(x)+" "+(y)+" "+(z);
	}
	
	public void applytransform(VectorObj v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}
}
