package basics;

public class Person {
	
	public String name;
	public int age;
	public String gender;
	
	public String getGender() {
		return gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public void zeroAge() {
		age = 0;
	}
	
}
