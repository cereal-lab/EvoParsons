Author = EvoParsons Team

Title= Improper Casting

Description= int to float

FragmentSelection=^(.*?)(\(\s*)(int)(\s*\))(.*)$

Transform=$1$2float$4$5
