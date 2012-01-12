/**
 * 
 */
package com.jeffreyricker.osgi.resolver.sat4j;


import org.junit.Before;
import org.junit.Test;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.SolverFactory;
import org.sat4j.pb.tools.DependencyHelper;

/**
 * @author jricker
 * 
 */
public class TestBasic {

	IPBSolver solver;
	DependencyHelper<Named, String> helper;
	Thing A, B, C, D;

	@Before
	public void setup() {
		/*
		 * model
		 */
		A = new Thing("A");
		A.capabilities.add(new Cap("W"));
		B = new Thing("B");
		B.capabilities.add(new Cap("X"));
		B.requirements.add(new Req("Y"));
		C = new Thing("C");
		C.capabilities.add(new Cap("Y"));
		C.capabilities.add(new Cap("Z"));
		D = new Thing("D");
		D.capabilities.add(new Cap("W"));
		/*
		 * 
		 */
		solver = SolverFactory.newEclipseP2();
		helper = new DependencyHelper<Named, String>(solver, false);
		
	}

	@Test
	public void test() {
		Thing N = new Thing("N");
		N.requirements.add(new Req("W"));
		
		
	}

}
