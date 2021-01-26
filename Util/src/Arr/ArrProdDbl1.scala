/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait ProdDbl1 extends Any
{ def dblValue: Double
  @inline def _1 : Double = dblValue
}

trait ArrProdDbl1[A <: ProdDbl1] extends Any with DblNArr[A]
{
  final override def productSize: Int = 1
  def newElem(dblValue: Double): A
  final override def apply(index: Int): A = newElem(arrayUnsafe(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = arrayUnsafe(index) = elem.dblValue
}
