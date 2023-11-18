/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import quoted.*

/** Simple macro, prints out [[String]] preceded by source code position. */
inline def deb(str: String): Unit = ${ debImpl('str) }
def debImpl(expr: Expr[String])(using Quotes) = '{ println($posnStrImpl + " " + $expr) }

/** Simplest Macro that shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
inline def debb(): Unit = ${ debbImpl }
def debbImpl(using Quotes) = '{ println($posnStrImpl) }

/** Simple macro to create a debug [[Exception]], inserts the [[String]] preceded by source code position. */
inline def debexc(str: String): Nothing = ${ debexcImpl('str) }
def debexcImpl(expr: Expr[String])(using Quotes) = '{ throw new Exception($posnStrImpl + " " + $expr) }

/** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
inline def debvar(expr: Any): Unit = ${ debvarImpl('expr) }
def debvarImpl(expr: Expr[Any])(using Quotes) = '{ println($posnStrImpl + " " + ${Expr(expr.show)} + " = " + $expr) }

/** Macro for getting the file name and line number of the source code position. */
inline def posnStr(): String = ${ posnStrImpl }
def posnStrImpl(using Quotes): Expr[String] =
{ val pos = quotes.reflect.Position.ofMacroExpansion
  Expr(pos.sourceFile.path + ":" + (pos.startLine + 1).toString)
}

inline def inspect[T](inline expr: T*): Seq[(String, String)] = ${inspectCode('expr) }
def inspectCode[T](expr: Expr[Seq[T]])(using Quotes): Expr[Seq[(String, String)]] =
{
  import quotes.reflect.report
  expr match {
    case Varargs(aExprs) => // numberExprs: Seq[Expr[Int]]
      { val strs = aExprs.map { v =>
          val v2 = v.show.reverse.takeWhile(_ != '.').reverse
        }
        val str2: String = strs.mkString(",")

        Expr[String](str2)
      }

    case _ => report.errorAndAbort("Expected explicit varargs sequence. " + "Notation `args*` is not supported.", expr)
  }
  val s1: String = expr.show
  val s2: String = s1.dropWhile(_ != '(').drop(1).dropRight(1)
  val str3 = Seq(s2, "There")
  val str4: Expr[(String, String)] = Expr(("hi4", "bye4"))
  '{ Seq(("hi1", "bye1"), $str4 ) }
}