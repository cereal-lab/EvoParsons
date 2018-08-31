package evoparsons.antlr;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Test {
    public static void main(String[] args) {        
        CharStream stream = (CharStream)CharStreams.fromString("package test;\npublic class A {\npublic static void main(String[] args){\n System.out.print(\"Hello world!\"); }}");
        JavaLexer lexer = new JavaLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit();
        compilationUnit.packageDeclaration().children.remove(2);
        CommonTreeAdaptor adaptor = new CommonTreeAdaptor();
        CommonTree tree = (CommonTree)adaptor.nil();
        adaptor.create(JavaLexer.PACKAGE, "package");
        adaptor.create(JavaLexer.NEW, "new");
        System.out.println(tree.getText());
        //adaptor.create(, "package");
        System.out.println(compilationUnit.packageDeclaration().getText());
        //System.out.println(compilationUnit.packageDeclaration().stop.getText());
        //System.out.println(compilationUnit.packageDeclaration().toInfoString(lexer));
    }
}