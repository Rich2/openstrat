/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

import collection.mutable.ArrayBuffer

abstract class ProductI4sBuilder[A <: ProdI4, M <: ProductI4s[A]](typeSym: Symbol) extends ProductIntsBuilder[A, M](typeSym)  
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
  override def persistSemi(thisColl: M): String = ???//thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def persistComma(thisColl: M): String = show(thisColl)
  //override def show(thisColl: R): String = typeStr - persistSemi(thisColl).enParenth
  override def fromParameterStatements(sts: List[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Seq[Clause]): EMon[M] = ???
} 