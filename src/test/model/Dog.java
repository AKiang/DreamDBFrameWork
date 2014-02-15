package test.model;


import dreamdb.entity.EntityBase;

public class Dog extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4033740051010882300L;
	
	private String name;
	private String password;
	private Long id;
	private int level;
	private double exp;
	public String getName() {
		System.out.println("___进来了");
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		System.out.println("++++");
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getExp() {
		return exp;
	}
	public void setExp(double exp) {
		this.exp = exp;
	}
	
}
