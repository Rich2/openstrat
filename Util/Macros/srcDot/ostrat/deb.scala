/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat


/** Macro function object, prints out string preceded by source code position. */
object deb
{
  /** Simple macro, prints out string preceded by source code position. */
  def apply(str: String): Unit = println(str)
}

/** Macro function object, prints source code position. */
object debb
{
  /** Simplest Macro shows source code position. Must include parenthesis debb(). Without the parenthesis the macro will not print. */
  def apply(): Unit = println("src code postion.")
}

/** Macro function object, Prints out source code position followed by expression name, followed by expression value. */
object debvar
{
  /** An expression debug macro. Prints out source code position followed by expression name, followed by expression value. */
  def apply(inputExpr: Any): Unit = println(inputExpr.toString)
}