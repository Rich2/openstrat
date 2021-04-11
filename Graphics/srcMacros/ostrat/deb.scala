/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import language.experimental.macros, reflect.macros.blackbox.Context

/** Macro function object, prints out string preceded by source code position. */
object deb
{
  /** Simple macro, prints out string preceded by source code position. */
  def apply(str: String): Unit = macro debImpl
   
  def debImpl(c: Context)(str: c.Expr[String]): c.Expr[Unit] = 
  { import c.universe._     

    val s1 = macroPosn(c)
    val Predef_println = typeOf[Predef.type].decl(TermName("println"))
    c.Expr(q"""$Predef_println($s1 + ": " + $str)""")
  }  
   
  def macroPosn(c: Context): String =
  { import c.universe._
    val posn: Position  = c.macroApplication.pos
    val fileName = posn.source.toString
    val lineNum: String = posn.line.toString
    val column: String = posn.column.toString
    fileName + "." + lineNum + "." + column
  }
}

object posnStr
{
  def apply(): String = macro strImpl

  def strImpl(c: Context)(): c.Expr[String] =
  { import c.universe._
    val s1 = deb.macroPosn(c)
    c.Expr(q"""$s1""")
  }
}

/** Macro function object, prints source code position. */
object debb
{
  /** Simplest Macro shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
  def apply(): Unit = macro debbImpl
  
  def debbImpl(c: Context)(): c.Expr[Unit] = 
  { import c.universe._
    val s1 = deb.macroPosn(c)
    val Predef_println = typeOf[Predef.type].decl(TermName("println"))
    c.Expr(q"""$Predef_println($s1)""")
  }  
}

/** Macro function object, Prints out source code position followed by expression name, followed by expression value. */
object debvar
{
  /** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
  def apply(inputExpr: Any): Unit = macro debvarImpl  
   
  /** Simple macro, prints out source code position, variable name, variable value */
  def debvarImpl(c: Context)(inputExpr: c.Expr[Any]): c.Expr[Unit] =
  {
    import c.universe._      
    val name1: String = show(inputExpr.tree).reverse.takeWhile(_ != '.').reverse
    val str: String = deb.macroPosn(c) + ". " + name1 + " = "
    val Predef_println: Symbol = typeOf[Predef.type].decl(TermName("println"))
    val tree: c.Tree = q"""$Predef_println($str + $inputExpr)"""
    c.Expr(tree)      
  }
}