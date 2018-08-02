Author=Alessio Gaspar
Title= Convert pre-decrement to post-decrement in effective parameter 
Description=Modify a function call with formal parameter by replacing existing pre-decrement to post-decrement
FragmentSelection=^(.*\W+\w+\s*\()(.*)\-\-(\s*\w+)(\W*.*\).*;\s*)$
Transform=$1$2$3\-\-$4
