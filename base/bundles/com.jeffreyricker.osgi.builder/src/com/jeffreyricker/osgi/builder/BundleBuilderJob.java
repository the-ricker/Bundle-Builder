package com.jeffreyricker.osgi.builder;

import java.util.Set;
import java.util.concurrent.Callable;

public interface BundleBuilderJob extends Callable<Set<BuildResource>>{

}
