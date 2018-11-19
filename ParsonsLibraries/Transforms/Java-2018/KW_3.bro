Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace variable name by continue

FragmentSelection=^(\s*)(int|float|double|boolean|char)(\s*)(\w+)(.*;)$

Transform=$1$2$3continue$5
