package com.jeffreyricker.osgi.builder.impl;

import java.util.concurrent.Callable;

import com.jeffreyricker.osgi.builder.BuildResource;

public interface BuildJob extends Callable<BuildResource>{

}
