Author = EvoParsons Team

Title =  removes * when * is associated with expression

Description = CACEFFO F2
return *p + 10 into return p + 10



FragmentSelection=^(\s*)(?!.*?(free|printf|char|int|long|float|double|\/\/))(.*\(*\s*)\*(\w+\s*\)*)(.*)$

Transform=$1$2$3$4$5
