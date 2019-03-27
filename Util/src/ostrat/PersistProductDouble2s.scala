/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

abstract class PersistProductDouble2s[A <: ProdD2, R <: ProductDouble2s[A]](typeSym: Symbol) extends PersistCompound[R](typeSym) with
  DoublesMaker[A, R]
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
  }
  
  import pParse._
  override def typeStr = typeSym.name
  override def syntaxDepth = 3
  override def persistSemi(thisColl: R): String = thisColl.mapPairs(_ + ", " + _ ).mkString("; ")
  override def persistComma(thisColl: R): String = persist(thisColl)
  //override def persist(thisColl: R): String = typeStr - persistSemi(thisColl).enParenth
  override def fromParameterStatements(sts: List[Statement]): EMon[R] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[R] = ???
}