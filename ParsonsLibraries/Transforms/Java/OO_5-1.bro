Author = EvoParsons Team

Title= zero to one  (Off by one bugs : OO)

Description=    when in relational expressions not inside loop and control statement

FragmentSelection=^(\s*)(if|while|for|else|do\s*\{\s*.*\}\s*while)(.*?)0(.*)$

Transform=$1$2$31$4

