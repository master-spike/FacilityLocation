package najeeb.facilitysolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.exit(0);
		}
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(new File(args[0])));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		String[] firstline = sc.nextLine().split(" ");
		facilities = new Facility[Integer.parseInt(firstline[0])];
		customers = new Customer[Integer.parseInt(firstline[1])];
		for (int i = 0; i < facilities.length; i++) {
			String[] thisline = sc.nextLine().split(" ");
			facilities[i] = new Facility(thisline[0], thisline[1], thisline[2], thisline[3]);
			if (facilities[i].x > max_x)
				max_x = facilities[i].x;
			if (facilities[i].y > max_y)
				max_y = facilities[i].y;
			if (facilities[i].x < min_x)
				min_x = facilities[i].x;
			if (facilities[i].y < max_y)
				min_y = facilities[i].y;
		}
		for (int i = 0; i < customers.length; i++) {
			String[] thisline = sc.nextLine().split(" ");
			customers[i] = new Customer(thisline[0], thisline[1], thisline[2]);
			if (customers[i].x > max_x)
				max_x = customers[i].x;
			if (customers[i].y > max_y)
				max_y = customers[i].y;
			if (customers[i].x < min_x)
				min_x = customers[i].x;
			if (customers[i].y < max_y)
				min_y = customers[i].y;
		}

		radius = Math.sqrt((max_y - min_y) * (max_y - min_y) + (max_x - min_x) * (max_x - min_x));

		int[] order = genIdentityPermutation(customers.length);
		for (int i = 0; i < customers.length; i++) {
			int next = i;
			for (int j = i + 1; j < customers.length; j++)
				if (customers[order[j]].demand < customers[order[next]].demand)
					next = j;
			int swp = order[next];
			order[next] = order[i];
			order[i] = swp;
		}
		customer_order_demand = order;
		sc.close();

		FacilityLocalSearch best_solution = null;
		long t0 = System.currentTimeMillis();
		// Do this on repeat for 10 minutes at least
		while (System.currentTimeMillis() - t0 < 600 * 1000) {
			FacilityLocalSearch solver = new FacilityLocalSearch(customers, facilities);
			solver.solve();
			if (best_solution == null) {
				best_solution = solver;
			}
			if (solver.objectiveFunction() < best_solution.objectiveFunction()) {
				best_solution = solver;
			}
		}


		System.out.println(best_solution.objectiveFunction() + " 0");
		for (int i = 0; i < best_solution.assignment.length; i++) {
			System.out.print(best_solution.assignment[i] + " ");
		}
		for (int i = 0; i < facilities.length; i++) {
			if (best_solution.used_caps[i] > facilities[i].cap) {
				System.out.println("Facility " + i + " over cap by " + best_solution.used_caps[i] + "/" + facilities[i].cap);
			}
		}

	}

	static Facility[] facilities;
	static Customer[] customers;
	static int[] customer_order_demand;

	static int[] genRandomPermutation(int len) {
		int[] out = genIdentityPermutation(len);
		Random random = new Random();
		for (int i = len - 1; i > 0; i--) {
			int n = random.nextInt(i + 1);
			int swp = out[i];
			out[i] = out[n];
			out[n] = swp;
		}
		return out;
	}

	static double radius;

	static double max_x = Double.NEGATIVE_INFINITY;
	static double max_y = Double.NEGATIVE_INFINITY;
	static double min_x = Double.POSITIVE_INFINITY;
	static double min_y = Double.POSITIVE_INFINITY;

	static int[] genIdentityPermutation(int len) {
		int[] out = new int[len];
		for (int i = 0; i < len; i++) {
			out[i] = i;
		}
		return out;
	}

}
