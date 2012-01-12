/**
 * 
 */
package com.jeffreyricker.osgi.builder;

/**
 * 
 * The state of the build resource in the bundle build process.
 * 
 * @author Ricker
 */
public enum ResourceState {

	/**
	 * The dependencies are NOT set
	 */
	Unresolved,

	Resolving,

	FailedResolve,

	/**
	 * The dependencies are set
	 */
	Resolved,

	/**
	 * The compilation is currently running
	 */
	Compiling,

	/**
	 * The compilation is complete and failed
	 */
	FailedCompile,

	/**
	 * The compilation is complete and succeeded
	 */
	Compiled,

	Packaging,

	FailedPackaging,

	Built;

	/**
	 * The resource is still working
	 * 
	 * @return
	 */
	public boolean isWorking() {
		switch (this) {
		case Unresolved:
		case Resolving:
		case Resolved:
			// case Ready:
		case Compiling:
		case Compiled:
		case Packaging:
			return true;
		case FailedCompile:
		case FailedResolve:
		case FailedPackaging:
		case Built:
		default:
			return false;
		}
	}

	/**
	 * The resource has more steps
	 * 
	 * @return
	 */
	public boolean hasWork() {
		switch (this) {
		case Unresolved:
		case Resolving:
		case Resolved:
			// case Ready:
		case Compiling:
		case Compiled:
			return true;
		case Packaging:
		case FailedCompile:
		case FailedResolve:
		case FailedPackaging:
		case Built:
		default:
			return false;
		}
	}

	/**
	 * The resource has failed to build
	 * 
	 * @return
	 */
	public boolean isFailed() {
		switch (this) {
		case FailedCompile:
		case FailedResolve:
		case FailedPackaging:
			return true;
		case Unresolved:
		case Resolving:
		case Resolved:
			// case Ready:
		case Compiling:
		case Compiled:
		case Packaging:
		case Built:
		default:
			return false;
		}
	}

}
