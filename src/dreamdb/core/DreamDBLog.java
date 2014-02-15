package dreamdb.core;

public class DreamDBLog {
	public static void alert(String msg){
		StringBuilder builder=new StringBuilder("【");
		builder.append(msg);
		builder.append("】");
		System.out.println(builder);
	}
	
	public static void error(String msg){
		StringBuilder builder=new StringBuilder("【");
		builder.append(msg);
		builder.append("】");
		System.out.println(builder);
	}
	
	public static void sql(String msg){
		StringBuilder builder=new StringBuilder("【sql语句】:\n");
		builder.append(msg);
		builder.append("");
		System.out.println(builder);
	}
}
