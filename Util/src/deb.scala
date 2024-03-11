/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
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

/** Simple macro to create a debug [[Exception]], inserts the [[String]] "Not Implemented" preceded by source code position. */
inline def debnotimp(): Nothing = ${ debNotimplImpl() }
def debNotimplImpl()(using Quotes) = '{ throw new Exception($posnStrImpl + " Not implemented") }

/** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
inline def debvar(expr: Any): Unit = ${ debvarImpl('expr) }
def debvarImpl(expr: Expr[Any])(using Quotes) = '{ println($posnStrImpl + " " + ${Expr(expr.show)} + " = " + $expr) }

/** Macro for getting the file name and line number of the source code position. */
inline def posnStr(): String = ${ posnStrImpl }
def posnStrImpl(using Quotes): Expr[String] =
{ val pos = quotes.reflect.Position.ofMacroExpansion
  Expr(pos.sourceFile.path + ":" + (pos.startLine + 1).toString)
}

/** Transforms varargs of type T into a sequence of [[Tuple2]][(String, T)]s. */
inline def identStrs[T](inline expr: T*): Seq[(String, T)] = ${identStrsCode('expr) }
def identStrsCode[T](expr: Expr[Seq[T]])(using Type[T], Quotes): Expr[Seq[(String, T)]] =
{
  import quotes.reflect.report
  val aSeq: Seq[Expr[T]] = expr match
  { case Varargs(aExprs) => aExprs
    case _ => report.errorAndAbort("Expected explicit varargs sequence. " + "Notation `args*` is not supported.", expr)
  }
  val s1: String = expr.show
  val s2s: Array[String] = s1.split(", ")
  val s3s = s2s.map(_.reverse.takeWhile(_ != '.').reverse)
  var i = 0
  val aSeq2 = aSeq.map{ (ep: Expr[T]) =>
    i = i + 1
    Expr.ofTuple(Expr(s3s(i - 1)), ep)
  }

  Expr.ofSeq(aSeq2)
}