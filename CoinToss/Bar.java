// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA1
// Fall 2018

// we included the import statements for you
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Bar class
 * A labeled bar that can serve as a single bar in a bar graph.
 * The text for the label is centered under the bar.
 * 
 * NOTE: we have provided the public interface for this class. Do not change
 * the public interface. You can add private instance variables, constants,
 * and private methods to the class. You will also be completing the
 * implementation of the methods given.
 * 
 */
public class Bar {
   
	// All properties to create a labeled bar
	private int bottom;
	private int left;
	private int width;
	private int barHeight;
	private double scale;
	private Color color;
	private String label;

   /**
      Creates a labeled bar.  You give the height of the bar in application
      units (e.g., population of a particular state), and then a scale for how
      tall to display it on the screen (parameter scale). 
  
      @param bottom  location of the bottom of the label
      @param left  location of the left side of the bar
      @param width  width of the bar (in pixels)
      @param barHeight  height of the bar in application units
      @param scale  how many pixels per application unit
      @param color  the color of the bar
      @param label  the label at the bottom of the bar
   */
   public Bar(int bottom, int left, int width, int barHeight,
              double scale, Color color, String label) {

	   // Initializes all parameters
	   this.bottom = bottom;
	   this.left = left;
	   this.width = width;
	   this.barHeight = barHeight;
	   this.scale = scale;
	   this.color = color;
	   this.label = label;
	   
   }
   
   /**
      Draw the labeled bar. 
      @param g2  the graphics context
   */
   public void draw(Graphics2D g2) {

	   // Gets the object of pixel range to measure the width and height
	   // of the label
	   Font font = g2.getFont();
	   FontRenderContext context = g2.getFontRenderContext();
	   Rectangle2D labelBounds = font.getStringBounds(label, context);
	   
	   // Gets the width and height of the label
	   int labelWidth = (int)labelBounds.getWidth();
	   int labelHeight = (int)labelBounds.getHeight();
	   
	   // Computes the height of the bar in pixels and the y-coordinate of 
	   // the bar. Height in pixels = height in application units * pixels 
	   // per application unit. Therefore, y-coordinate = bottom - height of
	   // label - height of the bar (in pixels).
	   int barHeightInPx = (int)(barHeight * scale);
	   int barY = bottom - labelHeight - barHeightInPx;
	   // Computes the x-coordinate of the label. The x-coordinate makes each
	   // label centered under each bar.
	   int labelX = left + (width - labelWidth) / 2;
	   
	   // Creates a rectangle which represents one result
	   Rectangle bar = new Rectangle(left, barY , width , barHeightInPx);
	   // Creates a Color object to store the current color
	   Color c = g2.getColor();
	   
	   // Draws the bar with the corresponding color
	   g2.setColor(color);
	   g2.draw(bar);
	   g2.fillRect(left, barY, width , barHeightInPx);
	   // Draws the label with the original color.
	   g2.setColor(c);
	   g2.drawString(label, labelX, bottom);
	   
   }
   
}
