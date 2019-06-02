/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

class ArrayRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Array[A]]('Array, ev)
{       
  override def showSemi(thisArray: Array[A]): String = thisArray.map(ev.showComma(_)).semiFold
  override def showComma(thisArray: Array[A]): String = thisArray.map(ev.show(_)).commaFold
  override def fromParameterStatements(sts: List[Statement]): EMon[Array[A]] = ???
  override def fromClauses(clauses: List[Clause]): EMon[Array[A]] = ???
}


  
  