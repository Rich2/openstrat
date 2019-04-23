/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** A Persist class described by a single value. */
abstract class PersistSimple[A](typeSym: Symbol) extends ShowSimple[A](typeSym) with Persist[A]
{
  override def typeStr: String = typeSym.name
  /** A PersistSimple can be parsed from the expression within a Clause, but it can't be parsed from a ClausedStatement */
  override def fromClauses(clauses: Seq[Clause]): EMon[A] = bad1(clauses.head, typeStr -- "can not be parsed from Claused Statement")
  def fromStatement(st: Statement): EMon[A] = st match
  { case MonoStatement(expr, _) => fromExpr(expr)
    case ClausedStatement(clauses, _) => fromClauses(clauses)
    case es: EmptyStatement => es.asError
  }
  override def showComma(obj: A): String = show(obj)
  override def showSemi(obj: A): String = show(obj)
  override def showTyped(obj: A): String = typeStr - show(obj).enParenth
}




