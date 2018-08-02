[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Add two integer linked lists item by item and provide their summation 

[#description#]
As for example, if two lists are 6->8->7 and 5->6->9 then addList(Node, Node, int)
will produce a list 1->5->7->1

[#source#]
public  Node addList(Node head1, Node head2, int Carry) {
		Node head = null; int addedValue = 0;	
	    while (head1 != null) {
	    	addedValue = (Integer)head1.data + (Integer) head2.data + Carry;
	    	Carry = addedValue / 10; 
	    	addedValue = addedValue %10;
	    	Node newNode = new Node(addedValue, null );
	    	if (head == null) 	
	    	   head = newNode;
	    	else {	    		
	    		newNode.next = head;
	    		head = newNode;		 
	    	}
	    	if (Carry > 0 && head1.next == null){
	    		newNode = new Node(Carry, null );
	    		newNode.next = head;
	    		head = newNode;
	    	}
	    	head1 = head1.next; head2 = head2.next;
	    }
	    
	    
		return head;
	}
