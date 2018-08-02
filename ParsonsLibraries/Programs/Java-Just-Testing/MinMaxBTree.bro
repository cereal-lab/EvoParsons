[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Minimum and maximum element of a binary tree 

[#description#]
There are two methods : Minimum (node ) which returns the minimum and 
Maximum (node ) which returns the maximum of the tree.

[#source#]
public int Minimum(node root) { //Write this line before line X
	node y = null;
	while (root != null) {
		y = root;
		root = root.left;
		}
	return y.data;			
}
		
public int Maximum(node root) {
    node y = root;
    while (root != null) {
	y = root;
	root = root.right;
    }
return y.data;
}
