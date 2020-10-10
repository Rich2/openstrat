/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProdLong1 extends Any
{ def intValue: Long
  @inline def _1 : Long = intValue
}

trait ArrProdLong1[A <: ProdLong1] extends Any with ArrProdLongN[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Long): A
  final override def apply(index: Int): A = newElem(array(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = array(index) = elem.intValue

  /** This method could be made more general. */
  def findIndex(value: A): Option[Int] =
  {
    var count = 0
    var acc: Option[Int] = None
    var continue = true
    while (continue == true & count < elemsLen)
    {
      if (value.intValue == array(count))
      { acc = Some(count)
        continue = false
      }
      count += 1
    }
    acc
  }
}

trait ProductL1sBuff[A <: ProdLong1, M <: ArrProdLong1[A]] extends Any with ProductLongsBuff[A]
{ override def grow(newElem: A): Unit = { buffer.append(newElem._1); () }
}