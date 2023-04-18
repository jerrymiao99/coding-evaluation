package com.aa.act.interview.org;

import java.util.ArrayDeque;
import java.util.Optional;

public abstract class Organization {

	private Position root;

	public Organization() {
		root = createOrganization();
	}

	protected abstract Position createOrganization();

	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		ArrayDeque<Position> q = new ArrayDeque<>();
		q.addLast(root);
		// bfs to find and fill the position with the specified title
		while (!q.isEmpty()) {
			Position p = q.removeFirst();
			if (p.getTitle().equals(title)) {
				p.setEmployee(Optional.of(new Employee(person)));
				return Optional.of(p);
			} else {
				for (Position report : p.getDirectReports())
					q.addLast(report);
			}
		}
		// position not found
		return Optional.empty();
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}

	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for (Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
