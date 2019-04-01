grammar Distractors;
import JavaParser, JavaLexer;

@header {
package evoparsons.antlr;
}

blockStatement
    : localVariableDeclaration ';'
    | statement
    | localTypeDeclaration
    | capture["blockStatement"]  //adding capture here
    ;

capture [String ruleName] //[String ruleName]: 
    : '`' (IDENTIFIER | ANYTHING) (':' ruleType=IDENTIFIER { $ruleType.getText().equals($ruleName) }?)? '`' ('*' | '+')? '?'?
    | ANYTHING_MANY;

ANYTHING: '_';    
ANYTHING_MANY: '...';