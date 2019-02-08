Author = EvoParsons Team

Title =  removing * from variable declaration and use of free - standalone or inside function signature (from my own experience)
Description = Own experience
char *a into char a;
fun(char *a) into fun(char a)


FragmentSelection=^(\s*)(.*)(free|printf|char|int|long|float|double|\/\/)(.*)(\*)(.*)$


Transform=$1$2$3$4$6
