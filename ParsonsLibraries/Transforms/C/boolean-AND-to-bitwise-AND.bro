Author=Alessio Gaspar
Title= Convert a boolean AND operator to bitwise AND operator 
Description=Modify an expression containing  boolean AND by replacing it with bitwise AND
FragmentSelection=^(.*?)&&(.*)$
Transform=$1&$2
