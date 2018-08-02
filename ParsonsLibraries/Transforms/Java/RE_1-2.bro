Author = EvoParsons Team

Title= boolean AND to bitwise AND (Relational Expression : RE)

Description= when boolean AND is not inside control or loop statement

FragmentSelection=^(?!.*?(if|else\s*if|while|for))(.*?)&&(.*)$


Transform=$1$2&$3
