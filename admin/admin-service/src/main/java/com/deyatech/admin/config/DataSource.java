package com.deyatech.admin.config;

import com.google.common.collect.Maps;

import java.util.Map;

public class DataSource {

	private String id;
	
	private String bean;
	
	private String name;
	
	Map<String,String> para = Maps.newLinkedHashMap();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the bean
	 */
	public String getBean() {
		return bean;
	}

	/**
	 * @param bean the bean to set
	 */
	public void setBean(String bean) {
		this.bean = bean;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the para
	 */
	public Map<String, String> getPara() {
		return para;
	}

	/**
	 * @param para the para to set
	 */
	public void setPara(Map<String, String> para) {
		this.para = para;
	}
}
