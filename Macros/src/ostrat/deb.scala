/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import language.experimental.macros
import reflect.macros.blackbox.Context

object deb
{
  /** Simple macro, prints out string preceded by source code position. */
  def apply(str: String): Unit = macro debImpl
   
  def debImpl(c: Context)(str: c.Expr[String]): c.Expr[Unit] = 
  { import c.universe._     
    val pos: Position  = c.macroApplication.pos      
    val s1 = macroPosn(c)(pos)
    val Predef_println = typeOf[Predef.type].decl(TermName("println"))
    c.Expr(q"""$Predef_println($s1 + ": " + $str)""")
  }  
   
  def macroPosn(c: Context)(posn: c.Position): String =
  { val fileName = posn.source.toString
    val lineNum: String = posn.line.toString
    val column: String = posn.column.toString
    fileName + "." + lineNum + "." + column
  }   
}

object debb
{
  def apply(): Unit = macro debImpl
  
  def debImpl(c: Context)(): c.Expr[Unit] = 
  { import c.universe._     
    val pos: Position  = c.macroApplication.pos      
    val s1 = deb.macroPosn(c)(pos)
    val Predef_println = typeOf[Predef.type].decl(TermName("println"))
    c.Expr(q"""$Predef_println($s1)""")
  }  
}

object debvar
{
  /** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
  def apply(inputExpr: Any): Unit = macro debvarImpl  
   
  /** Simple macro, prints out source code position, variable name, variable value */
  def debvarImpl(c: Context)(inputExpr: c.Expr[Any]): c.Expr[Unit] =
  {
    import c.universe._      
    val name1: String = show(inputExpr.tree).reverse.takeWhile(_ != '.').reverse
    val name2: String = name1 + ": " 
    val pos: Position  = c.macroApplication.pos      
    val codePosnStr: String = deb.macroPosn(c)(pos)
    val Predef_println: Symbol = typeOf[Predef.type].decl(TermName("println"))
    val tree: c.Tree = q"""$Predef_println($codePosnStr + " " + $name2 + $inputExpr)"""
    c.Expr(tree)      
  }
}