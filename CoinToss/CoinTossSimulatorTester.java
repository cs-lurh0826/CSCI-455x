// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA1
// Fall 2018

import java.util.Random;

/**
 * CoinTossSimulatorTester class
 * @author Lu,Ruihui
 * This class is used as a unit test
 */
public class CoinTossSimulatorTester {
	
	/**
	 * Shows the result of one test
	 * @param expNum the expected number of trials
	 * @param numTrials the actual number of trials
	 * @param twoHeads the times the result was two heads
	 * @param twoTails the times the result was two tails
	 * @param headTails the times the result was one of each
	 */
	private static void showTestResult(int expNum, int numTrials, int twoHeads, 
										int twoTails, int headTails) {
		
		System.out.println("Number of trials [exp:" + expNum + "]: " + numTrials);
		System.out.println("Two-head tosses: " + twoHeads);
		System.out.println("Two-tail tosses: " + twoTails);
		System.out.println("One-head one-tail tosses: " + headTails);
		System.out.println("Tosses add up correctly? " + 
							((numTrials == twoHeads + twoTails + headTails) && (expNum == numTrials)));
		System.out.println();
		
	}

	public static void main(String[] args) {
		
		int expNum = 0;
		int runNum;
		
		Random numberGenerator = new Random();
		CoinTossSimulator cts = new CoinTossSimulator();
		
		// Constructs
		System.out.println("After constructor:");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
						cts.getTwoTails(), cts.getHeadTails());
		
		// 1st add, the number of trials is generated at random (0 ~ 1000)
		runNum = numberGenerator.nextInt(1000);
		expNum += runNum;
		cts.run(runNum);
		System.out.println("After run(" + runNum + "):");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
						cts.getTwoTails(), cts.getHeadTails());
		
		// 2nd add
		runNum = numberGenerator.nextInt(1000);
		expNum += runNum;
		cts.run(runNum);
		System.out.println("After run(" + runNum + "):");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
						cts.getTwoTails(), cts.getHeadTails());
		
		// 3rd add
		runNum = numberGenerator.nextInt(1000);
		expNum += runNum;
		cts.run(runNum);
		System.out.println("After run(" + runNum + "):");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
						cts.getTwoTails(), cts.getHeadTails());
		
		// Resets
		cts.reset();
		expNum = 0;
		System.out.println("After reset:");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
				cts.getTwoTails(), cts.getHeadTails());
		
		// 1st add
		runNum = numberGenerator.nextInt(1000);
		expNum += runNum;
		cts.run(runNum);
		System.out.println("After run(" + runNum + "):");
		showTestResult(expNum, cts.getNumTrials(), cts.getTwoHeads(), 
						cts.getTwoTails(), cts.getHeadTails());
	}

}
