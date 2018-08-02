Author=Alessio Gaspar
Title=Use of division operator instead of modulo
Description=Modify an expression featuring a modulo operator so that we use a division operator instead
FragmentSelection=^(.*[^/])%([^/][0-9]+.*)$
Transform=$1/$2
