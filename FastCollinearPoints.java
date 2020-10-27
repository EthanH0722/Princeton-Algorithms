import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] cached;
	
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		this.points = Arrays.copyOf(points, points.length);
		for (Point point: this.points) {
			if (point == null ) {
				throw new IllegalArgumentException();
			}
		}
		Arrays.sort(this.points);
		for (int i = 0; i < this.points.length; i++) {
			if (i > 0 && Double.compare(this.points[i].slopeTo(this.points[i - 1]), Double.NEGATIVE_INFINITY) == 0) {
				throw new IllegalArgumentException();
			}
		}
		cached = cache();
	}
	
	private LineSegment[] cache() {
        ArrayList<LineSegment> list = new ArrayList<>();
        Arrays.sort(points);
        for (Point p: points) {
            Point[] pp = Arrays.copyOf(points, points.length);
            if (pp.length < 4) {
                continue;
            }
            Arrays.sort(pp, p.slopeOrder());
            int begin = 1;
            int end = 1;
            double last = p.slopeTo(pp[end]);
            for (int j = 2; j < pp.length; j++) {
                double slope = p.slopeTo(pp[j]);
                if (Double.compare(last, slope) != 0) {
                    if (end - begin >= 2) {
                        if (p.compareTo(pp[begin]) < 0) {
                            list.add(new LineSegment(p, pp[end]));
                        }
                    }
                    last = slope;
                    begin = j;
                }
                end = j;
            }
            if (end - begin >= 2) {
                if (p.compareTo(pp[begin]) < 0) {
                    list.add(new LineSegment(p, pp[end]));
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
