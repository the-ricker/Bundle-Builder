package com.jeffreyricker.osgi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.obr.Capability;



public class SimpleCapability implements Capability {
	private String m_name = null;
	private final Map<String, Object> m_map = new HashMap<String, Object>();
	private final List<Property> m_list = new ArrayList<Property>();

	public SimpleCapability() {
	}

	public SimpleCapability(String name) {
		setName(name);
	}

	public SimpleCapability(String name, SimpleProperty[] properties) {
		setName(name);
		for (int i = 0; properties != null && i < properties.length; i++) {
			addProperty(properties[i]);
		}
	}

	@Override
	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name.intern();
	}

	@Override
	public Map<String, Object> getProperties() {
		return m_map;
	}

	public Property[] getPropertiesSet() {
		return m_list.toArray(new Property[m_list.size()]);
	}

	public void addProperty(Property prop) {
		m_map.put(prop.getName().toLowerCase(), prop.getConvertedValue());
		m_list.add(prop);
	}

	public void addProperty(String name, String value) {
		addProperty(name, null, value);
	}

	public void addProperty(String name, String type, String value) {
		addProperty(new SimpleProperty(name, type, value));
	}

	@Override
	public String toString() {
		return m_name + ":" + m_map.toString();
	}
}