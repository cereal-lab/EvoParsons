Author = EvoParsons Team

Title= boolean OR to bitwise OR (Relational Expression : RE)

Description=  when boolean OR is not inside if/else if/ for/ while expressions

FragmentSelection=^(?!.*?(if|while|for|else\s*if))(.*?)\|\|(.*)$

Transform=$1$2\|$3

