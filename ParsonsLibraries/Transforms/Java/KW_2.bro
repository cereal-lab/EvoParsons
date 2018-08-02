Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace variable name by new

FragmentSelection=^(.*?)(int|float|double|boolean|char)(\s*)(\w+)(\s*)(;\s*)$

Transform=$1$2$3new$5$6
