[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Delete a node from a single linked list, give only access to that node 

[#description#]
The methods DeleteNode(Node node) takes a node, deletes it and return 
true, otherwise returns false. A node has two attributes : data and next where next holds the 
address of its next node.

[#source#]
public boolean DeleteNode (Node node) {
    	if (node == null || node.next == null) 
    	   return false;
    	node.data = node.next.data;
    	node.next = node.next.next;
    	return true;
    }
