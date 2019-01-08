Author = EvoParsons Team

Title= Caceffo Misconceptions adaptation

Description=   Improper update of loop counter
FragmentSelection=^(\s*)(while|for)(.*=)(\s*\w+\s*)\+(\s*1\s*.*\).*)$

Transform=$1$2$3$5

