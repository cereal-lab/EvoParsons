Author = EvoParsons Team

Title =  Pointers are not arrays

Description = the article C-Pitfalls
ex char r[10] into char *r



FragmentSelection=^(\s*)(char|int|long|double|float)(\s*)(\w+)(\s*\[.*\])(.*)$

Transform=$1$2$3*$4$6


