package com.jeffreyricker.osgi.manifest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.osgi.framework.BundleException;

public class ManifestLoader {

	/**
	 * Will load a manifest from a jar file or a directory.
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Manifest load(File file) throws IOException {
		if (file.isDirectory()) {
			File mf = new File(file, JarFile.MANIFEST_NAME);
			if (!mf.exists()) {
				return null;
			}
			return new Manifest(new FileInputStream(mf));
		} else {
			JarFile jar = new JarFile(file);
			return jar.getManifest();
		}
	}
	
	@Deprecated
	public static Map<String, String> loadManifest(File file) throws IOException {
		InputStream manifest = null;
		if (file.isDirectory()) {
			File mf = new File(file, JarFile.MANIFEST_NAME);
			if (!mf.exists()) {
				return null;
			}
			manifest = new FileInputStream(mf);
		} else {
			JarFile jar = new JarFile(file);
			manifest = jar.getInputStream(jar.getEntry(JarFile.MANIFEST_NAME));
		}
		return loadManifest(manifest);
	}

	@Deprecated
	public static Map<String, String> loadManifest(URI uri) throws IOException {
		return loadManifest(uri.toURL());
	}
	
	@Deprecated
	public static Map<String, String> loadManifest(URL url) throws IOException {
		InputStream manifest = null;
		if (url.getFile().endsWith("/")) {
			manifest = new URL(url, JarFile.MANIFEST_NAME).openStream();
		} else {
			manifest = new URL("jar:" + url.toExternalForm() + "!/" + JarFile.MANIFEST_NAME).openStream();
		}
		return loadManifest(manifest);
	}

	@Deprecated
	public static Map<String, String> loadManifest(InputStream manifest) throws IOException {
		if (manifest == null) {
			return null;
		}
		Map<String, String> headers = new HashMap<String, String>();
		try {
			return parseBundleManifest(manifest, headers);
		} catch (BundleException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Parses a bundle manifest and puts the header/value pairs into the
	 * supplied Map. Only the main section of the manifest is parsed (up to the
	 * first blank line). All other sections are ignored. If a header is
	 * duplicated then only the last value is stored in the map.
	 * <p>
	 * The supplied input stream is consumed by this method and will be closed.
	 * If the supplied Map is null then a Map is created to put the header/value
	 * pairs into.
	 * </p>
	 * 
	 * @param manifest
	 *            an input stream for a bundle manifest.
	 * @param headers
	 *            a map used to put the header/value pairs from the bundle
	 *            manifest. This value may be null.
	 * @throws BundleException
	 *             if the manifest has an invalid syntax
	 * @throws IOException
	 *             if an error occurs while reading the manifest
	 * @return the map with the header/value pairs from the bundle manifest
	 */
	@Deprecated
	public static Map<String, String> parseBundleManifest(InputStream manifest, Map<String, String> headers)
			throws IOException, BundleException {
		if (headers == null)
			headers = new HashMap<String, String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(manifest, "UTF8")); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			br = new BufferedReader(new InputStreamReader(manifest));
		}
		try {
			String header = null;
			StringBuffer value = new StringBuffer(256);
			boolean firstLine = true;

			while (true) {
				String line = br.readLine();
				/*
				 * The java.util.jar classes in JDK 1.3 use the value of the
				 * last encountered manifest header. So we do the same to
				 * emulate this behavior. We no longer throw a BundleException
				 * for duplicate manifest headers.
				 */

				if ((line == null) || (line.length() == 0)) /* EOF or empty line */
				{
					if (!firstLine) /* flush last line */
					{
						headers.put(header, value.toString().trim());
					}
					break; /* done processing main attributes */
				}

				if (line.charAt(0) == ' ') /* continuation */
				{
					if (firstLine) /* if no previous line */
					{
						throw new BundleException("The manifest line has an invalid leading space '' '' character.",
								BundleException.MANIFEST_ERROR);
					}
					value.append(line.substring(1));
					continue;
				}

				if (!firstLine) {
					headers.put(header, value.toString().trim());
					value.setLength(0); /* clear StringBuffer */
				}

				int colon = line.indexOf(':');
				if (colon == -1) /* no colon */
				{
					throw new BundleException(
							"The manifest line is invalid; it has no colon '':'' character after the header key.",
							BundleException.MANIFEST_ERROR);
				}
				header = line.substring(0, colon).trim();
				value.append(line.substring(colon + 1));
				firstLine = false;
			}
		} finally {
			try {
				br.close();
			} catch (IOException ee) {
				// do nothing
			}
		}
		return headers;
	}

}
