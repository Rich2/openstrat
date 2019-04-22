/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

abstract class ProductI2sBuilder[A <: ProdI2, M <: ProductI2s[A]](typeSym: Symbol) extends ProductIntsBuilder[A, M](typeSym)  
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value._1
    buf += value._2
  }
  
  import pParse._  
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)  
  override def fromParameterStatements(sts: List[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[M] = ???
} 