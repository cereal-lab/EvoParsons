Author=EvoParsons Team

Title=Mismatching Curly Braces(Other Syntax Errors : OS)

Description=remove { only if this is followed by do and class keywords

FragmentSelection=^(.*?)(class|do)(.*?)(\{\s*)(.*)$

Transform=$1$2$3$5

