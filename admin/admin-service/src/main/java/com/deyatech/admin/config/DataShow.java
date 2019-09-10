package com.deyatech.admin.config;

import com.google.common.collect.Lists;

import java.util.List;

public class DataShow {

	private String id;
	
	private String name;
	
	private List<Validate> validate = Lists.newArrayList();
	
	private List<DataSource> dataSource = Lists.newArrayList();
	
	private List<DataLength> dataLengths = Lists.newArrayList();

	private List<ControlLength> controlLengths = Lists.newArrayList();
	
	private boolean mustValidate = false;

	/**
	 * 根据现有DataShow获取新的DataShow
	 * @return
	 */
	public DataShow newDataShow(){
		DataShow dataShow = new DataShow();
		dataShow.setId(this.id);
		dataShow.setName(this.name);
		dataShow.setDataSource(this.dataSource);
		dataShow.setDataLengths(this.dataLengths);
		dataShow.setControlLengths(this.controlLengths);
		return dataShow;
	}
	
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
	 * @return the validate
	 */
	public List<Validate> getValidate() {
		return validate;
	}

	/**
	 * @param validate the validate to set
	 */
	public void setValidate(List<Validate> validate) {
		this.validate = validate;
	}

	/**
	 * @return the dataSource
	 */
	public List<DataSource> getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(List<DataSource> dataSource) {
		this.dataSource = dataSource;
	}

	public List<DataLength> getDataLengths() {
		return dataLengths;
	}

	public void setDataLengths(List<DataLength> dataLengths) {
		this.dataLengths = dataLengths;
	}

	public List<ControlLength> getControlLengths() {
		return controlLengths;
	}

	public void setControlLengths(List<ControlLength> controlLengths) {
		this.controlLengths = controlLengths;
	}

	/**
	 * @return the mustValidate
	 */
	public boolean isMustValidate() {
		return mustValidate;
	}

	/**
	 * @param mustValidate the mustValidate to set
	 */
	public void setMustValidate(boolean mustValidate) {
		this.mustValidate = mustValidate;
	}
}
