Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace variable name by final

FragmentSelection=^(\s*)(int|float|double|boolean|char)(\s*)(\w+)(.*;)$

Transform=$1$2$3final$5
