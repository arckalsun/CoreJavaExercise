package imageViewer;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * A bean for viewing an image
 * @author john
 *
 */
public class ImageViewerBean extends JLabel {

	private Path path = null;
	private static final int XPREFSIZE = 200;
	private static final int YPREFSIZE = 200;
	
	
	public ImageViewerBean()
	{
		setBorder(BorderFactory.createEtchedBorder());
	}
	/**
	 * Sets the fileName property.
	 * @param fileName the image file name
	 */
	public void setFileName(String fileName)
	{
		path = Paths.get(fileName);
		try (InputStream in = Files.newInputStream(path))
		{
			setIcon(new ImageIcon(ImageIO.read(in)));
			
		}
		catch (IOException e)
		{
			path = null;
			setIcon(null);
		}
		
	}
	
	/**
	 * Gets the fileName property.
	 * @return the image file name
	 */
	public String getFileName()
	{
		if (path ==null) return "";
		else return path.toString();
	}
	
	public Dimension getPreferedSize()
	{
		return new Dimension(XPREFSIZE, YPREFSIZE);
	}
	
	
}


