package evoparsons.antlr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import evoparsons.repo.WatchedJsonFileRepo;

public class Test {

    public static class Distractor {
        public final String programName;
        public final int programNode; 
        public final int selectedTransform;
        public Distractor(String programName, int programNode, int selectedTransform) {
            this.programName = programName;
            this.programNode = programNode;
            this.selectedTransform = selectedTransform;
        }
    }

    public static final Distractor dist1 = new Distractor("Welcome.java", 3, 2);

    // private static class Listener extends JavaParserBaseListener {
    //     @Override
    //     enter(
    // }

    private static Stream<ParseTree> buildSubstream(ParseTree parent)
    {
        return IntStream.range(0, parent.getChildCount()).mapToObj(i -> parent.getChild(i));
    }

    public static void main(String[] args) {
        System.out.println(WatchedJsonFileRepo.InstructorJsonFileRepo.class.getName());
    }
    public static void main2(String[] args) throws IOException {        
        Files.list(Paths.get(args[0]))
            .forEach(path -> 
            {
                try
                {
                    CharStream stream = CharStreams.fromPath(path);
                    JavaLexer lexer = new JavaLexer(stream);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    JavaParser parser = new JavaParser(tokens);
                    JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit();
                    //compilationUnit.
                    compilationUnit.children.stream().flatMap(p -> ((ParserRuleContext)p).children.stream());
                    compilationUnit.packageDeclaration().children.remove(2);
                    CommonTreeAdaptor adaptor = new CommonTreeAdaptor();
                    CommonTree tree = (CommonTree)adaptor.nil();
                    adaptor.create(JavaLexer.PACKAGE, "package");
                    adaptor.create(JavaLexer.NEW, "new");
                    System.out.println(tree.getText());
                    //adaptor.create(, "package");
                    System.out.println(compilationUnit.packageDeclaration().getText());            
                } catch (Exception e) {

                }
            });

        /*
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
        //System.out.println(compilationUnit.packageDeclaration().toInfoString(lexer));*/
    }
}