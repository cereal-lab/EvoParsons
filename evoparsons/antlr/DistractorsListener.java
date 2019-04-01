// Generated from .\Distractors.g4 by ANTLR 4.7.1

package evoparsons.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DistractorsParser}.
 */
public interface DistractorsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(DistractorsParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(DistractorsParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#capture}.
	 * @param ctx the parse tree
	 */
	void enterCapture(DistractorsParser.CaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#capture}.
	 * @param ctx the parse tree
	 */
	void exitCapture(DistractorsParser.CaptureContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(DistractorsParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(DistractorsParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(DistractorsParser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(DistractorsParser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(DistractorsParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(DistractorsParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(DistractorsParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(DistractorsParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(DistractorsParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(DistractorsParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceModifier(DistractorsParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceModifier(DistractorsParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(DistractorsParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(DistractorsParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(DistractorsParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(DistractorsParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(DistractorsParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(DistractorsParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(DistractorsParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(DistractorsParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void enterTypeBound(DistractorsParser.TypeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void exitTypeBound(DistractorsParser.TypeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(DistractorsParser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(DistractorsParser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstants(DistractorsParser.EnumConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstants(DistractorsParser.EnumConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstant(DistractorsParser.EnumConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstant(DistractorsParser.EnumConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterEnumBodyDeclarations(DistractorsParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitEnumBodyDeclarations(DistractorsParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(DistractorsParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(DistractorsParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(DistractorsParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(DistractorsParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(DistractorsParser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(DistractorsParser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(DistractorsParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(DistractorsParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(DistractorsParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(DistractorsParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(DistractorsParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(DistractorsParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(DistractorsParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(DistractorsParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(DistractorsParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(DistractorsParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericMethodDeclaration(DistractorsParser.GenericMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericMethodDeclaration(DistractorsParser.GenericMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericConstructorDeclaration(DistractorsParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericConstructorDeclaration(DistractorsParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(DistractorsParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(DistractorsParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(DistractorsParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(DistractorsParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBodyDeclaration(DistractorsParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBodyDeclaration(DistractorsParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberDeclaration(DistractorsParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberDeclaration(DistractorsParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstDeclaration(DistractorsParser.ConstDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstDeclaration(DistractorsParser.ConstDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConstantDeclarator(DistractorsParser.ConstantDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConstantDeclarator(DistractorsParser.ConstantDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(DistractorsParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(DistractorsParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodModifier(DistractorsParser.InterfaceMethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodModifier(DistractorsParser.InterfaceMethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericInterfaceMethodDeclaration(DistractorsParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericInterfaceMethodDeclaration(DistractorsParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(DistractorsParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(DistractorsParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(DistractorsParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(DistractorsParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(DistractorsParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(DistractorsParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(DistractorsParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(DistractorsParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(DistractorsParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(DistractorsParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(DistractorsParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(DistractorsParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(DistractorsParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(DistractorsParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(DistractorsParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(DistractorsParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(DistractorsParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(DistractorsParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(DistractorsParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(DistractorsParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(DistractorsParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(DistractorsParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(DistractorsParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(DistractorsParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(DistractorsParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(DistractorsParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(DistractorsParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(DistractorsParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(DistractorsParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(DistractorsParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(DistractorsParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(DistractorsParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(DistractorsParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(DistractorsParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairs(DistractorsParser.ElementValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairs(DistractorsParser.ElementValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(DistractorsParser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(DistractorsParser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(DistractorsParser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(DistractorsParser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(DistractorsParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(DistractorsParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeDeclaration(DistractorsParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeDeclaration(DistractorsParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeBody(DistractorsParser.AnnotationTypeBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeBody(DistractorsParser.AnnotationTypeBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementDeclaration(DistractorsParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementDeclaration(DistractorsParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementRest(DistractorsParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementRest(DistractorsParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodOrConstantRest(DistractorsParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodOrConstantRest(DistractorsParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodRest(DistractorsParser.AnnotationMethodRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodRest(DistractorsParser.AnnotationMethodRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationConstantRest(DistractorsParser.AnnotationConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationConstantRest(DistractorsParser.AnnotationConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(DistractorsParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(DistractorsParser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(DistractorsParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(DistractorsParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(DistractorsParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(DistractorsParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#localTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalTypeDeclaration(DistractorsParser.LocalTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#localTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalTypeDeclaration(DistractorsParser.LocalTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DistractorsParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DistractorsParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(DistractorsParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(DistractorsParser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#catchType}.
	 * @param ctx the parse tree
	 */
	void enterCatchType(DistractorsParser.CatchTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#catchType}.
	 * @param ctx the parse tree
	 */
	void exitCatchType(DistractorsParser.CatchTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(DistractorsParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(DistractorsParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void enterResourceSpecification(DistractorsParser.ResourceSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void exitResourceSpecification(DistractorsParser.ResourceSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#resources}.
	 * @param ctx the parse tree
	 */
	void enterResources(DistractorsParser.ResourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#resources}.
	 * @param ctx the parse tree
	 */
	void exitResources(DistractorsParser.ResourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(DistractorsParser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(DistractorsParser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(DistractorsParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(DistractorsParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(DistractorsParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(DistractorsParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(DistractorsParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(DistractorsParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(DistractorsParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(DistractorsParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForControl(DistractorsParser.EnhancedForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForControl(DistractorsParser.EnhancedForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(DistractorsParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(DistractorsParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(DistractorsParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(DistractorsParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(DistractorsParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(DistractorsParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DistractorsParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DistractorsParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(DistractorsParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(DistractorsParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(DistractorsParser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(DistractorsParser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBody(DistractorsParser.LambdaBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBody(DistractorsParser.LambdaBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(DistractorsParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(DistractorsParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(DistractorsParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(DistractorsParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(DistractorsParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(DistractorsParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(DistractorsParser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(DistractorsParser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void enterInnerCreator(DistractorsParser.InnerCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void exitInnerCreator(DistractorsParser.InnerCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreatorRest(DistractorsParser.ArrayCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreatorRest(DistractorsParser.ArrayCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(DistractorsParser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(DistractorsParser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocation(DistractorsParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocation(DistractorsParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentsOrDiamond(DistractorsParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentsOrDiamond(DistractorsParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArgumentsOrDiamond(DistractorsParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArgumentsOrDiamond(DistractorsParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArguments(DistractorsParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArguments(DistractorsParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(DistractorsParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(DistractorsParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(DistractorsParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(DistractorsParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(DistractorsParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(DistractorsParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(DistractorsParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(DistractorsParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(DistractorsParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(DistractorsParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocationSuffix(DistractorsParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocationSuffix(DistractorsParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link DistractorsParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(DistractorsParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DistractorsParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(DistractorsParser.ArgumentsContext ctx);
}