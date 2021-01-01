/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A Persist class described by a single value. This may be removed. Its not clear whether this means a single token or not. */
abstract class PersistSimple[A](typeStr: String) extends ShowSimple[A](typeStr) with Persist[A]
{  
  /** A PersistSimple can be parsed from the expression within a Clause, but it can't be parsed from a ClausedStatement */
 // override def fromClauses(clauses: Refs[Clause]): EMon[A] = bad1(clauses.head, typeStr -- "can not be parsed from Claused Statement")

  /*override def fromStatements(sts: Refs[Statement]): EMon[A] = sts match
  { case Refs1(st) if st.noSemi => fromExpr(st.expr)
    case sts => sts.startPosn.bad("A simple value can not be parsed from a Statement sequence.")
  }*/
//  def fromStatement(st: Statement): EMon[A] = st match
//  { case MonoStatement(expr, _) => fromExpr(expr)
//    case ClausedStatement(clauses, _) => fromClauses(clauses)
//    case es: EmptyStatement => es.asError
//  }
  
}

abstract class ShowSimple[-A](val typeStr: String) extends ShowT[A]
{
  final override def syntaxDepth: Int = 1
  override def showComma(obj: A): String = showT(obj, 0)
  override def showSemi(obj: A): String = showT(obj, 0)
  override def showTyped(obj: A): String = typeStr + showT(obj, 0).enParenth
}




