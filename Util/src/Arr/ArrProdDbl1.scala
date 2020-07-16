/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait ProdDbl1 extends Any
{ def dblValue: Int
  @inline def _1 : Double = dblValue
}

trait ArrProdDbl1[A <: ProdDbl1] extends Any with ArrProdIntN[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Int): A
}
