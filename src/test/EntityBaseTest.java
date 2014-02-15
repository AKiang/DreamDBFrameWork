package test;

import test.model.Dog;

public class EntityBaseTest {
	public static void main(String[] args) {
		Dog dog=new Dog();//entity
		dog.setName("big yellow dog");
		dog.setPassword("admin");
		if(dog.save()){
			alert("保存成功");
		}else {
			alert("数据异常");
		}
		dog.autoCreateTableToDB();
	}
	
	
	public static void alert(String msg){
		System.out.println(msg);
	}
}
