/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** I think this class may be redundant and can be replace by a more general PersistSum class for displaying algebraic sum types. */
abstract class PersistSingletons[A <: ShowSingleton](typeStr: String) extends PersistSimplePrecision[A](typeStr)
{ def singletonList: List[A]
  @inline override def strT(obj: A): String = obj.str
  def fromExpr(expr: Expr): EMon[A] = expr match
  { case IdentLowerToken(_, str) => singletonList.find(el => el.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}