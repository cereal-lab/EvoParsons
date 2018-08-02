Author = EvoParsons Team

Title= equality to assignment operator

Description=  when equality operator is not inside loop or control statements

FragmentSelection=^(\s*)(?!.*?(if|while|for|else|do\s*\{\s*.*\}\s*while))(.*)==(.*)$

Transform=$1$3=$4

