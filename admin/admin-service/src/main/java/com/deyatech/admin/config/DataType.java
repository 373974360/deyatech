package com.deyatech.admin.config;

import com.google.common.collect.Lists;

import java.util.List;

public class DataType {
	
	private String id;
	
	private String name;
	
	private List<DataShow> dataShow = Lists.newArrayList();
	
	

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
	 * @return the dataShow
	 */
	public List<DataShow> getDataShow() {
		return dataShow;
	}

	/**
	 * @param dataShow the dataShow to set
	 */
	public void setDataShow(List<DataShow> dataShow) {
		this.dataShow = dataShow;
	}


}
