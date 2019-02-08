Author = EvoParsons Team

Title =  removing * when printing the value of a variable that is pointed by a pointer

Description = Sources: http://www.c4learn.com/c-programming/c-mistakes/pointer/
Example: printf(“%d”, ptr) instead of printf(“%d”, *ptr)



FragmentSelection=^(\s*)(printf)(.*)(,\s*)\*(.*)$

Transform=$1$2$3$4$5
