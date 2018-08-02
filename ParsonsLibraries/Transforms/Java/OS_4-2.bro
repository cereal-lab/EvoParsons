Author = EvoParsons Team

Title=  misspelling a variable/Keyword name(Other Syntax Errors : OS)

Description=  capitalize first letter of main keyword

FragmentSelection=^(?!.*?(int|char|float|double|boolean|=))(.*)(main)(.*)$

Transform=$1$2Main$4

