package com.jeffreyricker.osgi.builder.compiler;

import java.util.List;
import java.util.concurrent.Callable;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * The callable job that performs the work of compiling the classes.
 * 
 * @author ricker
 * 
 */
public interface CompilerJob extends Callable<List<Diagnostic<? extends JavaFileObject>>> {

}
