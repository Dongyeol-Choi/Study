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
	
	//Collectors와 함께 collect로 HashSet 객체를 만드는 예제입니다. 인자에 toSet()만 넣어주면 Set 자료형
    public static void streamTest() {
    	Set<String> fruitSet = fruits.collect(Collectors.toSet());
    	for (String s : fruitSet) {
    	    System.out.println(s);
    	}
    }
    
    //Collectors를 이용하여 스트림의 아이템들을 List 객체를 만드는 예제입니다. 인자에 toList()만 넣어주면 List 자료형
    public static void streamTest1() {
    	List<String> fruitList = fruits.collect(Collectors.toList());
    	for (String s : fruitList) {
    	    System.out.println(s);
    	}
    }
    
    //스트림의 모든 아이템들을 1개의 String
    public static void streamTest2() {
    	String result = fruits.collect(Collectors.joining());
    	System.out.println(result);
    }
    
    //string 객체 간에 구분자를 넣어주고 싶다면 Collectors.joining(", ")처럼 param으로 구분자를 전달
    public static void streamTest3() {
    	String result = fruits.map(Object::toString).collect(Collectors.joining(", "));
    	System.out.println(result);
    }
    
    //문자열의 길이로 아이템들을 비교해서, 가장 긴 문자열을 리턴하는 예제, 리턴 타입은 Optional, 비교 함수(Comparator)
    public static void streamTest4() {
    	Function<String, Integer> getCount = fruit-> fruit.length();
    	Optional<String> result = fruits.map(Object::toString).collect(maxBy(comparing(getCount)));
    	System.out.println("result: " + result.orElse("no item"));
    }
    
    //Collectors는 스트림 아이템들의 평균 값을 구해주는 기능
    public static void streamTest5() {
    	List<Integer> list = Arrays.asList(1,2,3,4);
    	Double result = list.stream().collect(Collectors.averagingInt(v -> v*1));
    	System.out.println("Average: "+result);
    }
    
    //Custom 객체에 대해서도 Collect를 할 수도 있습니다. Collectors.toMap()을 사용
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
    
    //toMap() 수행 중, 동일한 key를 갖고 있는 데이터를 만나면 IllegalStateException을 발생시킵니다.
    //이런 경우 예외처리를 할 수 있습니다. 3번째 param으로, 존재하는 값과 새로운 값이 중 어떤 값을 저장할지 정의해야 합니다.
    //아래 코드는 기존에 등록된 값을 사용하도록 (existingValue, newValue) -> existingValue)로 정의 했습니다. (코드를 보면 key 5를 갖고 있는 데이터가 두개 있습니다.)
    //key 5의 경우 peach와 lemon이 있는데, peach가 먼저 등록되었기 때문에 peach가 map에 저장
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
    
    //동일 key를 갖고 있는 데이터가 있을 때, 두개의 값을 합하여 저장하도록 정의
    //두개의 값을 더한 값을 리턴하는 함수를 정의
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
    	
        //Stream테스트
    	
        //Collectors와 함께 collect로 HashSet 객체를 만드는 예제입니다. 인자에 toSet()만 넣어주면 Set 자료형
        //streamTest();
    	
        //Collectors를 이용하여 스트림의 아이템들을 List 객체를 만드는 예제입니다. 인자에 toList()만 넣어주면 List 자료형
        //streamTest1();
    	
        //스트림의 모든 아이템들을 1개의 String
        //streamTest2();
    	
        //string 객체 간에 구분자를 넣어주고 싶다면 Collectors.joining(", ")처럼 param으로 구분자를 전달
        //streamTest3();
    	
        //문자열의 길이로 아이템들을 비교해서, 가장 긴 문자열을 리턴하는 예제, 리턴 타입은 Optional, 비교 함수(Comparator)
        //streamTest4();
    	
        //Collectors는 스트림 아이템들의 평균 값을 구해주는 기능
        //streamTest5();
    	
        //Custom 객체에 대해서도 Collect를 할 수도 있습니다. Collectors.toMap()을 사용
        //streamTest6();
    	
        //toMap() 수행 중, 동일한 key를 갖고 있는 데이터를 만나면 IllegalStateException을 발생시킵니다.
        //이런 경우 예외처리를 할 수 있습니다. 3번째 param으로, 존재하는 값과 새로운 값이 중 어떤 값을 저장할지 정의해야 합니다.
        //아래 코드는 기존에 등록된 값을 사용하도록 (existingValue, newValue) -> existingValue)로 정의 했습니다. (코드를 보면 key 5를 갖고 있는 데이터가 두개 있습니다.)
    	//key 5의 경우 peach와 lemon이 있는데, peach가 먼저 등록되었기 때문에 peach가 map에 저장
    	//streamTest7();
    	
        //동일 key를 갖고 있는 데이터가 있을 때, 두개의 값을 합하여 저장하도록 정의
        //두개의 값을 더한 값을 리턴하는 함수를 정의
    	streamTest8();
    }
}
