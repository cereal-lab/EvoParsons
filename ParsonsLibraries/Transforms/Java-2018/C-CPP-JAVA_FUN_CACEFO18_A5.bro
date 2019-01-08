Author = EvoParsons Team

Title =  not catching the return value of a function

Description = CACEFFO A5
squareOf(a)   instead of  retVal = squareOf(a)

FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(\w+\s*\w*\s*)=(.*)(\(.*\))(.*)$


Transform=$1$2$4$5$6
