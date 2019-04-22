/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Both Persists and Builds ProductD2s collection classes. */
abstract class ProductD2sBuilder[A <: ProdD2, M <: ProductD2s[A]](typeSym: Symbol) extends ProductDsBuilder[A, M](typeSym)  
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
  }
  
  import pParse._  
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def persistSemi(thisColl: M): String = thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def persistComma(thisColl: M): String = show(thisColl)
  //override def show(thisColl: R): String = typeStr - persistSemi(thisColl).enParenth
  override def fromParameterStatements(sts: List[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[M] = ???
}