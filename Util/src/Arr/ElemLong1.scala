/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Long]]. These are used in [[Long1Arr]] Array[Int] based collections. */
trait ElemLong1 extends Any with ElemLongN
{ def intValue: Long
  @inline def _1 : Long = intValue
}

/** A specialised immutable, flat Array[Long] based collection of a type of [[ElemLong1]]s. */
trait ArrLong1s[A <: ElemLong1] extends Any with ArrLongNs[A]
{
  final override def elemProdSize: Int = 1
  def newElem(intValue: Long): A
  final override def apply(index: Int): A = newElem(array(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = array(index) = elem.intValue

  /** This method could be made more general. */
  def findIndex(value: A): Option[Int] =
  {
    var count = 0
    var acc: Option[Int] = None
    var continue = true
    while (continue == true & count < dataLength)
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

/** A specialised flat ArrayBuffer[long] based trait for [[ElemLong1]]s collections. */
trait BuffLong1s[A <: ElemLong1, ArrA <: ArrLong1s[A]] extends Any with BuffLongNs[A]
{ override def grow(newElem: A): Unit = { buffer.append(newElem._1); () }
}