package com.jeffreyricker.osgi.resolver.sat4j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.SolverFactory;
import org.sat4j.pb.tools.DependencyHelper;
import org.sat4j.pb.tools.WeightedObject;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.TimeoutException;

public class TestIPBSolver {
	
	@Test
	public void testSimpleResolver() throws ContradictionException, TimeoutException {
		IPBSolver solver = SolverFactory.newMiniLearningOPBClauseCardConstrMaxSpecificOrderIncrementalReductionToClause();
		DependencyHelper<Named, String> helper = new DependencyHelper<Named, String>(solver, false);
		Set<Named> slice = new HashSet<Named>();
		Named A1 = new Thing("A1");
		slice.add(A1);
		Named A2 = new Thing("A2");
		slice.add(A2);
		Named B = new Thing("B");
		slice.add(B);
		Named X = new Thing("X");
		// base
		helper.setTrue(X, "Build");
		// weighted
		List<WeightedObject<Named>> wos = new ArrayList<WeightedObject<Named>>();
		wos.add(WeightedObject.newWO(A1, 1));
		wos.add(WeightedObject.newWO(A2, 2));
		helper.setObjectiveFunction(wos.toArray(new WeightedObject[0]));
		wos.clear();
		wos.add(WeightedObject.newWO(B, 4));
		helper.setObjectiveFunction(wos.toArray(new WeightedObject[0]));
		// depends
		helper.or("a", X, new Named[] { A1, A2 });
		helper.or("b", X, new Named[] { B });
		// solve
		assertTrue(solver.isSatisfiable());
		IVec<Named> solution = helper.getSolution();
		assertNotNull(solution);
		assertEquals(3, solution.size());
		assertTrue(solution.contains(A1));
		assertTrue(solution.contains(B));
		assertTrue(solution.contains(X));
	}

}
