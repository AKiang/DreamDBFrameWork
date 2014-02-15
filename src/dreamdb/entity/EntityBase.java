package dreamdb.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dreamdb.core.DreamDBLog;
import dreamdb.util.EnumUtil;

public abstract class EntityBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2175716918678531137L;
	private Map<String, Field> columns = new HashMap<String, Field>();

	public Map<String, Field> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, Field> columns) {
		this.columns = columns;
	}

	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean save() {
		StringBuilder sql = new StringBuilder("   INSERT INTO ");
		this.setTableName(this.getClass().getSimpleName());
		Field[] f = this.getClass().getDeclaredFields();
		
		sql.append(this.getTableName()+"(");
		for (Field field : f) {
			if (!field.getName().equals("serialVersionUID")){
				Method method;
					
					try {
						method = this.getClass().getMethod(this.getMethodName(EnumUtil.Method.get, field.getName()));
						Object value=method.invoke(this);
						if(value!=null){
							sql.append(field.getName()+", ");
						}
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		
		sql.append(") VALUES (");

		for (Field field : f) {
			if (!field.getName().equals("serialVersionUID")
					&& this.checkTypeIsBaseDataType(field.getType())) {
//				DreamDBLog.alert("columuName:" + field.getName() + " type:"
//						+ field.getType().getSimpleName());
				Method method;
				try {
					
					
					method = this.getClass().getMethod(this.getMethodName(EnumUtil.Method.get, field.getName()));
					Object value=method.invoke(this);
					if(value!=null){
						if(field.getType().getSimpleName().equals("String")){
							sql.append(" '"+method.invoke(this).toString()+"', ");
						}else {
							sql.append(" "+method.invoke(this).toString()+" , ");
						}
					}
					
					
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				this.getColumns().put(field.getName(), field);
			}
			// DreamDBLog.alert("columuName:" + field.getName() + " type:"
			// + field.getType().getSimpleName());
		}
		sql.append(");");
		DreamDBLog.sql(sql.toString());
		return false;
	}

	public boolean delete() {
		return false;
	}

	public <T> T load() {
		return (T) this;
	}

	public boolean update() {
		return false;
	}

	private boolean checkTypeIsBaseDataType(Class<?> clasz) {
		if (clasz.getName().equals("java.lang.String") || clasz == String.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Long") || clasz == Long.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Double") || clasz == Double.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Float") || clasz == Float.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Integer")
				|| clasz == Integer.class) {
			return true;
		}
		if (clasz.getName().equals("int") || clasz == int.class) {
			return true;
		}
		if (clasz.getName().equals("double") || clasz == double.class) {
			return true;
		}
		if (clasz.getName().equals("float") || clasz == float.class) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * CREATE TABLE if not exists `seclog050101` ( `Id` varchar(50) NOT NULL
	 * default '' COMMENT '主键', `MachineID` varchar(50) NOT NULL default ''
	 * COMMENT '终端 ID', `AddTime` datetime NOT NULL default '1900-01-01
	 * 00:00:00' COMMENT '生成时间', PRIMARY KEY (`Id`), KEY `mid` (`MachineID`),
	 * KEY `uid` (`UserId`) )
	 * 
	 * 
	 * */
	public boolean autoCreateTableToDB() {
		if (this.getTableName() == null) {
			DreamDBLog.error("获取不到实体类名");
			return false;
		}

		// 读取解析配置文件，看是什么数据库
		StringBuilder sql = new StringBuilder("CREATE TABLE if not exists '");
		if (1 == 1) {
			sql.append(this.getTableName());
			sql.append("' (\n '");
			sql.append(this.getPropertyValueByKey("id"));
			if (this.getPropertyValueByKey("id.generator").equals("uuuid")) {
				sql.append("' varchar(32) NOT NULL ,");
			} else {
				sql.append("' Integer NOT NULL  ,");
			}

			Collection<Field> c = columns.values();
			Iterator<Field> it = c.iterator();
			while (it.hasNext()) {
				sql.append("\n '");
				Field f = (Field) it.next();
				sql.append(f.getName());
				sql.append("'");
				sql.append(" ");
				sql.append(f.getType().getSimpleName());
				sql.append(" ,");
			}
			sql.append("PRIMARY KEY  ('");
			sql.append(this.getPropertyValueByKey("id"));
			sql.append("') \n);");
			DreamDBLog.sql(sql.toString());
		}

		return false;
	}

	/***
	 * 根据键名读取配置文件中的值
	 * 
	 * @param key
	 * @return value
	 */
	private String getPropertyValueByKey(String key) {
		return "good";
	}

	/**
	 * 反射调获取get/set方法
	 * 
	 * @param method
	 *            EnumUtil.Method.get/set
	 * @param attribute
	 * @return
	 */
	private String getMethodName(EnumUtil.Method method, String attribute) {
		String firstElemntOfAttribute = attribute.substring(0, 1).toUpperCase();
		String restElementOfAttrute = attribute.substring(1);
		return method.name() + firstElemntOfAttribute + restElementOfAttrute;
	}
}
