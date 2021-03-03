/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.quoted._

/** Simple macro, prints out string preceded by source code position. */
def deb(str: String): Unit = println(str)

/** Simplest Macro shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
def debb(): Unit = println("src code postion.")

/** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
inline def debvar(expr: Any): Unit = ${ debvarImpl('expr) }
def debvarImpl(expr: Expr[Any])(using Quotes) = '{ println(${Expr(expr.show)} + " = " + $expr) }