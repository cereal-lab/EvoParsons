Author = EvoParsons Team

Title= Functions/Methods (FN)

Description= return nothing when return something is expected

FragmentSelection=^(?!.*?(System))(.*)return(\s+)(.*);(\s*)$

Transform=$1$2return$3;$5
