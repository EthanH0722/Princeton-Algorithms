import java.util.Arrays;
import java.util.ArrayList;
public class Board {
	
	
	private final int[][] tiles;
	private final int n;
	private final int hamming;
	private final int manhattan;
	
	// create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	n = tiles.length;
    	this.tiles = new int[n][n];
    	int hammingSum = 0;
    	int manhattanSum = 0;
    	
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			this.tiles[i][j] = tiles[i][j];
    			if (this.tiles[i][j] != 0) {
    				int targetPos = tiles[i][j]-1;
    				int nowPos = i * n + j;
    				
    				hammingSum += targetPos != nowPos ? 1:0;
    				int vertical = Math.abs(i - targetPos / n);
    				int horizontal = Math.abs(i - targetPos % n);
    				manhattanSum += vertical + horizontal;
    			}
    		}
    	}
    	hamming = hammingSum;
    	manhattan = manhattanSum;
    }
                                           
    // string representation of this board
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
    	return n;
    }

    // number of tiles out of place
    public int hamming() {
    	return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
    	if (y == null) {
    		return false;
    	}
    	if (this == y) {
    		return true;
    	}
    	if (y.getClass() != this.getClass()) {
            return false;
        }
        Board board = (Board) y;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
    	ArrayList<Board> neighbours = new ArrayList<>(); 
    	int x = 0;
    	int y = 0;
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			if (tiles[i][j] == 0) {
    				x = i;
    				y = j;
    			}
    		}
    	}
    	int[][] directions = { {-1 , 0} , {1 , 0} , {0 , 1} , {0 , -1} };
    	for (int[] direction : directions) {
    		int xx = x + direction[0];
    		int yy = y +direction[1];
    		if (valid(xx, yy)) {
    			neighbours.add(new Board(swap(x, y, xx, yy)));
    		}
    	}
    	return neighbours;
    }
    
    private boolean valid(int x, int y) {
    	return x >= 0 && x < n && y >= 0 && y < n;
    }
    
    private int[][] swap(int x, int y, int xx, int yy){
    	int[][] newTiles = new int[n][n];
    	for (int i =0; i < n; i++) {
    		System.arraycopy(tiles[i], 0, newTiles[i], 0, n);
    	}
    	int tmp = newTiles[x][y];
        newTiles[x][y] = newTiles[xx][yy];
        newTiles[xx][yy] = tmp;
        return newTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	Board b = null;
    	for (int i = 0; i < n * n - 1; i++) {
            int x = i / n;
            int y = i % n;
            int xx = (i + 1) / n;
            int yy = (i + 1) % n;
            if (tiles[x][y] != 0 && tiles[xx][yy] != 0) {
                b = new Board(swap(x, y, xx, yy));
                break;
            }
        }
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	int[][] t = {{1, 4, 3}, {2, 5, 7}, {0, 8, 6}};
        Board b = new Board(t);
        System.out.println(b.dimension());
        System.out.println(b);
//        System.out.println(b.hamming());
//        System.out.println(b.manhattan());
        System.out.println(b.isGoal());
//        System.out.println(b.twin());
//        System.out.println(b.equals(b.twin()));
        for (Board bb : b.neighbors()) {
            System.out.println(bb);
        }

    }
}
