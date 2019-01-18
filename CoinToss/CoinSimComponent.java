// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA1
// Fall 2018

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * CoinSimComponent class
 * @author Lu,Ruihui
 * This component draws three labeled bars.
 * Each bar represents one result including two heads, two tails, and one of each
 */
public class CoinSimComponent extends JComponent {

	// The number of trials
	private int numTrials;
	// The number of times each result came out
	private int twoHeads;
	private int twoTails;
	private int headTails;
	// The object of trials
	private CoinTossSimulator cts;
	// The fixed value of vertical buffer 
	private static final int VERTICAL_BUFFER = 25;
	// The fixed value of bar width
	private static final int BAR_WIDTH = 50;
	// The label height is approximately 15.0
	private static final double HEIGHT_OF_LABEL = 15.0;
	// Three colors of three bars
	private static final Color TWO_HEADS_COLOR = Color.RED;
	private static final Color HEAD_TAIL_COLOR = Color.GREEN;
	private static final Color TWO_TAILS_COLOR = Color.BLUE;
	
	/**
	 * Constructs a CoinSimComponent with the data of trials
	 * @param numTrials the number of trials
	 */
	public CoinSimComponent(int numTrials) {
		
		cts = new CoinTossSimulator();
		// Tosses two coins (numTrials times)
		cts.run(numTrials);
		
		this.numTrials = cts.getNumTrials();
		this.twoHeads = cts.getTwoHeads();
		this.headTails = cts.getHeadTails();
		this.twoTails = cts.getTwoTails();
		
	}
	
	/**
	 * Draws three labeled bars.
	 */
	public void paintComponent(Graphics g) {
	      
		Graphics2D g2 = (Graphics2D) g;

		// Computes how many pixels per application unit and bottom
		// The label height is approximately 15.0
		double scale = (getHeight() - 2 * VERTICAL_BUFFER - HEIGHT_OF_LABEL) / numTrials;
		int bottom = getHeight() - VERTICAL_BUFFER;
		
		// Computes the width between each bar, and between the end bars and each
		// side of the window. Then, we can get the x-coordinate of each bar.
		int width = (getWidth() - 3 * BAR_WIDTH) / 4;
		int xOfTwoHeads = width;
		int xOfHeadTails = 2 * width + BAR_WIDTH;
		int xOfTwoTails = 3 * width + 2 * BAR_WIDTH;
		
		// Computes the percent of each result
		int percentOfTwoHeads = (int) Math.round(twoHeads * 100.0 / numTrials);
		int percentOfHeadTails = (int) Math.round(headTails * 100.0 / numTrials);
		int percentOfTwoTails = (int) Math.round(twoTails * 100.0 / numTrials);
		
		// Creates three labels
		String labelOfTwoHeads = "Two Heads: " + twoHeads + " (" + percentOfTwoHeads + "%)";
		String labelOfHeadTails = "A Head and a tail: " + headTails + " (" + percentOfHeadTails + "%)";
		String labelOfTwoTails = "Two Tails: " + twoTails + " (" + percentOfTwoTails + "%)";
		
		// Creates three labeled bar with three different colors
		Bar barOfTwoHeads = new Bar(bottom, xOfTwoHeads, BAR_WIDTH, twoHeads, scale, 
									TWO_HEADS_COLOR, labelOfTwoHeads);
		Bar barOfHeadTails = new Bar(bottom, xOfHeadTails, BAR_WIDTH, headTails, scale, 
									HEAD_TAIL_COLOR, labelOfHeadTails);
		Bar barOfTwoTails = new Bar(bottom, xOfTwoTails, BAR_WIDTH, twoTails, scale, 
									TWO_TAILS_COLOR, labelOfTwoTails);
		
		barOfTwoHeads.draw(g2);
		barOfHeadTails.draw(g2);
		barOfTwoTails.draw(g2);
		
	}
}
