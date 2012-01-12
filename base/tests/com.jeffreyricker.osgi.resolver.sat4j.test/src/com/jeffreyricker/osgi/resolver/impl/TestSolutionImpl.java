package com.jeffreyricker.osgi.resolver.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.jeffreyricker.osgi.resolver.SolutionState;

public class TestSolutionImpl {
	
	@Test
	public void testConstructor() {
		SolutionImpl solution = new SolutionImpl(null,null);
		assertNotNull(solution.getDependencies());
		assertNotNull(solution.getReasons());
		assertEquals(SolutionState.Unresolved, solution.getState());
		
	}

}
