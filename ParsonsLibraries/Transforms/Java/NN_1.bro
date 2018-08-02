Author = EvoParsons Team

Title= No Name (NN - they are remaing from the two papers. see notes)

Description= Wrong separators in for loops (coma instead of semicolon for the second condition)

FragmentSelection=^(\s*)(for)(.*?);(.*)(\)\s*\{)(.*)$

Transform=$1$2$3,$4$5$6
