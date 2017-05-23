package streams;

import java.util.List;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import basics.Person;

public class StreamsExample {
	
	//Defining this as static outside main since it won't work otherwise. No need to do this if it's in a class.
	static List<Person> people_file = new ArrayList<Person>();
	static Stream<Person> people_file_stream = null;
	
	public static Function<String, Person> mapToPerson = (line) -> {
		  String[] p = line.split(",");
		  return new Person(p[0], Integer.parseInt(p[1]));
		};
	
	public static void main(String[] args) {
		
		//Creating a list of Person objects
		List<Person> people_create = Arrays.asList(
				new Person("A",1),
				new Person("B",2),
				new Person("C",3)
				);
		
		Stream<Person> people_create_stream = people_create.stream();
	
		//Creating a list of Person objects from a file
		try{
			InputStream is = new FileInputStream(new File(System.getProperty("user.dir")+"/data/persons.csv"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    people_file = br.lines()
						.skip(1)
						.map(mapToPerson)
						.collect(Collectors.toList());
			
			people_file_stream = br.lines()
								 .skip(1)
								 .map(mapToPerson);
			
			//Can't do this cause it says the stream has been closed when trying to use supplier later on.
			//people_file_supplier = () -> br.lines()
			//						       .skip(1)
			//					           .map(mapToPerson);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Creating separate streams to work with objects in a list
		System.out.println("Output 1");
		people_create.stream().forEach(p -> System.out.println(p.getName()));
		people_create.stream().forEach(p -> System.out.println(p.getAge()));
		people_create.stream().filter(p -> p.getName().startsWith("A")).forEach(p -> p.zeroAge());
		people_create.stream().filter(p -> p.getName().startsWith("A")).forEach(p -> System.out.println(p.getAge())); //0
		people_create.stream().forEach(p -> System.out.println(p.getAge())); //0 2 3
		
		System.out.println("Output 2");
		people_file.stream().forEach(p -> System.out.println(p.getName()));
		people_file.stream().forEach(p -> System.out.println(p.getAge()));
		
		//Working directly with streams
		System.out.println("Output 3");
		people_create_stream.forEach(p -> System.out.println(p.getName()));
		//The below two lines of code don't work since streams can't be reused.
		//people_create_stream.forEach(p -> System.out.println(p.getAge()));
		//people_file_stream.forEach(p -> System.out.println(p.getName()));
		
		//We need to use suppliers to reuse streams
		
		//Reusing streams created from a list
		System.out.println("Output 4");
		Supplier<Stream<Person>> people_create_supplier = () -> people_create.stream();
		people_create_supplier.get().forEach(p -> System.out.println(p.getName()));
		people_create_supplier.get().filter(p -> p.getName().startsWith("A")).forEach(p -> p.zeroAge());
		people_create_supplier.get().forEach(p -> System.out.println(p.getAge()));
		Person a = people_create_supplier.get().filter(p -> Objects.equals(p.getName(), "A")).findFirst().orElse(null);
		System.out.println(a.getName());
		System.out.println(a.getAge());
		
		//Reusing streams created from a file
		System.out.println("Output 5");
		//Supplier<Stream<Person>> people_file_supplier = () -> people_file_stream; (This would have been good if it worked but we error saying stream is closed)
		//This works but it's reading from a list.
		Supplier<Stream<Person>> people_file_supplier = () -> people_file.stream();
		people_file_supplier.get().forEach(p -> System.out.println(p.getName()));
		people_file_supplier.get().filter(p -> p.getName().startsWith("A")).forEach(p -> p.zeroAge());
		people_file_supplier.get().forEach(p -> System.out.println(p.getAge()));
		Person b = people_create_supplier.get().filter(p -> Objects.equals(p.getName(), "A")).findFirst().orElse(null);
		System.out.println(b.getName());
		System.out.println(b.getAge());
		System.out.println("Number of elements in stream = " + people_file_supplier.get().count()); //26
		
		//The awesome part about suppliers is that we can define some common actions that always needs to happen for the stream
		System.out.println("Output 6");
		Supplier<Stream<Person>> people_file_supplier_a_b = () -> people_file.stream().filter(p -> p.getName().startsWith("B"));
		people_file_supplier_a_b.get().forEach(p -> p.zeroAge());	
		people_file_supplier_a_b.get().forEach(p -> System.out.println(p.getAge()));//Prints only one age
		System.out.println("Number of elements in stream = " + people_file_supplier_a_b.get().count()); //1
	}

}
