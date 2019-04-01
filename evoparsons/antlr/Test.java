package evoparsons.antlr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.pattern.ParseTreeMatch;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.RuleNode;

public class Test {

    // public static class Distractor {
    //     public final String programName;
    //     public final int programNode; 
    //     public final int selectedTransform;
    //     public Distractor(String programName, int programNode, int selectedTransform) {
    //         this.programName = programName;
    //         this.programNode = programNode;
    //         this.selectedTransform = selectedTransform;
    //     }
    // }

    // public static final Distractor dist1 = new Distractor("Welcome.java", 3, 2);

    public static class Var { //also function 
        public final String name; 
        public final String type;
        public final List<Var> signature;
        private Var(String name, String type, List<Var> signature) {
            this.name = name;
            this.type = type;
            this.signature = signature;
        }
        public static Var of(String name, String type, List<Var> signature) {
            return new Var(name, type, signature);
        }
    }

    public static class Scope {
        public final Map<String, Var> names; 
        public final Map<String, Var> formalParams; 
        public final List<Scope> childScopes;
        public final Scope parent;
        private Scope(Scope parent) {
            this.names = new HashMap<>();
            this.formalParams = new HashMap<>();
            this.childScopes = new ArrayList<>();
            this.parent = parent;
            if (parent != null) 
                parent.childScopes.add(this);
        }
    }

    private static class TextPart {
        public final Interval interval;
        public final String text; 
        public TextPart(Interval i, String text) {
            this.interval = i;
            this.text = text;
        }
    }

    public static class TokenDistractor {
        private Map<String, String> remapping; 
        private Set<Token> fTokens;

        private static final Random rnd = new Random(Double.doubleToLongBits(Math.random()));    
        public static final Function<List<Token>, Set<Token>> SELECT_RANDOM = foundTokens -> 
            {
                Optional<Token> tokenOpt = foundTokens.stream().skip(rnd.nextInt(foundTokens.size())).findFirst();
                if (tokenOpt.isPresent()) return Collections.singleton(tokenOpt.get());
                return Collections.emptySet();
            };

        public TokenDistractor(TokenStream tokens, final Map<String, String> remapping, Function<List<Token>, Set<Token>> selector) {
            this.remapping = remapping;
            ((BufferedTokenStream)tokens).fill();
            List<Token> foundTokens = 
                IntStream.range(0, tokens.size())
                    .mapToObj(tokens::get)
                    .filter(t -> remapping.containsKey(t.getText()))
                    .collect(Collectors.toList());       
            this.fTokens = selector.apply(foundTokens);                    
        }
        public String distract(TerminalNode node) {
            Token token = node.getSymbol();
            String text = node.getText();
            return fTokens.contains(token) ? remapping.get(text) : text;
        }
    }

    private static class Tuple<T, S> {
        public final T first;
        public final S second; 
        public Tuple(T t, S s) {
            first = t; second = s;
        }
    }    

    private static class Triple<T, S, U> {
        public final T first;
        public final S second; 
        public final U third;
        public Triple(T t, S s, U u) {
            first = t; second = s; third = u;
        }
    }    

    public static class ScopeDistrator {
        private final Map<Integer, Tuple<String, Scope>> posScopes = new HashMap<>();
        private final String replacement; 
        private final Integer position; 
        private static final Random rnd = new Random(Double.doubleToLongBits(Math.random()));    
        public ScopeDistrator(TokenStream tokens)
        {
            JavaParserBaseVisitor<Scope> scopeGatherer = new JavaParserBaseVisitor<Scope>() {
                private Scope currentScope = new Scope(null);   
                private boolean suppressReplacement = false;             
                @Override
                public Scope visitBlock(JavaParser.BlockContext ctx) {
                    Scope parentScope = currentScope;
                    currentScope = new Scope(parentScope);
                    currentScope.names.putAll(parentScope.formalParams);
                    parentScope.formalParams.clear();
                    visitChildren(ctx);
                    currentScope = parentScope;
                    return currentScope;
                }
                @Override
                public Scope defaultResult() {
                    return currentScope;
                }
                @Override
                public Scope visitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
                    String className = ctx.IDENTIFIER().getText(); //name of the class
                    currentScope.names.put(className, new Var(className, "class", null));
                    visitChildren(ctx); //children works on new scope
                    return currentScope;
                }

                @Override
                public Scope visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
                    String functionName = ctx.IDENTIFIER().getText();
                    currentScope.names.put(functionName, new Var(functionName, "function", null)); //TODO
                    visitChildren(ctx);
                    return currentScope;
                }

