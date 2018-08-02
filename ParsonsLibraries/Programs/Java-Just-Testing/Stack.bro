[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Stack data structure implementation using Linked List.

[#description#]
A class definition, push, pop and IsEmpty operations for Stack using "Node" type elements. 

[#source#]
public class Stack { 
private Node top; private int count;
public void Push (Object data) {             
    	Node NewNode = new Node(data, null);
        if (top == null) top = NewNode;		
		else{
			NewNode.next = top;
			top = NewNode;
		}
		count++;
   }
public Object Top() {
		if (!IsEmpty()) return top.data;
		return -1; 	
   }
private class Node extends Object {
		Object data; Node next;
		private Node(Object data, Node next) {
			this.data = data; this.next = next;
		}
  }
 private boolean IsEmpty() {
    return count == 0;
  }
}
