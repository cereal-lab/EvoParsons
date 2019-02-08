Author = EvoParsons Team

Title =  removing * when two pointers are declared in by comma

Description = Sources: http://www.c4learn.com/c-programming/c-mistakes/pointer/
Example: int *p1, p2 instead of int *p1, *p2



FragmentSelection=^(\s*)(char|int|long|double|float)(.*)\*(.*)\*(.*)$

Transform=$1$2$3$4\*$5