                @Override
                public Scope visitFormalParameter(JavaParser.FormalParameterContext ctx) {
                    String pName = ctx.variableDeclaratorId().IDENTIFIER().getText();
                    currentScope.formalParams.put(pName, new Var(pName, ctx.typeType().getText(), null));
                    return currentScope;
                }

                @Override
                public Scope visitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx)
                {
                    ctx.variableDeclarators().variableDeclarator().forEach(vd -> {
                        String vName = vd.variableDeclaratorId().IDENTIFIER().getText();
                        currentScope.names.put(vName, new Var(vName, ctx.typeType().getText(), null));
                    });
                    suppressReplacement = true;
                    visitChildren(ctx);
                    suppressReplacement = false;
                    return currentScope;
                }

                @Override
                public Scope visitTerminal(TerminalNode node) {
                    String name = node.getText(); //can be the name of variable - usage of variable                    
                    //checking current scope - TODO - generalize to check parents too
                    if (currentScope.names.containsKey(name) && !suppressReplacement) {
                        posScopes.put(node.getSymbol().getTokenIndex(), new Tuple<>(name, currentScope)); //TODO: make copy of the current scope - it is not finished yet
                    }
                    return currentScope;
                }

            };
            JavaParser parser = new JavaParser(tokens);
                    
            JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit();
            scopeGatherer.visit(compilationUnit);
            Optional<Tuple<Integer, String>> f = 
                posScopes.entrySet().stream().map(entry -> {
                    Scope sc = entry.getValue().second;
                    Var curVar = sc.names.get(entry.getValue().first);
                    if (sc.parent != null) {
                        Optional<Scope> other = sc.parent.childScopes.stream().filter(s -> s != sc).findFirst();
                        if (other.isPresent()) {
                            Map<String, Var> names = new HashMap<>(other.get().names);
                            names.remove(entry.getValue().first);
                            Optional<Map.Entry<String, Var>> found = names.entrySet().stream().filter(ent -> ent.getValue().type.equals(curVar.type)).findFirst();
                            if (found.isPresent()) return new Tuple<>(entry.getKey(), found.get().getKey());
                            else return null;
                        }
                        return null;
                    }
                    return null;
                }).filter(v -> v != null).findFirst(); //TODO: do random - not found first
            if (f.isPresent()) {
                position = f.get().first;
                replacement = f.get().second;
            } else {
                position = -1;
                replacement = "";
            }
            tokens.seek(0);
            //posScopes.entrySet().stream().skip(rnd.nextInt(posScopes.entrySet()))
        }        
        public String distract(TerminalNode node) {
            Token token = node.getSymbol();
            String text = node.getText();
            return (token.getTokenIndex() == position) ? replacement : text;
        }
    }
    
    private static class TransformedPart {
        public final List<TextPart> parts;
        public final Scope currentScope; 
        public TransformedPart(List<TextPart> parts, Scope scope) {
            this.parts = parts;
            this.currentScope = scope;
        }
        public String getText() {
            return parts.stream().map(t -> t.text).collect(Collectors.joining());
        }
        public TransformedPart joinParts(Interval interval) {
            String text = getText();
            parts.clear();
            parts.add(new TextPart(interval, text));
            return this;
        }
        public static final TransformedPart empty = new TransformedPart(Collections.emptyList(), null);
    }

    // private static Stream<ParseTree> buildSubstream(ParseTree parent)
    // {
    //     return IntStream.range(0, parent.getChildCount()).mapToObj(i -> parent.getChild(i));
    // }

    public static void main(String[] args) throws IOException {   
        Files.list(Paths.get(args[0]))
            .filter(name -> name.endsWith(Paths.get("Welcome.java")))
            .forEach(path -> 
            {
                try
                {
                    CharStream stream = CharStreams.fromPath(path);
                    JavaLexer lexer = new JavaLexer(stream);                                        
                    final TokenStream tokens = new CommonTokenStream(lexer);  
                    Map<String, String> remapping = new HashMap<>();
                    remapping.put(";", "");
                    remapping.put("class", "Class");
                    remapping.put("<", ">");
                    final TokenDistractor distr = new TokenDistractor(tokens, remapping, TokenDistractor.SELECT_RANDOM);
                    
                    final ScopeDistrator distr2 = new ScopeDistrator(tokens);
                    //populate buffer 
                    //int tokenToSearch = lexer.getTokenType(distr.from);
                    
                        //    CommonTokenStream - hides some strings - whitespaces and comments
                    //new BufferedTokenStream(lexer); // 
                    JavaParser parser = new JavaParser(tokens);
                    TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens);

                    JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit();

                    /*
                    ParseTreePattern pattern = parser.compileParseTreePattern("int <IDENTIFIER> = <variableInitializer>;", JavaParser.RULE_blockStatement);
                    List<ParseTreeMatch> match = pattern.findAll(compilationUnit, "//*");
                    match.stream().forEach(m -> 
                    {
                        if (m.succeeded()) {
                            Interval i = m.getTree().getSourceInterval();
                            String name = m.get("IDENTIFIER").getText();
                            System.out.format("found: %d-%d; ID=%s%n", i.a, i.b, name);
                        }                                        
                    });
                    */
                    /*//context/ancestor-or-self::*[self::context or @target='true']*/
                    //String xPath = "//block//localVariableDeclaration/ancestor-or-self::*[self::* or ]";
                    ParseTreePattern pattern = parser.compileParseTreePattern("{<blockStatement> int <IDENTIFIER> = <variableInitializer>; <blockStatement> int <IDENTIFIER> = <variableInitializer>; <blockStatement>}", JavaParser.RULE_blockStatement);
                    List<ParseTreeMatch> match = pattern.findAll(compilationUnit, "//block//*");
                    //step one - search for variables
                    List<Triple<String, String, Integer>> vars = 
                        match.stream().filter(ParseTreeMatch::succeeded)
                        .map(m -> 
                            {
                                Interval i = m.getTree().getSourceInterval();
                                String name = m.get("IDENTIFIER").getText();
                                //System.out.format("found: %d-%d; ID=%s%n", i.a, i.b, name);
                                //search all parent scopes
                                List<String> scopes = new ArrayList<>();
                                ParseTree cur = m.getTree();
                                while (cur.getParent() != null) {
                                    cur = cur.getParent();
                                    if (cur instanceof JavaParser.BlockContext) {
                                        scopes.add(Integer.valueOf(cur.getSourceInterval().a).toString());
                                        scopes.add("|");
                                    }
                                }
                                Collections.reverse(scopes);
                                String scopeString = scopes.stream().collect(Collectors.joining());
                                return new Triple<>(name, scopeString, i.b);
                            }).collect(Collectors.toList());
                    List<Tuple<Triple<String, String, Integer>, Triple<String, String, Integer>>> replacements = 
                        vars.stream().flatMap(v -> 
                                vars.stream().filter(v2 -> !v2.second.startsWith(v.second) && !v.second.startsWith(v2.second))
                                    .map(v2 -> new Tuple<>(v, v2)))
                                .collect(Collectors.toList());
                    if (replacements.size() > 0) {
                        Tuple<Triple<String, String, Integer>, Triple<String, String, Integer>> r = replacements.get(0);
                        System.out.format("%s[%s] <- %s[%s]%n", r.first.first, r.first.second, r.second.first, r.second.second);
                        Token t = null;
                        for (int i = 0; i < tokens.size(); i++) {
                            t = tokens.get(i);
                            if (t.getText().equals(r.second.first) && (t.getTokenIndex() > r.second.third))
                            {
                                rewriter.replace(t, t, r.first.first);
                                break;
                            }
                        }
                    }
                    System.out.println(rewriter.getText());
                } catch (Exception e) {
                    System.err.println("Error occured");
                    e.printStackTrace();
                }
            });
    }

    public static void main2(String[] args) throws IOException {      
        Files.list(Paths.get(args[0]))
            .filter(name -> name.endsWith(Paths.get("Welcome.java")))
            .forEach(path -> 
            {
                try
                {
                    CharStream stream = CharStreams.fromPath(path);
                    JavaLexer lexer = new JavaLexer(stream);                                        
                    final TokenStream tokens = new CommonTokenStream(lexer);  
                    Map<String, String> remapping = new HashMap<>();
                    remapping.put(";", "");
                    remapping.put("class", "Class");
                    remapping.put("<", ">");
                    final TokenDistractor distr = new TokenDistractor(tokens, remapping, TokenDistractor.SELECT_RANDOM);
                    
                    final ScopeDistrator distr2 = new ScopeDistrator(tokens);
                    //populate buffer 
                    //int tokenToSearch = lexer.getTokenType(distr.from);
                    
                        //    CommonTokenStream - hides some strings - whitespaces and comments
                    //new BufferedTokenStream(lexer); // 
                    JavaParser parser = new JavaParser(tokens);
                    
                    JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit();
                    JavaParserBaseVisitor<TransformedPart> visitor = new JavaParserBaseVisitor<TransformedPart>() {
                        // @Override
                        // public String visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
                        //     System.out.println(ctx.getText());
                        //     return visitChildren(ctx);
                        // }
                        @Override 
                        public TransformedPart defaultResult() {
                            return TransformedPart.empty;
                        }
                        @Override 
                        public TransformedPart aggregateResult(TransformedPart acc, TransformedPart res) {
                            List<TextPart> parts = new ArrayList<>(acc.parts);
                            parts.addAll(res.parts);
                            return new TransformedPart(parts, null);//TODO - think
                        }
                        /*
                        @Override
                        public TransformedPart visitStatement(JavaParser.StatementContext ctx)
                        {
                            System.out.println(ctx.getText());
                            return visitChildren(ctx);
                        }
                        @Override 
                        public TransformedPart visitMethodCall(JavaParser.MethodCallContext ctx) {
                            System.out.println(ctx.getText());
                            return visitChildren(ctx);
                        }
                        @Override
                        public TransformedPart visitBlock(JavaParser.BlockContext ctx) {
                            System.out.format("%nBLOCK: %s%n", ctx.getText());
                            return visitChildren(ctx);
                        }                               
                        @Override
                        public String visitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
                            System.out.format("%nPART: %s%n", ctx.getText());
                            return visitChildren(ctx);
                        }                               
                        @Override
                        public String visitClassBody(JavaParser.ClassBodyContext ctx) {
                            System.out.println(ctx.getText());
                            return visitChildren(ctx);
                        }      
                        */                  
                        // @Override
                        // public String visitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
                        //     System.out.println(ctx.getText());
                        //     return visitChildren(ctx);
                        // }
                        // @Override 
                        // public String visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
                        //     System.out.println(ctx.getText());
                        //     return visitChildren(ctx);                            
                        // }
                        // @Override 
                        // public String visitChildren(org.antlr.v4.runtime.tree.RuleNode node) {
                        //     for (int i = 0; i < node.getChildCount(); i++) {
                        //         visit(node.getChild(i));
                        //     }
                        //     return "";
                        // }
                        @Override 
                        public TransformedPart visitChildren(org.antlr.v4.runtime.tree.RuleNode node) {
                            List<TextPart> parts = new ArrayList<>();
                            Interval interval = node.getSourceInterval();
                            int childCount = node.getChildCount();
                            int selectedChild = 0;
                            int tokenIndex = interval.a;      
                            
                            TransformedPart fromChildren = super.visitChildren(node);
                            
                            while (tokenIndex <= interval.b)
                            {
                                if (selectedChild >= childCount) 
                                {
                                    //last hidden tokens
                                    Token token = tokens.get(tokenIndex);
                                    String tokenText = token.getText();
                                    //System.out.format("T %d[%d] %s%n", tokenIndex, token.getChannel(), tokenText);
                                    parts.add(new TextPart(Interval.of(tokenIndex, tokenIndex), tokenText));
                                    tokenIndex++;
                                } else {
                                    TextPart child = fromChildren.parts.get(selectedChild);
                                    Interval selectedInterval = child.interval;
                                    while (tokenIndex < selectedInterval.a) {
                                        Token token = tokens.get(tokenIndex);
                                        String tokenText = token.getText();
                                        //System.out.format("T %d[%d] %s%n", tokenIndex, token.getChannel(), tokenText);
                                        parts.add(new TextPart(Interval.of(tokenIndex, tokenIndex), tokenText));
                                        tokenIndex++;
                                    }
                                    //System.out.format("C %d[%s] %d..%d%n", selectedChild, child.getClass().getSimpleName(), selectedInterval.a, selectedInterval.b);
                                    //String childString = visit(child);                                    
                                    //builder.append(childString);
                                    parts.add(child);
                                    tokenIndex = tokenIndex + (selectedInterval.b - selectedInterval.a) + 1;
                                    selectedChild++;
                                }
                            }

                            //System.out.format("R %s%n", result);

                            return new TransformedPart(parts, null).joinParts(interval);
                        }
                        // @Override 
                        // public String visitMethodBody(JavaParser.MethodBodyContext ctx) {
                        //     System.out.println(ctx.getText());
                        //     return visitChildren(ctx);
                        // }

                        /*
                        @Override
                        public String visit(ParseTree tree) {
                            StringBuilder builder = new StringBuilder();
                            Interval interval = tree.getSourceInterval(); //selecting tokens from all channels                            
                            int childCount = tree.getChildCount();
                            int selectedChild = 0;
                            int tokenIndex = interval.a;

                            while (tokenIndex <= interval.b)
                            {
                                if (selectedChild >= childCount) 
                                {
                                    //last hidden tokens
                                    Token token = tokens.get(tokenIndex);
                                    String tokenText = token.getText();
                                    System.out.format("T %d[%d] %s%n", tokenIndex, token.getChannel(), tokenText);
                                    builder.append(tokenText);
                                    tokenIndex++;
                                } else {
                                    ParseTree child = tree.getChild(selectedChild);
                                    Interval selectedInterval = child.getSourceInterval();
                                    while (tokenIndex < selectedInterval.a) {
                                        Token token = tokens.get(tokenIndex);
                                        String tokenText = token.getText();
                                        System.out.format("T %d[%d] %s%n", tokenIndex, token.getChannel(), tokenText);
                                        builder.append(tokenText);    
                                        tokenIndex++;
                                    }
                                    System.out.format("C %d[%s] %d..%d%n", selectedChild, child.getClass().getSimpleName(), selectedInterval.a, selectedInterval.b);
                                    String childString = visit(child);                                    
                                    builder.append(childString);
                                    tokenIndex = tokenIndex + (selectedInterval.b - selectedInterval.a) + 1;
                                    selectedChild++;
                                }
                            }

                            String result = builder.toString();
                            System.out.format("R %s%n", result);
                            return result;
                        }
                        */
                        // @Override 
                        // public String visit(ParseTree tree) {
                        //     return super.visit(tree);                            
                        // }
                        @Override 
                        public TransformedPart visitTerminal(TerminalNode node) {
                            Interval i = node.getSourceInterval();
                            // for (int j = i.a; j <= i.b; j++)
                            // {
                            //     Token t = tokens.get(j);
                            //     System.out.format("%d[%d] %s%n", j, t.getChannel(), t.getText());
                            // }
                            String tokenText;
                            if (node.getSymbol().getType() == Token.EOF)
                            {
                                tokenText = "";
                            } else {
                                //bug 002 - remove random semicolon
                                tokenText = distr2.distract(node);
                            }
                            //bug 001 - missing semicolon - remove all semicolons
                            /*
                            if (tokenText.equals(";")) tokenText = "";
                            */
                            
                            //if (node.getSymbol().getTokenIndex() == foundToken) tokenText = distr.to;
                            //distr.distract(node);

                            return new TransformedPart(Collections.singletonList(new TextPart(i, tokenText)), null);
                        }    
                        /*      
                        @Override
                        public String visitPackageDeclaration(JavaParser.PackageDeclarationContext ctx)
                        {
                            // Interval i = ctx.getSourceInterval();
                            // for (int j = i.a; j <= i.b; j++)
                            // {
                            //     Token t = tokens.get(j);
                            //     System.out.format("%d[%d] %s%n", j, t.getChannel(), t.getText());
                            // }
                            System.out.println(ctx.getText());
                            return visitChildren(ctx);
                        }*/

                        // @Override
                        // public String visitTerminal(TerminalNode ctx) {
                        //     System.out.println(ctx.getText());
                        //     return "";
                        // }
                    };
                    TransformedPart parts = visitor.visit(compilationUnit);
                    System.out.println(parts.getText());
                    //compilationUnit.
                    // compilationUnit.children.stream().flatMap(p -> ((ParserRuleContext)p).children.stream());
                    // compilationUnit.packageDeclaration().children.remove(2);
                    // CommonTreeAdaptor adaptor = new CommonTreeAdaptor();
                    // CommonTree tree = (CommonTree)adaptor.nil();
                    // adaptor.create(JavaLexer.PACKAGE, "package");
                    // adaptor.create(JavaLexer.NEW, "new");
                    // System.out.println(tree.getText());
                    // //adaptor.create(, "package");
                    // System.out.println(compilationUnit.packageDeclaration().getText());            
                } catch (Exception e) {
                    System.err.println("Error occured");
                    e.printStackTrace();
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