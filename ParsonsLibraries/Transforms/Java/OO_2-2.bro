Author = EvoParsons Team

Title= less than equal into less than  (Off by one bugs : OO)

Description=   when in relational expressions but not inside loop and control statements

FragmentSelection=^(?!.*?(if|while|for|else\s*if))(.*)<=(.*)$

Transform=$1$2<$3

