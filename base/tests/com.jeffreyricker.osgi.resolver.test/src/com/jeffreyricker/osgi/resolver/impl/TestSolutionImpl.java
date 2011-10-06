package com.jeffreyricker.osgi.resolver.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeffreyricker.osgi.resolver.Solution;

public class TestSolutionImpl {
	
	@Test
	public void testConstructor() {
		SolutionImpl solution = new SolutionImpl(null,null);
		assertNotNull(solution.getDependencies());
		assertNotNull(solution.getReasons());
		assertEquals(Solution.State.Unresolved, solution.getState());
		
	}

}
