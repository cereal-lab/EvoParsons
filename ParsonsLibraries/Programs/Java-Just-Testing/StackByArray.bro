[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Stack implementation using array

[#description#]
A class definition for stack data structure using array data type.

[#source#]
	
public class Stack {
	private int [] Data; // line p
	private int top ; //This line should be between line p and line q
	private final int SIZE; // line q
	public Stack() { //This line should be written before line X
		top = -1; SIZE = 10;
		Data = new int[SIZE];		
	}
	public void push(int data) { //This is line X. Line X should be written before line A
		Data[++top] = data;
	}
	public int pop () { //This is line A. Line A should be written before line B
		return --top;
	}
	public int size() { // This is line B. Line B should be written before line C  
		if (IsEmpty()) return top;
		return top + 1;
	}
	public boolean IsEmpty() { // This is line C
		return top < 0;
	}
}
	
	