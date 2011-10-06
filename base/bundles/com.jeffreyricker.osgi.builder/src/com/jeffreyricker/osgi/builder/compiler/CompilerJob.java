package com.jeffreyricker.osgi.builder.compiler;

import java.util.List;
import java.util.concurrent.Callable;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public interface CompilerJob extends Callable<List<Diagnostic<? extends JavaFileObject>>>{

}
