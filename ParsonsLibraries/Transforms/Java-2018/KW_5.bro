Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace boolean variable name by break

FragmentSelection=^(\s*)(int|float|double|boolean|char)(\s*)(\w+)(.*;)$

Transform=$1$2$3break$5
