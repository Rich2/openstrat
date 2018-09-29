/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** A Persist class described by a single value */
abstract class PersistSimple[A](typeSym: Symbol) extends Persist[A](typeSym)
{ final override def syntaxDepth: Int = 1
   /** A PersistSimple can be parsed from the expression within a Clause, but it can't be parsed from a ClausedStatement */
   override def fromClauses(clauses: Seq[Clause]): EMon[A] = bad1(clauses.head, typeStr -- "can not be parsed from Claused Statement")
   def fromStatement(st: Statement): EMon[A] = st match
   {
      case MonoStatement(expr, _) => fromExpr(expr)
      case ClausedStatement(clauses, _) => fromClauses(clauses)
      case es: EmptyStatement => es.asError
   }
   override def persistComma(obj: A): String = persist(obj)
   override def persistSemi(obj: A): String = persist(obj)
   def persistTyped(obj: A): String = typeStr - persist(obj).enParenth
   //override def fromStatement(st: Statement): EMon[A] = 
}

