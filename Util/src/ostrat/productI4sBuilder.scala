/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

import collection.mutable.ArrayBuffer

abstract class ProductI4sBuilder[A <: ProdI4, M <: ProductI4s[A]](typeStr: String) extends ProductIntsBuilder[A, M](typeStr)  
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value._1
    buf += value._2
    buf += value._3
    buf += value._4
  }
  
  import pParse._  
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = ???//thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)  
  override def fromParameterStatements(sts: List[Statement]): EMon[M] = ???
  override def fromClauses(clauses: List[Clause]): EMon[M] = ???
} 