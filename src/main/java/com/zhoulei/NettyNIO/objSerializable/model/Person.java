package com.zhoulei.NettyNIO.objSerializable.model;

import java.util.Date;

public class Person implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Date hir;
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
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the hir
	 */
	public Date getHir() {
		return hir;
	}
	/**
	 * @param hir the hir to set
	 */
	public void setHir(Date hir) {
		this.hir = hir;
	}
	@Override
	public String toString() {
		return "name:"+name+","+"age:"+age+"hr:"+hir;
	}
	

}
