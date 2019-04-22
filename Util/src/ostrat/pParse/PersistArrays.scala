/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

class ArrayRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Array[A]]('Array, ev)
{       
  override def persistSemi(thisArray: Array[A]): String = thisArray.map(ev.persistComma(_)).semicolonFold
  override def persistComma(thisArray: Array[A]): String = thisArray.map(ev.show(_)).commaFold
  override def fromParameterStatements(sts: List[Statement]): EMon[Array[A]] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[Array[A]] = ???
}


  
  