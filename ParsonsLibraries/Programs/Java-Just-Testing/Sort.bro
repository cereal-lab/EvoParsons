[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Sort a linked list using bubble sort

[#description#]
sort(Node head) takes the head node of a list and sorts the list items using bubble sort.
Node is a structure that contains data and a reference to Node type.

[#source#]
	
public void sort(Node head) {
	Node tmp = head.next, temp = new Node(-1,null);
	while (head != null) {
				while (tmp != null) {
					if (head.data > tmp.data) {
					temp.data = head.data;
					head.data = tmp.data;
					tmp.data = temp.data;					
				}
				tmp = tmp.next;
			}
			head = head.next != null ? head.next : null;
			tmp = head != null ? head.next : null;
	}
}	
