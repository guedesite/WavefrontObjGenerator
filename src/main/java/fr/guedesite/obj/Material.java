package fr.guedesite.obj;

import java.awt.Color;

public class Material {

	private String name;
	private Color diffuse;
	private Color ambient;
	
	
	public Material(String name) {
		this.name=name.replaceAll(" ", "_");
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setRGBDiffuseColor(int R, int G, int B) {
		this.diffuse = new Color(R,G,B);
	}
	
	public Color getRGBDiffuseColor() {
		return this.diffuse;
	}
	
	public void setRGBAmbientColor(int R, int G, int B) {
		this.ambient = new Color(R,G,B);
	}
	
	public Color getRGBAmbientColor() {
		return this.ambient;
	}
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o)
	        return true;
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    Material mtl = (Material) o;
	    return mtl.getName().equals(this.name);
	}
	
	public String toMtl() {
		StringBuilder builder = new StringBuilder();
		builder.append("newmtl Colored\n");
		if(this.ambient != null) {
			builder.append("Ka "+this.ambient.getRed()/255+" "+this.ambient.getGreen()/255+" "+this.ambient.getBlue()/255+"\n");
		}
		if(this.diffuse != null) {
			builder.append("Kd "+this.diffuse.getRed()/255+" "+this.diffuse.getGreen()/255+" "+this.diffuse.getBlue()/255+"\n");
		}
		return builder.toString();
	}
	
	public String toObj() {
		return "usemtl "+this.name+"\n";
	}
}
