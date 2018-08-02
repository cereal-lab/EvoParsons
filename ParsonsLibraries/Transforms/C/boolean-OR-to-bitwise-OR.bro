Author=Alessio Gaspar
Title= Convert a boolean OR operator to bitwise OR operator 
Description=Modify an expression containing  boolean OR by replacing it with bitwise OR
FragmentSelection=^(.*?)\|\|(.*)$
Transform=$1\|$2
