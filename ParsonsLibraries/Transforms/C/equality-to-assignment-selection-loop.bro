Author=Alessio Gaspar
Title= converting an equality opertor to an assignment opertor in selection and loop
Description=Modify an expression which uses loop/selection replacing equality operator to assignment operator 
FragmentSelection=^(\s*(if|while|for|do\s*\{.*\}\s*while)\s*\(.*)==(.*\).*)$
Transform=$1\=$3
