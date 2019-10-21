/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProdL1 extends Any
{ def intValue: Long
  @inline def _1 : Long = intValue
}

trait ProductL1s[A <: ProdL1] extends Any with ProductLongs[A]
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
    while (continue == true & count < length)
    {
      if (value.intValue == array(count))
      { acc = Some(count)
        continue = false
      }
      count += 1
    }
    acc
  }
  def toArrs: Arr[Arr[Long]] = mapArrSeq(el => Arr(el.intValue))
  def foreachArr(f: Arr[Long] => Unit): Unit = foreach(el => f(Arr(el.intValue)))
}

trait ProductL1sBuff[A <: ProdL1, M <: ProductL1s[A]] extends Any with ProductLongsBuff[A, M]
{ override def append(newElem: A): Unit = { buffer.append(newElem._1); () }
}