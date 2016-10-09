package com.zhoulei.NettyNIO.marshalling.model;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String name;
	private int age;
	private List<Student> list = new ArrayList<Student>();
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
	 * @return the list
	 */
	public List<Student> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<Student> list) {
		this.list = list;
	}
	

}
