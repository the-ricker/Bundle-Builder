package com.jeffreyricker.osgi.repository;

import java.util.regex.Pattern;

import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.obr.Capability;
import org.osgi.service.obr.Requirement;

public class SimpleRequirement implements Requirement {

	private static final Pattern REMOVE_LT = Pattern
			.compile("\\(([^<>=~()]*)<([^*=]([^\\\\\\*\\(\\)]|\\\\|\\*|\\(|\\))*)\\)");
	private static final Pattern REMOVE_GT = Pattern
			.compile("\\(([^<>=~()]*)>([^*=]([^\\\\\\*\\(\\)]|\\\\|\\*|\\(|\\))*)\\)");
	private static final Pattern REMOVE_NV = Pattern.compile("\\(version>=0.0.0\\)");

	private String m_name = null;
	private boolean m_extend = false;
	private boolean m_multiple = false;
	private boolean m_optional = false;
	private Filter m_filter = null;
	private String m_comment = null;

	public SimpleRequirement() {
	}

	public SimpleRequirement(String name) {
		setName(name);
	}

	@Override
	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		/*
		 * Name of capabilities and requirements are interned for performances
		 * (with a very low memory consumption as there are only a handful of
		 * values)
		 */
		m_name = name.intern();
	}

	@Override
	public String getFilter() {
		return m_filter.toString();
	}

	public void setFilter(String filter) {
		try {
			String nf = REMOVE_LT.matcher(filter).replaceAll("(!($1>=$2))");
			nf = REMOVE_GT.matcher(nf).replaceAll("(!($1<=$2))");
			nf = REMOVE_NV.matcher(nf).replaceAll("");
			m_filter =  FrameworkUtil.createFilter(nf); 
		} catch (InvalidSyntaxException e) {
			IllegalArgumentException ex = new IllegalArgumentException();
			ex.initCause(e);
			throw ex;
		}
	}

	@Override
	public boolean isSatisfied(Capability capability) {
		return m_name.equals(capability.getName())
				&& m_filter.matches(capability.getProperties())
				&& (m_filter.toString().indexOf("(mandatory:<*") >= 0 || capability.getProperties().get("mandatory:") == null);
	}

	@Override
	public boolean isExtend() {
		return m_extend;
	}

	public void setExtend(boolean extend) {
		m_extend = extend;
	}

	@Override
	public boolean isMultiple() {
		return m_multiple;
	}

	public void setMultiple(boolean multiple) {
		m_multiple = multiple;
	}

	@Override
	public boolean isOptional() {
		return m_optional;
	}

	public void setOptional(boolean optional) {
		m_optional = optional;
	}

	@Override
	public String getComment() {
		return m_comment;
	}

	public void addText(String s) {
		m_comment = s;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Requirement) {
			Requirement r = (Requirement) o;
			return m_name.equals(r.getName()) && (m_optional == r.isOptional()) && (m_multiple == r.isMultiple())
					&& m_filter.toString().equals(r.getFilter())
					&& ((m_comment == r.getComment()) || ((m_comment != null) && (m_comment.equals(r.getComment()))));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return m_filter.toString().hashCode();
	}

	@Override
	public String toString() {
		return m_name + ":" + getFilter();
	}
}