Author = EvoParsons Team

Title= greater than equal  into greater than  (Off by one bugs : OO)

Description=    when in relational expressions not inside loop and control statement

FragmentSelection=^(?!.*(if|while|for|else\s*if))(.*?)>=(.*)$

Transform=$1$2>$3

