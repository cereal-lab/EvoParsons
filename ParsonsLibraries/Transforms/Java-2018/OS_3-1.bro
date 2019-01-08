Author=EvoParsons Team

Title=Mismatching Curly Braces(Other Syntax Errors : OS)

Description=remove { only if this is followed by loop (without do while) , control statement (except else).

FragmentSelection=^(\s*)(if|while|for|else if|switch)(.*?\(.*\)\s*)(\{)(.*)$

Transform=$1$2$3$5

