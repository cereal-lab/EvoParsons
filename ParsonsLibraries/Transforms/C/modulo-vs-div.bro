Author=Alessio Gaspar
Title=Use of Modulo operator instead of integer division operator
Description=Modify an expression featuring an integer division operator so that we use a modulo operator instead
FragmentSelection=^(.*[^/])/([^/][0-9]+.*)$
Transform=$1%$2
