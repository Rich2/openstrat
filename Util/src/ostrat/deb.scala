/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import quoted.*

/** Simple macro, prints out string preceded by source code position. */
inline def deb(str: String): Unit = ${ debImpl('str) }
def debImpl(expr: Expr[String])(using Quotes) = '{ println($posnStrImpl + " " + $expr) }

/** Simplest Macro shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
inline def debb(): Unit = ${ debbImpl }
def debbImpl(using Quotes) = '{ println($posnStrImpl) }

/** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
inline def debvar(expr: Any): Unit = ${ debvarImpl('expr) }
def debvarImpl(expr: Expr[Any])(using Quotes) = '{ println($posnStrImpl + " " + ${Expr(expr.show)} + " = " + $expr) }

inline def posnStr(): String = ${ posnStrImpl }
def posnStrImpl(using Quotes): Expr[String] =
{ val pos = quotes.reflect.Position.ofMacroExpansion
  Expr(pos.sourceFile.name + "\u27e6" + pos.startLine + ", " + pos.startColumn + "\u27e7")
}