import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
	private final LineSegment[] cached;
	public BruteCollinearPoints(Point[] points){
    	if (points == null ) {
    		throw new IllegalArgumentException();
    	}
    	this.points = Arrays.copyOf(points, points.length);
    	for (Point p : this.points) {
    		if (p == null) {
    			throw new IllegalArgumentException();
    		}
    	}
    	Arrays.sort(this.points);
    	for (int i = 0; i < this.points.length; i++) {
    		if (i > 0 && Double.compare(this.points[i].slopeTo( this.points[i - 1]), Double.NEGATIVE_INFINITY) == 0){
    			throw new IllegalArgumentException();
    		}
    	}
    	cached = cache();
    }
	
	private LineSegment[] cache() {
		ArrayList<LineSegment> list = new ArrayList<>();
		int n = points.length;
		
		for (int i = 0; i < n; i++) {
			for (int u = i+1; u < n; u++) {
				for (int y = u + 1; y < n; y++) {
					for (int t =y + 1; t < n; t++) {
						Point p = points[i];
						Point q = points[u];
						Point r = points[y];
						Point s = points[t];
						
						double slope1 = p.slopeTo(q);
						double slope2 = p.slopeTo(r);
						double slope3 = p.slopeTo(s);
						if (Double.compare(slope1, slope2) == 0 && Double.compare(slope2,slope3) == 0) {
							list.add(new LineSegment(p,s));
						}
					}
				}
			}
		}
		LineSegment[] segments = new LineSegment[list.size()];
        return list.toArray(segments);
	}
	
	public int numberOfSegments() {
		return cached.length;
	}
	
	public LineSegment[] segments() {
		return Arrays.copyOf(cached, cached.length);
	}
}
