/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import quoted.*

/** Simple macro, prints out string preceded by source code position. */
inline def deb(str: String): Unit = ${ debImpl('str) }

def debImpl(expr: Expr[String])(using Quotes) = {

'{ println("Hello " + $expr) }
}

/** Simplest Macro shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
def debb(): Unit = println("src code postion.")

/** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
inline def debvar(expr: Any): Unit = ${ debvarImpl('expr) }
def debvarImpl(expr: Expr[Any])(using Quotes) = {
  import quotes.reflect.*
  val pos = Position.ofMacroExpansion
  val path = Expr(pos.sourceFile.jpath.toString)
  val line = Expr(pos.startLine)
  val column = Expr(pos.startColumn)
  '{ println($path + " " + $line + " " + $column + " " + ${Expr(expr.show)} + " = " + $expr) }
}