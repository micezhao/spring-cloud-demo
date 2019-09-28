package com.mock.mongodb;

import java.util.List;

public class Orders {
	
	private double id;
	
	private String item;
	
	private double price;
	
	private double quantity;
	
	private List<Object> specs;

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public List<Object> getSpecs() {
		return specs;
	}

	public void setSpecs(List<Object> specs) {
		this.specs = specs;
	}
	
	
	
	
}
