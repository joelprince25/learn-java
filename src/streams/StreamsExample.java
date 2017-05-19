package streams;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import basics.Person;

public class StreamsExample {
	
	public static Function<String, Person> mapToPerson = (line) -> {
		  String[] p = line.split(",");
		  return new Person(p[0], Integer.parseInt(p[1]));
		};
	
	public static void main(String[] args) {
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		
		List<Person> people = Arrays.asList(
				new Person("A",1),
				new Person("B",2),
				new Person("C",3)
				);
		
		List<Person> persons = new ArrayList<Person>();
		try{
			InputStream is = new FileInputStream(new File("C:\\Users\\Pc\\workspace\\learn-java\\data\\persons.csv"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			persons = br.lines()
					.skip(1)
					.map(mapToPerson)
					.collect(Collectors.toList());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		persons.stream()
		.forEach(p -> System.out.println(p.getName()));
		
		persons.stream()
		.filter(p -> p.getName().startsWith("A"))
		.forEach(p -> System.out.println(p.getName()));
		
		people.stream()
		.forEach(Person::zeroAge);
		
		people.stream()
		.forEach(p -> System.out.println(p.getAge()));
		
	}

}
