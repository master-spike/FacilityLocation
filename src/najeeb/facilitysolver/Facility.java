package najeeb.facilitysolver;

public class Facility {

	double x;
	double y;
	double cost;
	int cap;

	public Facility(double cost, int cap, double x, double y) {
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.cap = cap;
	}

	public Facility(String cost, String cap, String x, String y) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
		this.cost = Double.parseDouble(cost);
		this.cap = Integer.parseInt(cap);
	}

}
