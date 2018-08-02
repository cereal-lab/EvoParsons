Author = EvoParsons Team

Title= Keword as variable name (KW)

Description= replace boolean variable name by import

FragmentSelection=^(.*?)(int|float|double|boolean|char)(\s*)(\w+)(\s*)(;\s*)$

Transform=$1$2$3import$5$6
