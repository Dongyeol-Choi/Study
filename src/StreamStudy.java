import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamStudy {
	
	static Stream<String> fruits = Stream.of("banana", "apple", "mango", "kiwi", "peach", "cherry", "lemon");
	
	static class Fruit {
	    public String id;
	    public String name;

	    Fruit(String id, String name) {
	        this.id = id;
	        this.name = name;
	    }

	    public String getId() {
	        return id;
	    }
	    public String getName() {
	        return name;
	    }
	}
	
	//Collectors�� �Բ� collect�� HashSet ��ü�� ����� �����Դϴ�. ���ڿ� toSet()�� �־��ָ� Set �ڷ���
    public static void streamTest() {
    	Set<String> fruitSet = fruits.collect(Collectors.toSet());
    	for (String s : fruitSet) {
    	    System.out.println(s);
    	}
    }
    
    //Collectors�� �̿��Ͽ� ��Ʈ���� �����۵��� List ��ü�� ����� �����Դϴ�. ���ڿ� toList()�� �־��ָ� List �ڷ���
    public static void streamTest1() {
    	List<String> fruitList = fruits.collect(Collectors.toList());
    	for (String s : fruitList) {
    	    System.out.println(s);
    	}
    }
    
    //��Ʈ���� ��� �����۵��� 1���� String
    public static void streamTest2() {
    	String result = fruits.collect(Collectors.joining());
    	System.out.println(result);
    }
    
    //string ��ü ���� �����ڸ� �־��ְ� �ʹٸ� Collectors.joining(", ")ó�� param���� �����ڸ� ����
    public static void streamTest3() {
    	String result = fruits.map(Object::toString).collect(Collectors.joining(", "));
    	System.out.println(result);
    }
    
    //���ڿ��� ���̷� �����۵��� ���ؼ�, ���� �� ���ڿ��� �����ϴ� ����, ���� Ÿ���� Optional, �� �Լ�(Comparator)
    public static void streamTest4() {
    	Function<String, Integer> getCount = fruit-> fruit.length();
    	Optional<String> result = fruits.map(Object::toString).collect(maxBy(comparing(getCount)));
    	System.out.println("result: " + result.orElse("no item"));
    }
    
    //Collectors�� ��Ʈ�� �����۵��� ��� ���� �����ִ� ���
    public static void streamTest5() {
    	List<Integer> list = Arrays.asList(1,2,3,4);
    	Double result = list.stream().collect(Collectors.averagingInt(v -> v*1));
    	System.out.println("Average: "+result);
    }
    
    //Custom ��ü�� ���ؼ��� Collect�� �� ���� �ֽ��ϴ�. Collectors.toMap()�� ���
    public static void streamTest6() {
    	Stream<Fruit> fruits2 = Stream.of(new Fruit("1", "banana"), new Fruit("2", "apple"),
    	        new Fruit("3", "mango"), new Fruit("4", "kiwi"),
    	        new Fruit("5", "peach"), new Fruit("6", "cherry"),
    	        new Fruit("7", "lemon"));
    	Map<String, String> map = fruits2.collect(Collectors.toMap(Fruit::getId, Fruit::getName));
    	for (String key : map.keySet()) {
    	    System.out.println("key: " + key + ", value: " + map.get(key));
    	}
    }
    
    //toMap() ���� ��, ������ key�� ���� �ִ� �����͸� ������ IllegalStateException�� �߻���ŵ�ϴ�.
    //�̷� ��� ����ó���� �� �� �ֽ��ϴ�. 3��° param����, �����ϴ� ���� ���ο� ���� �� � ���� �������� �����ؾ� �մϴ�.
    //�Ʒ� �ڵ�� ������ ��ϵ� ���� ����ϵ��� (existingValue, newValue) -> existingValue)�� ���� �߽��ϴ�. (�ڵ带 ���� key 5�� ���� �ִ� �����Ͱ� �ΰ� �ֽ��ϴ�.)
    //key 5�� ��� peach�� lemon�� �ִµ�, peach�� ���� ��ϵǾ��� ������ peach�� map�� ����
    public static void streamTest7() {
    	Stream<Fruit> fruits2 = Stream.of(new Fruit("1", "banana"), new Fruit("2", "apple"),
    	        new Fruit("3", "mango"), new Fruit("4", "kiwi"),
    	        new Fruit("5", "peach"), new Fruit("6", "cherry"),
    	        new Fruit("5", "lemon"));
    	Map<String, String> map = fruits2.collect(
    	    Collectors.toMap(item -> item.getId(), item -> item.getName(),
    	            (existingValue, newValue) -> existingValue));
    	for (String key : map.keySet()) {
    	    System.out.println("key: " + key + ", value: " + map.get(key));
    	}
    }
    
    //���� key�� ���� �ִ� �����Ͱ� ���� ��, �ΰ��� ���� ���Ͽ� �����ϵ��� ����
    //�ΰ��� ���� ���� ���� �����ϴ� �Լ��� ����
    public static void streamTest8() {
    	Stream<Fruit> fruits2 = Stream.of(new Fruit("1", "banana"), new Fruit("2", "apple"),
    	        new Fruit("3", "mango"), new Fruit("4", "kiwi"),
    	        new Fruit("5", "peach"), new Fruit("6", "cherry"),
    	        new Fruit("5", "lemon"));
    	Map<String, String> map = fruits2.collect(
    	        Collectors.toMap(item -> item.getId(), item -> item.getName(),
    	                (existingValue, newValue) -> {
    	                    String concat = existingValue + ", " + newValue;
    	                    return concat;
    	                }));
    	for (String key : map.keySet()) {
    	    System.out.println("key: " + key + ", value: " + map.get(key));
    	}
    }
    
    public static void main(String[] args) {
    	
        //Stream�׽�Ʈ
    	
        //Collectors�� �Բ� collect�� HashSet ��ü�� ����� �����Դϴ�. ���ڿ� toSet()�� �־��ָ� Set �ڷ���
        //streamTest();
    	
        //Collectors�� �̿��Ͽ� ��Ʈ���� �����۵��� List ��ü�� ����� �����Դϴ�. ���ڿ� toList()�� �־��ָ� List �ڷ���
        //streamTest1();
    	
        //��Ʈ���� ��� �����۵��� 1���� String
        //streamTest2();
    	
        //string ��ü ���� �����ڸ� �־��ְ� �ʹٸ� Collectors.joining(", ")ó�� param���� �����ڸ� ����
        //streamTest3();
    	
        //���ڿ��� ���̷� �����۵��� ���ؼ�, ���� �� ���ڿ��� �����ϴ� ����, ���� Ÿ���� Optional, �� �Լ�(Comparator)
        //streamTest4();
    	
        //Collectors�� ��Ʈ�� �����۵��� ��� ���� �����ִ� ���
        //streamTest5();
    	
        //Custom ��ü�� ���ؼ��� Collect�� �� ���� �ֽ��ϴ�. Collectors.toMap()�� ���
        //streamTest6();
    	
        //toMap() ���� ��, ������ key�� ���� �ִ� �����͸� ������ IllegalStateException�� �߻���ŵ�ϴ�.
        //�̷� ��� ����ó���� �� �� �ֽ��ϴ�. 3��° param����, �����ϴ� ���� ���ο� ���� �� � ���� �������� �����ؾ� �մϴ�.
        //�Ʒ� �ڵ�� ������ ��ϵ� ���� ����ϵ��� (existingValue, newValue) -> existingValue)�� ���� �߽��ϴ�. (�ڵ带 ���� key 5�� ���� �ִ� �����Ͱ� �ΰ� �ֽ��ϴ�.)
    	//key 5�� ��� peach�� lemon�� �ִµ�, peach�� ���� ��ϵǾ��� ������ peach�� map�� ����
    	//streamTest7();
    	
        //���� key�� ���� �ִ� �����Ͱ� ���� ��, �ΰ��� ���� ���Ͽ� �����ϵ��� ����
        //�ΰ��� ���� ���� ���� �����ϴ� �Լ��� ����
    	streamTest8();
    }
}
