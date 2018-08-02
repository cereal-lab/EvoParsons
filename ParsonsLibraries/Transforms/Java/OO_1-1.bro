Author = EvoParsons Team

Title= less than into less than equal to (Off by one bugs : OO)

Description=   when in relational expressions inside loop and control statements

FragmentSelection=^(\s*)(if|while|for|else|do\s*\{\s*.*\}\s*while)(.*)<([^=].*)$

Transform=$1$2$3<=$4

