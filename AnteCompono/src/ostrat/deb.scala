/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import language.experimental.macros
import reflect.macros.blackbox.Context

object deb
{
   def apply(str: String): Unit = macro debImpl
   
   def debImpl(c: Context)(str: c.Expr[String]): c.Expr[Unit] = 
   {
      import c.universe._     
      val pos: Position  = c.macroApplication.pos      
      val s1 = macroPosn(c)(pos)
      val Predef_println = typeOf[Predef.type].decl(TermName("println"))
      c.Expr(q"""$Predef_println($s1 + ": " + $str)""")
   }
   
   def debv(s: Any): Unit = macro debvImpl  
   
   def debvImpl(c: Context)(s: c.Expr[Any]): c.Expr[Unit] = {
      import c.universe._      
      val name = show(s.tree).reverse.takeWhile(_ != '.').reverse      
      val pos: Position  = c.macroApplication.pos      
      val s1: String = macroPosn(c)(pos)
      val Predef_println: Symbol = typeOf[Predef.type].decl(TermName("println"))
      c.Expr(q"""$Predef_println($s1 -- $name + ": " + $s)""")
   }
   
   def macroPosn(c: Context)(posn: c.Position): String =
   {
      val fileName = posn.source.toString
      val lineNum: String = posn.line.toString
      val column: String = posn.column.toString
      fileName + "." + lineNum + "." + column
   }
   
}