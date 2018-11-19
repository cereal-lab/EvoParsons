Author = EvoParsons Team

Title= boolean AND to bitwise AND (Relational Expression : RE)

Description= when boolean AND is inside if/else if/ for/ while expressions

FragmentSelection=^(\s*)(if|while|for|else|do\s*\{\s*.*\}\s*while)(.*)&&(.*)$

Transform=$1$2$3&$4
