import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int[][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
	private static final int top = 0;
	private int bottom;
	private int opennum;
	private final int n;
	private boolean[][] openSpots;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf1;
	
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(" n must be larger than zero ");
		}
		this.n = n;
		openSpots = new boolean[n+1][n+1];
		bottom = n * n + 1;
		uf = new WeightedQuickUnionUF(bottom+1);
		uf1 = new WeightedQuickUnionUF(bottom+1);
	}
    public void open(int row, int col) {
    	if (isValid(row,col)) {
    		if (!openSpots[row][col]){
    			opennum++;
    			openSpots[row][col] = true;
    			int index = (row - 1) * n + col;
    			if (row == 1) {
    				uf.union(index, top);
    				uf1.union(index, top);
    			}
    			if (row == n) {
    				uf.union(index, bottom);
    			}
    		}	
    	    for (int[] ints : directions) {
    		    int newRow = row + ints[0];
    		    int newCol = col + ints[1];
    		    if (isValid(newRow, newCol) && isOpen(newRow, newCol)) {
    			    int indexP = (row - 1) * n + col;
    			    int indexQ = (newRow - 1) * n + newCol;
    			    uf.union(indexP, indexQ);
    			    uf1.union(indexP, indexQ);
    		    }
    	    }
    	}
    	else {
    		throw new IllegalArgumentException(" index of row and col must be larger than zero ");
    	}
    	   
    }
    private boolean isValid(int row, int col) {
    	return row >=1 && row <= n && col >= 1 && col <=n;
    }
    private boolean connectedWithTop(int row, int col) {
    	int unionindex = (row - 1) * n + col;
    	return uf1.find(top) == uf1.find(unionindex);
    }
    public boolean isOpen(int row, int col) {
    	if (isValid(row, col)) {
    		return openSpots[row][col];
    	}
    	else {
    		throw new IllegalArgumentException(" index > zero ");
    	}
    }
    public boolean isFull(int row, int col) {
    	if (isValid(row,col)) {
    		return isOpen(row, col) && connectedWithTop(row, col);
    	}
    	else {
    		throw new IllegalArgumentException();
    	}
    }
    public int numberOfOpenSites() {
    	return opennum;
    }
    public boolean percolates() {
    	return uf.find(top) == uf.find(bottom);
    }
    public static void main(String[] args) {
    	Percolation p;
    	p = new Percolation(2);
    	p.open(1,1);
    	p.open(1,2);
    	p.open(2,2);
    	assert p.percolates();
    	
    	p = new Percolation(3);
    	p.open(1,1);
    	p.open(1, 2);
        p.open(1, 3);
        p.open(2, 3);
        p.open(2, 2);
        p.open(3, 3);
        p.open(3, 1);
        assert !p.isFull(3, 1);
        assert p.percolates();
    }
}












