package ua.khpi.test.finalTask.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity  {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
