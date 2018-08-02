Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace variable name by final

FragmentSelection=^(.+?)(int|float|double|boolean|char)(\s*)(\w+)(\s*)(;\s*)$

Transform=$1$2$3final$5$6
