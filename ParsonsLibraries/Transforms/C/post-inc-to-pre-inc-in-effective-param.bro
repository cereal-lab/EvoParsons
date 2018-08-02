Author=Alessio Gaspar
Title= Convert post-increment to pre-increment in effective parameter 
Description=Modify a function call with formal parameter by replacing existing post-increment to pre-increment
FragmentSelection=^(.*\W+\w+\s*\(\s*)(.*,\s*)*(\w+\s*)\+\+(\W*.*\).*;\s*)$
Transform=$1$2\+\+$3$4

