Author=Alessio Gaspar
Title= Remove a semicolon after closing parenthesis
Description=Modify an expression containing  semicolon before a closing parentheiss by removing the semicolon
FragmentSelection=^(.*\)\s*);(.*)$
Transform=$1$2
