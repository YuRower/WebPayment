package ua.khpi.test.finalTask.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

	private int id;

	public AbstractEntity(int id) {
		this.id = id;
	}

	public AbstractEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
