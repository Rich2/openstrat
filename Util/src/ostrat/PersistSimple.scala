/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** A Persist class described by a single value. Its not clear whther this means a single token not. */
abstract class PersistSimple[A](val typeStr: String) extends ShowSimple[A] with Persist[A]
{  
  /** A PersistSimple can be parsed from the expression within a Clause, but it can't be parsed from a ClausedStatement */
  override def fromClauses(clauses: Arr[Clause]): EMon[A] = bad1(clauses.head, typeStr -- "can not be parsed from Claused Statement")

  override def fromStatements(sts: List[Statement]): EMon[A] = sts match
  { case st :: Nil if st.noSemi => fromExpr(st.expr)
    case sts => bad1(sts.startPosn, "A simple value can not be parsed from a Statement sequence.")
  }
//  def fromStatement(st: Statement): EMon[A] = st match
//  { case MonoStatement(expr, _) => fromExpr(expr)
//    case ClausedStatement(clauses, _) => fromClauses(clauses)
//    case es: EmptyStatement => es.asError
//  }
  
}

trait ShowSimple[-A] extends Show[A]
{
  final override def syntaxDepth: Int = 1
  override def showComma(obj: A): String = show(obj)
  override def showSemi(obj: A): String = show(obj)
  override def showTyped(obj: A): String = typeStr + show(obj).enParenth
}




