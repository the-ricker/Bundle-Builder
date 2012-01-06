/**
 * 
 */
package com.jeffreyricker.osgi.resolver.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resource;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.SolverFactory;
import org.sat4j.pb.tools.DependencyHelper;
import org.sat4j.pb.tools.WeightedObject;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;

import com.jeffreyricker.osgi.resolver.ResolverJob;
import com.jeffreyricker.osgi.resolver.Solution;
import com.jeffreyricker.osgi.resolver.util.ResourceSorter;
import com.jeffreyricker.osgi.resolver.util.Slicer;

/**
 * @author Ricker
 * 
 */
public class ResolverJobImpl implements ResolverJob {

	private Repository repository;
	private Resource resource;

	/**
	 * The subset of the repository that we are interested in
	 */
	// private Set<Resource> slice;
	// private SolutionImpl solution;
	// private DependencyHelper<Resource, String> helper;

	public ResolverJobImpl(Repository repository, Resource resource) {
		this.repository = repository;
		this.resource = resource;
	}

	@Override
	public Solution call() throws Exception {
		SolutionImpl solution = new SolutionImpl(repository, resource);
		solution.setState(Solution.State.Resolving);
		IPBSolver solver = SolverFactory
				.newMiniLearningOPBClauseCardConstrMaxSpecificOrderIncrementalReductionToClause();
		DependencyHelper<Resource, String> helper = new DependencyHelper<Resource, String>(solver, true);
		Set<Resource> slice = Slicer.slice(repository, resource);
		/*
		 * create the question
		 */
		try {
			helper.setTrue(resource, "Build");
			weighVersions(helper, slice);
			addRequires(helper, slice);
			addSingletons();
		} catch (ContradictionException e) {
			e.printStackTrace();
		}
		/*
		 * ask the question
		 */
		try {
			if (helper.hasASolution()) {
				IVec<Resource> solutionSet = helper.getSolution();
				System.out.println("Size " + solutionSet.size());
				Iterator<Resource> i = solutionSet.iterator();
				while (i.hasNext()) {
					solution.addDependency(i.next());
				}
				solution.setState(Solution.State.Satisfied);
			} else {
				solution.setState(Solution.State.Unsatisfied);
			}
		} catch (Exception e) {
			solution.setState(Solution.State.Unsatisfied);
		}
		return solution;
	}

	@SuppressWarnings("unchecked")
	private void weighVersions(DependencyHelper<Resource, String> helper, Set<Resource> slice) {
		List<Resource> list = new ArrayList<Resource>(slice);
		Collections.sort(list, new ResourceSorter());
		List<WeightedObject<Resource>> wos = new ArrayList<WeightedObject<Resource>>();
		int i = 1;
		for (Resource r : list) {
			wos.add(WeightedObject.newWO(r, 10 * i));
			i++;
		}
		helper.setObjectiveFunction(wos.toArray(new WeightedObject[] {}));
	}

	// @SuppressWarnings("unchecked")
	// private void weighVersionsByName(DependencyHelper<Resource, String>
	// helper, Set<Resource> slice) {
	// // group the versions
	// Map<String, List<Resource>> versions = new HashMap<String,
	// List<Resource>>();
	// for (Resource res : slice) {
	// List<Resource> list = versions.get(res.getSymbolicName());
	// if (list == null) {
	// list = new ArrayList<Resource>();
	// versions.put(res.getSymbolicName(), list);
	// }
	// list.add(res);
	// }
	// // sort the versions
	// int i = 1;
	// for (List<Resource> list : versions.values()) {
	// Collections.sort(list, new ResourceSorter());
	// List<WeightedObject<Resource>> wos = new
	// ArrayList<WeightedObject<Resource>>();
	// for (Resource r : list) {
	// wos.add(WeightedObject.newWO(r, 10 * i));
	// i++;
	// }
	// helper.setObjectiveFunction(wos.toArray(new WeightedObject[]{}));
	// }
	// }

	private void addSingletons() {
		// TODO
	}

	private void addRequires(DependencyHelper<Resource, String> helper, Set<Resource> slice)
			throws ContradictionException {
		HashSet<Resource> set = new HashSet<Resource>();
		for (Requirement req : resource.getRequirements()) {
			set.clear();
			for (Resource res : slice) {
				if (Slicer.match(req, res)) {
					set.add(res);
				}
			}
			if (set.size() > 0) {
				helper.or(req.getFilter().toString(), resource, set.toArray(new Resource[] {}));
			}
			// else {
			// solution.addReason(req.getName() + req.getFilter() +
			// " has no matches");
			// }
		}
	}

}
