package com.jeffreyricker.osgi.manifest;

import java.util.jar.Manifest;

import org.osgi.framework.Constants;
import org.osgi.framework.Version;

public class ManifestUtil {

	public enum VersionComponent {
		MAJOR, MINOR, MICRO, QUALIFIER;
	}

	public static final String QUALIFIER = "qualifier";

	/**
	 * 
	 * @param manifest
	 * @param qualifier
	 */
	public static void qualifyVersion(Manifest manifest, String qualifier) {
		if (manifest == null || qualifier == null) {
			return;
		}
		String version = manifest.getMainAttributes().getValue(Constants.BUNDLE_VERSION);
		if (version != null && version.contains(QUALIFIER)) {
			version = version.replace(QUALIFIER, qualifier);
			manifest.getMainAttributes().putValue(Constants.BUNDLE_VERSION, version);
		}
	}

	public static Version getVersion(Manifest manifest) {
		String version = manifest.getMainAttributes().getValue(Constants.BUNDLE_VERSION);
		return new Version(version);
	}

	public static String getBundleSymbolicName(Manifest manifest) {
		return manifest.getMainAttributes().getValue(Constants.BUNDLE_SYMBOLICNAME);
	}
	
	public static void bumpVersion(Manifest manifest, VersionComponent component) {
		Version version = getVersion(manifest);
		switch (component) {
		case MAJOR:
			version = new Version(version.getMajor() + 1, version.getMinor(), version.getMicro(),
					version.getQualifier());
			break;
		case MINOR:
			version = new Version(version.getMajor(), version.getMinor() + 1, version.getMicro(),
					version.getQualifier());
			break;
		case MICRO:
			version = new Version(version.getMajor(), version.getMinor(), version.getMicro() + 1,
					version.getQualifier());
			break;
		}
		manifest.getMainAttributes().putValue(Constants.BUNDLE_VERSION, version.toString());
	}

	/**
	 * You should call {@link #qualifyVersion} before calling this method. 
	 * @param manifest
	 * @return
	 */
	public static String createJarName(Manifest manifest) {
		StringBuilder buf = new StringBuilder();
		buf.append(manifest.getMainAttributes().getValue(Constants.BUNDLE_SYMBOLICNAME));
		buf.append("_");
		buf.append(manifest.getMainAttributes().getValue(Constants.BUNDLE_VERSION));
		buf.append(".jar");
		return buf.toString();
	}

}
