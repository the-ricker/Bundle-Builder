/**
 * 
 */
package com.jeffreyricker.osgi.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.manifest.Attribute;
import com.jeffreyricker.osgi.manifest.Clause;
import com.jeffreyricker.osgi.manifest.Directive;
import com.jeffreyricker.osgi.manifest.Parser;
import com.jeffreyricker.osgi.manifest.VersionCleaner;
import com.jeffreyricker.osgi.manifest.VersionRange;

/**
 * Creates a build resource from a Manifest
 * @author Ricker
 * 
 */
public class ResourceLoader {
	
	public static final String BUNDLE_LICENSE = "Bundle-License";
	public static final String BUNDLE_SOURCE = "Bundle-Source";
	public static final String BUNDLE = "bundle";
	public static final String FRAGMENT = "fragment";
	public static final String PACKAGE = "package";
	public static final String SERVICE = "service";
	public static final String EXECUTIONENVIRONMENT = "ee";
	public static final String MANIFEST_VERSION = "manifestversion";
	
	public static void populate(SimpleResource resource, Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();
		Map<String, String> headers = new HashMap<String, String>(attributes.size());
		for (Object key : attributes.keySet()) {
			String v = attributes.getValue(key.toString());
			if (v != null) {
				headers.put(key.toString(),v);
			}
		}
		populate(resource, headers);
	}

	public static void populate(SimpleResource resource, Map<String, String> headers) {
		String bsn = getSymbolicName(headers);
		String v = getVersion(headers);
		resource.put(Resource.ID, bsn + "/" + v);
		resource.put(Resource.SYMBOLIC_NAME, bsn);
		resource.setVersion(v);
		if (headers.get(Constants.BUNDLE_NAME) != null) {
			resource.put(Resource.PRESENTATION_NAME, headers.get(Constants.BUNDLE_NAME));
		}
		if (headers.get(Constants.BUNDLE_DESCRIPTION) != null) {
			resource.put(Resource.DESCRIPTION, headers.get(Constants.BUNDLE_DESCRIPTION));
		}
		if (headers.get(BUNDLE_LICENSE) != null) {
			resource.put(Resource.LICENSE_URL, headers.get(BUNDLE_LICENSE));
		}
		if (headers.get(Constants.BUNDLE_COPYRIGHT) != null) {
			resource.put(Resource.COPYRIGHT, headers.get(Constants.BUNDLE_COPYRIGHT));
		}
		if (headers.get(Constants.BUNDLE_DOCURL) != null) {
			resource.put(Resource.DOCUMENTATION_URL, headers.get(Constants.BUNDLE_DOCURL));
		}
		if (headers.get(BUNDLE_SOURCE) != null) {
			resource.put(Resource.SOURCE_URL, headers.get(BUNDLE_SOURCE));
		}
		doCategories(resource, headers);
		doBundle(resource, headers);
		doFragment(resource, headers);
		doRequires(resource, headers);
		doExports(resource, headers);
		doImports(resource, headers);
		doExecutionEnvironment(resource, headers);
	}

