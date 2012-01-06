/**
 * 
 */
package com.jeffreyricker.osgi.builder.packager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import com.jeffreyricker.osgi.builder.packager.PackagerJob;
import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.manifest.ManifestUtil;

/**
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class PackagerJobImpl implements PackagerJob {

	private static int BUFFER_SIZE = 10240;
	private BundleSource source;
	private File targetDir;
	private byte[] buffer;

	public PackagerJobImpl(BundleSource source, File targetDir) {
		this.source = source;
		this.targetDir = targetDir;
	}

	@Override
	public File call() throws Exception {
		Manifest manifest = source.getManifest();
		String filename = ManifestUtil.createJarName(manifest);
		File file = new File(targetDir, filename);
		if (file.exists()) {
			file.delete();
		}
		JarOutputStream jar = new JarOutputStream(new FileOutputStream(file), manifest);
		File bin = source.getOutput();
		buffer = new byte[BUFFER_SIZE];
		// java classes
		addDirectory(jar, bin, "");
		// other items
		Map<String, File> bins = source.getBinIncludes();
		for (String path : bins.keySet()) {
			add(jar, bins.get(path), "");
		}
		jar.close();
		return file;
	}

	private void add(JarOutputStream jar, File file, String path) throws IOException {
		if (file.isFile()) {
			addFile(jar, file, path);
		} else if (file.isDirectory()) {
			addDirectory(jar, file, path + file.getName() + "/");
		}
	}

	private void addDirectory(JarOutputStream jar, File dir, String path) throws IOException {
		for (File file : dir.listFiles()) {
			add(jar, file, path);
		}
	}

	private void addFile(JarOutputStream jar, File file, String path) throws IOException {
		String name = path + file.getName();
		if (name.toUpperCase().equals(JarFile.MANIFEST_NAME)) {
			// do NOT overwrite the manifest by mistake
			return;
		}
		JarEntry entry = new JarEntry(name);
		entry.setTime(file.lastModified());
		jar.putNextEntry(entry);
		/*
		 * Write file to archive. TODO change this to nio channels
		 * http://www.javalobby.org/java/forums/t17036.html
		 */
		FileInputStream in = new FileInputStream(file);
		while (true) {
			int nRead = in.read(buffer, 0, buffer.length);
			if (nRead <= 0)
				break;
			jar.write(buffer, 0, nRead);
		}
		in.close();

	}

}
