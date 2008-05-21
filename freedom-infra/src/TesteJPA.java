import java.util.HashMap;
import java.util.Map;
import org.freedom.infra.model.jpa.Key;


public class TesteJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		
		m1.put("1",1);
		m1.put("2",2);
		
		m2.put("1",1);
		m2.put("2",2);
		
		Object[][] i = {{1,2,3,4},{1,2,3,4}};
		
		Key key1 = new Key( i );
		//Key key2 = new Key();
		
		System.out.println(key1.hashCode());
		//System.out.println(key2.hashCode());
		
		String st1 = new String("123");
		String st2 = new String("123");
		
		System.out.println(st1.hashCode());
		System.out.println(st2.hashCode());
		
		System.out.println(key1.getInternalKey().hashCode());
//		System.out.println(key2.getInternalKey().hashCode());


	}

}
