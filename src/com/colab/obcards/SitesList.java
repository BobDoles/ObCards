package com.colab.obcards;

import java.util.ArrayList;

/** Contains getter and setter method for varialbles  */
public class SitesList {

	/** Variables */
	private ArrayList<String> name = new ArrayList<String>();

	
	/** In Setter method default it will return arraylist 
	 *  change that to add  */
	
	public ArrayList<String> getName() {
		return name;
	}

	public void setName(String name) {
		this.name.add(name);
	}

}
