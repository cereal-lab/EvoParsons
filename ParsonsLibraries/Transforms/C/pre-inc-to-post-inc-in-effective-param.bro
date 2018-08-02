Author=Alessio Gaspar
Title= Convert pre-increment to post-increment in effective parameter 
Description=Modify a function call with formal parameter by replacing existing pre-increment to post-increment
FragmentSelection=^(.*\W+\w+\s*\()(.*)\+\+(\s*\w+)(\W*.*\).*;\s*)$
Transform=$1$2$3\+\+$4
