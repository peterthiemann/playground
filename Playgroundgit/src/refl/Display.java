package refl;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Map;

public class Display {
	private PrintStream out;
	private Map<Object,Integer> history;

	private Display(PrintStream out) {
		this.out = out;
		this.history = new IdentityHashMap<Object,Integer>();
	}
	
	public static void display(Object o) {
		display(System.out, o);
	}

	public static void display(PrintStream out, Object o) {
		new Display(out).display0(o);
	}

	private void display0(Object o) {
		if (o == null) {
			out.print("null");
		} else {
			Integer ref = history.get(o);
			if(ref == null) {
				Class<?> clazz = o.getClass();
				// TODO move this dispatch into a hash table
				if (clazz.equals(String.class)) {
					out.print('"');
					out.print(o);
					out.print('"');
				} else if (clazz.equals(Character.class)) {
					out.print('\'');
					out.print(o);
					out.print('\'');					
				} else if (clazz.equals(Integer.class) || clazz.equals(Boolean.class) || clazz.equals(Short.class) ||
						clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) ||
						clazz.equals(Float.class)) {
					out.print(o);
				} else {
					int count = history.size();
					history.put(o, count);
					out.print(count + ":");
					if(clazz.isArray()) {
						String leader = "[";
						int len = Array.getLength(o);
						for(int j=0; j<len; j++) {
							out.print(leader);
							display0(Array.get(o, j));
							leader = ", ";
						}
						out.print("]");
					} else {
						displayFields(o);
					}
				}
			} else {
				out.print("/*#" + ref + "#*/");
			}
		}
		out.println();
	}

	private void displayFields(Object o) {
		Class<?> clazz = o.getClass();
		out.print(clazz.getName());
		out.println("{");
		Field[] fields = clazz.getDeclaredFields(); // clazz.getFields();
		for(int i=0; i<fields.length; i++) {
			Field field = fields[i];
			Class<?> ftype = field.getType();
			String ftypename = ftype.getName(); 
			out.print(ftypename);
			out.print(' ');
			out.print(field.getName());
			out.print(" = ");
			try {
				display0(field.get(o));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				out.print("/*illegal access*/");
			}
			out.println(";");
		}
		out.print('}');
	}
}
