package fr.guedesite.wavefront.io;

import java.awt.Color;
import java.io.OutputStream;
import java.util.List;

import fr.guedesite.wavefront.Face;

/**
 * Writable things can be converted to the
 * Wavefront .obj file format and written to
 * an OutputStream.
 * 
 * @author Christian Garbs &lt;mitch@cgarbs.de&gt;
 * @since 0.6.0
 */
public interface Writable
{
	
	Color getColor();
	void setColor(Color col);
	
	/**
	 * Returns all Faces contained in the Writable.
	 * 
	 * @return all Faces of the Writable
	 * @since 0.6.0
	 */
	List<Face> faces();

	/**
	 * Writes the corresponding .obj file to
	 * an OutputStream.
	 * Does not close the stream after writing.
	 * 
	 * See file format description at
	 * https://en.wikipedia.org/wiki/Wavefront_.obj_file
	 * 
	 * @param os
	 *            the OutputStream to write to
	 * @since 0.1.0
	 */
	default public void write()
	{
		new ObjWriter(faces()) //
				.write(getColor());
	}
}
