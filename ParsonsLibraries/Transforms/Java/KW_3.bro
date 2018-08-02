Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace boolean variable name by continue

FragmentSelection=^(.*?)(int|float|double|boolean|char)(\s*)(\w+)(\s*)(;\s*)$

Transform=$1$2$3continue$5$6