	private static void doFragment(SimpleResource resource, Map<String, String> headers) {
		// Check if we are a fragment
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.FRAGMENT_HOST));
		if (clauses != null && clauses.length == 1) {
			// We are a fragment, create a requirement
			// to our host.
			SimpleRequirement r = new SimpleRequirement(BUNDLE);
			StringBuffer sb = new StringBuffer();
			sb.append("(&(symbolicname=");
			sb.append(clauses[0].getName());
			sb.append(")");
			appendVersion(sb,
					VersionRange.parseVersionRange(clauses[0].getAttribute(Constants.BUNDLE_VERSION_ATTRIBUTE)));
			sb.append(")");
			r.setFilter(sb.toString());
			r.addText("Required Host " + clauses[0].getName());
			r.setExtend(true);
			r.setOptional(false);
			r.setMultiple(false);
			resource.addRequire(r);

			// And insert a capability that we are available
			// as a fragment. ### Do we need that with extend?
			SimpleCapability capability = new SimpleCapability(FRAGMENT);
			capability.addProperty("host", clauses[0].getName());
			capability.addProperty("version", Property.VERSION, getVersion(clauses[0]));
			resource.addCapability(capability);
		}
	}

	private static void doRequires(SimpleResource resource, Map<String, String> headers) {
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.REQUIRE_BUNDLE));
		for (int i = 0; clauses != null && i < clauses.length; i++) {
			SimpleRequirement r = new SimpleRequirement(BUNDLE);
			VersionRange v = VersionRange
					.parseVersionRange(clauses[i].getAttribute(Constants.BUNDLE_VERSION_ATTRIBUTE));
			StringBuffer sb = new StringBuffer();
			sb.append("(&(symbolicname=");
			sb.append(clauses[i].getName());
			sb.append(")");
			appendVersion(sb, v);
			sb.append(")");
			r.setFilter(sb.toString());
			r.addText("Require Bundle " + clauses[i].getName() + "; " + v);
			r.setOptional(Constants.RESOLUTION_OPTIONAL.equalsIgnoreCase(clauses[i]
					.getDirective(Constants.RESOLUTION_DIRECTIVE)));
			resource.addRequire(r);
		}
	}

	private static void doBundle(SimpleResource resource, Map<String, String> headers) {
		SimpleCapability capability = new SimpleCapability(BUNDLE);
		capability.addProperty(Resource.SYMBOLIC_NAME, getSymbolicName(headers));
		if (headers.get(Constants.BUNDLE_NAME) != null) {
			capability.addProperty(Resource.PRESENTATION_NAME, headers.get(Constants.BUNDLE_NAME));
		}
		capability.addProperty(Resource.VERSION, Property.VERSION, getVersion(headers));
		capability.addProperty(MANIFEST_VERSION, getManifestVersion(headers));
		resource.addCapability(capability);
	}

	private static void doExports(SimpleResource resource, Map<String, String> headers) {
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.EXPORT_PACKAGE));
		for (int i = 0; clauses != null && i < clauses.length; i++) {
			SimpleCapability capability = createCapability(PACKAGE, clauses[i]);
			resource.addCapability(capability);
		}
	}

	private static void doCategories(SimpleResource resource, Map<String, String> headers) {
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.BUNDLE_CATEGORY));
		for (int i = 0; clauses != null && i < clauses.length; i++) {
			resource.addCategory(clauses[i].getName());
		}
	}

	private static void doImports(SimpleResource resource, Map<String, String> headers) {
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.IMPORT_PACKAGE));
		for (int i = 0; clauses != null && i < clauses.length; i++) {
			SimpleRequirement requirement = new SimpleRequirement(PACKAGE);

			createImportFilter(requirement, PACKAGE, clauses[i]);
			requirement.addText("Import package " + clauses[i]);
			requirement.setOptional(Constants.RESOLUTION_OPTIONAL.equalsIgnoreCase(clauses[i]
					.getDirective(Constants.RESOLUTION_DIRECTIVE)));
			resource.addRequire(requirement);
		}
	}

	private static void doExecutionEnvironment(SimpleResource resource, Map<String, String> headers) {
		Clause[] clauses = Parser.parseHeader(headers.get(Constants.BUNDLE_REQUIREDEXECUTIONENVIRONMENT));
		if (clauses != null && clauses.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(|");
			for (int i = 0; i < clauses.length; i++) {
				sb.append("(");
				sb.append(EXECUTIONENVIRONMENT);
				sb.append("=");
				sb.append(clauses[i].getName());
				sb.append(")");
			}
			sb.append(")");
			SimpleRequirement req = new SimpleRequirement(EXECUTIONENVIRONMENT);
			req.setFilter(sb.toString());
			req.addText("Execution Environment " + sb.toString());
			resource.addRequire(req);
		}
	}

	private static Set<String> doImportPackageAttributes(SimpleRequirement requirement, StringBuffer filter,
			Attribute[] attributes) {
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; attributes != null && i < attributes.length; i++) {
			String name = attributes[i].getName();
			String value = attributes[i].getValue();
			if (name.equalsIgnoreCase(Constants.VERSION_ATTRIBUTE)) {
				continue;
			} else if (name.equalsIgnoreCase(Constants.RESOLUTION_DIRECTIVE + ":")) {
				requirement.setOptional(Constants.RESOLUTION_OPTIONAL.equalsIgnoreCase(value));
			}
			if (name.endsWith(":")) {
				// Ignore
			} else {
				filter.append("(");
				filter.append(name);
				filter.append("=");
				filter.append(value);
				filter.append(")");
				set.add(name);
			}
		}
		return set;
	}

	private static String getVersion(Map<String, String> headers) {
		String v = headers.get(Constants.BUNDLE_VERSION);
		return VersionCleaner.clean(v);
	}

	private static String getVersion(Clause clause) {
		String v = clause.getAttribute(Constants.VERSION_ATTRIBUTE);
		if (v == null) {
			v = clause.getAttribute(Constants.BUNDLE_VERSION_ATTRIBUTE);
		}
		return VersionCleaner.clean(v);
	}

	private static VersionRange getVersionRange(Clause clause) {
		String v = clause.getAttribute(Constants.VERSION_ATTRIBUTE);
		if (v == null) {
			v = clause.getAttribute(Constants.BUNDLE_VERSION_ATTRIBUTE);
		}
		return VersionRange.parseVersionRange(v);
	}

	private static String getSymbolicName(Map<String, String> headers) {
		String bsn = headers.get(Constants.BUNDLE_SYMBOLICNAME);
		if (bsn == null) {
			bsn = headers.get(Constants.BUNDLE_NAME);
			if (bsn == null) {
				bsn = "Untitled-" + headers.hashCode();
			}
		}
		Clause[] clauses = Parser.parseHeader(bsn);
		return clauses[0].getName();
	}

	private static String getManifestVersion(Map<String, String> headers) {
		String v = headers.get(Constants.BUNDLE_MANIFESTVERSION);
		if (v == null) {
			v = "1";
		}
		return v;
	}

	private static SimpleCapability createCapability(String name, Clause clause) {
		SimpleCapability capability = new SimpleCapability(name);
		capability.addProperty(name, clause.getName());
		capability.addProperty(Resource.VERSION, Property.VERSION, getVersion(clause));
		Attribute[] attributes = clause.getAttributes();
		for (int i = 0; attributes != null && i < attributes.length; i++) {
			String key = attributes[i].getName();
			if (key.equalsIgnoreCase(Constants.VERSION_ATTRIBUTE)) {
				continue;
			} else {
				String value = attributes[i].getValue();
				capability.addProperty(key, value);
			}
		}
		Directive[] directives = clause.getDirectives();
		for (int i = 0; directives != null && i < directives.length; i++) {
			String key = directives[i].getName();
			String value = directives[i].getValue();
			capability.addProperty(key + ":", value);
		}
		return capability;
	}

	private static void createImportFilter(SimpleRequirement requirement, String name, Clause clause) {
		StringBuffer filter = new StringBuffer();
		filter.append("(&(");
		filter.append(name);
		filter.append("=");
		filter.append(clause.getName());
		filter.append(")");
		appendVersion(filter, getVersionRange(clause));
		Attribute[] attributes = clause.getAttributes();
		Set<String> attrs = doImportPackageAttributes(requirement, filter, attributes);
		// The next code is using the subset operator
		// to check mandatory attributes, it seems to be
		// impossible to rewrite. It must assert that whateber
		// is in mandatory: must be in any of the attributes.
		// This is a fundamental shortcoming of the filter language.
		if (attrs.size() > 0) {
			String del = "";
			filter.append("(mandatory:<*");
			for (Iterator<String> i = attrs.iterator(); i.hasNext();) {
				filter.append(del);
				filter.append(i.next());
				del = ", ";
			}
			filter.append(")");
		}
		filter.append(")");
		requirement.setFilter(filter.toString());
	}

	private static void appendVersion(StringBuffer filter, VersionRange version) {
		if (version != null) {
			if (!version.isOpenFloor()) {
				if (!Version.emptyVersion.equals(version.getFloor())) {
					filter.append("(");
					filter.append(Constants.VERSION_ATTRIBUTE);
					filter.append(">=");
					filter.append(version.getFloor());
					filter.append(")");
				}
			} else {
				filter.append("(!(");
				filter.append(Constants.VERSION_ATTRIBUTE);
				filter.append("<=");
				filter.append(version.getFloor());
				filter.append("))");
			}

			if (!VersionRange.INFINITE_VERSION.equals(version.getCeiling())) {
				if (!version.isOpenCeiling()) {
					filter.append("(");
					filter.append(Constants.VERSION_ATTRIBUTE);
					filter.append("<=");
					filter.append(version.getCeiling());
					filter.append(")");
				} else {
					filter.append("(!(");
					filter.append(Constants.VERSION_ATTRIBUTE);
					filter.append(">=");
					filter.append(version.getCeiling());
					filter.append("))");
				}
			}
		}
	}

}
