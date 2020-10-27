import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double confidence = 1.96;
    private final double[] result;
    private final double mean;
    private final double stddev;
    
    public PercolationStats(int n, int trials) {
    	if (!isValid(n,trials)) {
    		throw new IllegalArgumentException();
    	}
    	result = new double[trials];
    	for (int i = 0; i < trials; i++) {
    		Percolation p = new Percolation(n);
    		while(!p.percolates()) {
    			int newcol = StdRandom.uniform(1, n+1);
    			int newrow = StdRandom.uniform(1, n+1);
    			p.open(newrow, newcol);
    		}
    	result[i] = (double) (p.numberOfOpenSites())/ (double) (n*n);
    	}
        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
    }
    
    private boolean isValid(int n, int trials) {
    	return n > 0 && trials > 0;
    }
    
    public double mean() {
    	return mean;
    }
    
    public double stddev() {
    	return stddev;
    }
    
    public double confidenceLo() {
    	return mean - confidence * stddev / Math.sqrt(result.length);
    }
    
    public double confidenceHi() {
    	return mean + confidence * stddev / Math.sqrt(result.length);
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean=" + ps.mean());
        System.out.println("stddev=" + ps.stddev());
        System.out.println("95% confidence interval=[" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
    }

}


