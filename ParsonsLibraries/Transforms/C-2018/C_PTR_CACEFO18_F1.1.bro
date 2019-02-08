Author = EvoParsons Team

Title =  * into & when value associates

Description = CACEFFO F1.1
Whenever it is necessary to directly manipulate	the	values pointed	by
pointer variables.Student	 attempts	 to	 dereference
a pointer variable	 with	 the	 & operator.
ex: *(num + i) into &(num + i)


FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*)\*(\s*\(.*\))(.*)$

Transform=$1$2$3&$4$5
