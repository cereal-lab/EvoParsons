Author = EvoParsons Team

Title= Improper Casting

Description= int to double

FragmentSelection=^(.*?)(\(\s*)(int)(\s*\))(.*)$

Transform=$1$2double$4$5
