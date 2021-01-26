package najeeb.facilitysolver;

public class Customer {

	int demand;
	double x;
	double y;

	public Customer(int demand, double x, double y) {
		this.demand = demand;
		this.x = x;
		this.y = y;
	}

	public Customer(String demand, String x, String y) {
		this.demand = Integer.parseInt(demand);
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
	}

}
