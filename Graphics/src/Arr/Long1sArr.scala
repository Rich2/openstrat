/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Long]]. These are used in [[Long1Arr]] Array[Int] based collections. */
trait Long1Elem extends Any with LongNElem
{ def intValue: Long
  @inline def _1 : Long = intValue
}

/** A specialised immutable, flat Array[Long] based collection of a type of [[Long1Elem]]s. */
trait Long1sArr[A <: Long1Elem] extends Any with LongNsArr[A]
{
  final override def elemProductNum: Int = 1
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

/** A specialised flat ArrayBuffer[long] based trait for [[Long1Elem]]s collections. */
trait Long1sBuffer[A <: Long1Elem, ArrA <: Long1sArr[A]] extends Any with LongNsBuffer[A]
{ override def grow(newElem: A): Unit = { buffer.append(newElem._1); () }
}