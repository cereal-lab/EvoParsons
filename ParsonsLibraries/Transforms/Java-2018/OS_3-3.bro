Author = EvoParsons Team

Title=  Mismatching parentheses(Other Syntax Errors : OS)

Description=  removing the closing one if this is followed by loop  , control statement (except else) .

FragmentSelection=^(\s*)(if|while|for|else if|switch)(.*?\(.*)\)(.*)$

Transform=$1$2$3$4

