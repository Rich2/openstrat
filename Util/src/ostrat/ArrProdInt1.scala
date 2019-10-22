/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProdI1 extends Any
{ def intValue: Int
  @inline def _1 : Int = intValue
}

trait ArrProdInt1[A <: ProdI1] extends Any with ArrProdIntN[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(array(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = array(index) = elem.intValue

  /** This method could be made more general. */
  def findIndex(value: A): OptInt =
  {
    var count = 0
    var acc: OptInt = NoInt
    var continue = true
    while (continue == true & count < length)
    {
      if (value.intValue == array(count))
      { acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }
  def toArrs: Arr[Arr[Int]] = mapArrSeq(el => Arr(el.intValue))
  def foreachArr(f: Arr[Int] => Unit): Unit = foreach(el => f(Arr(el.intValue)))
}

trait ProductI1sBuff[A <: ProdI1, M <: ArrProdInt1[A]] extends Any with ArrBuffHomoInts[A, M]
{ override def append(newElem: A): Unit = { buffer.append(newElem._1); () }
}