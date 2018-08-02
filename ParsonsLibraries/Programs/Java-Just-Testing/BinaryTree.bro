[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Creating a Binary Search Tree

[#description#]
Creates a binary search tree using insert(int ) and createNode(int ), node is a data structure
with one integer data, and two node type variables left and right.

[#source#]
public class BinarySearchTree {		
		node root, x, y;
		BinarySearchTree() { // Line X must be written after this line
			 root = x = y = null;			
		}			
		public void insert(int data) { //Line X
			if (root == null) 
				root = createNode(data);
			else {		//Line Y		
				x = root;				
				while(x != null) {
					 y = x;
                     			if (data > x.data)
                    	 			x = x.right;
                     			else 
                    	 			x = x.left;                    
				}
				node tmp = createNode(data); // Line Y must be written before this line
				if (tmp.data > y.data)
					y.right = tmp;
				else
					y.left = tmp;				
			}				
			
		}		
		private node createNode(int data) { // Line X must be written before this line
			node aNode = new node(data);
			return aNode;
		}
	}
