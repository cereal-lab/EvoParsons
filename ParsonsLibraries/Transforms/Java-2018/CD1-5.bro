Author = EvoParsons Team

Title= Caceffo Misconceptions adaptation

Description=   Improper update of loop counter
FragmentSelection=^(\s*)(for)(.*;\s*)(\w+)\-\-(\s*\).*)$

Transform=$1$2$3$4\+\+$5

