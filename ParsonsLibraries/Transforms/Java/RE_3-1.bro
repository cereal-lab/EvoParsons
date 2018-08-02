Author = EvoParsons Team

Title= equality to assignment operator

Description=   when equality operator is inside control or loop statement

FragmentSelection=^(\s*(if|while|for|do\s*\{.*\}\s*while)\s*\(.*)==(.*\).*)$

Transform=$1=$3

