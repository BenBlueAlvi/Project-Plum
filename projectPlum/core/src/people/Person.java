package people;

/*The person class stores full people, including
 * images for the person
 * brain & personality components
 * memories
 * 
 * Developers:
 * Coosome
*/

public class Person { //may want to extend baseactor, may not
	
	private String name;
	private Brain mind;
	
	public Person(String name) {
		this.name = name;
	}
	
	
	/**
	 * Builds a new random person from scratch.
	 * 
	 * @return Person
	 */
	public Person BuildPerson() {
		
		//Return a person constructed with variables setup in this function
		return new Person("Billy");
	}
}
