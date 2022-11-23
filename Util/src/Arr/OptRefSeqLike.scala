/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

trait OptRefSeqLike[A <: AnyRef] extends Any
{
  def unsafeArray: Array[A]

  def unsafeApply(index: Int): Option[A] =
  { val unsafe = unsafeArray(index)
    ife(unsafe == null, None, Some(unsafe))
  }

  def foreach[U](f: Option[A] => U): Unit =
  { var count = 0
    while (count <  unsafeArray.length) {
      f(unsafeApply(count))
      count += 1
    }
  }
}

/** OptRefs is an array based collection of optional values, that uses nulls for implementation. The collection use should not have to interact with
 *  the null values directly.  */
class OptRefArr[A <: AnyRef](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with OptRefSeqLike[A]
{ @inline def elemsLen: Int = unsafeArray.length

  inline final def apply(index: Int): Option[A] = unsafeApply(index)

  /** This produces a completely new immutable collection with the element in the new collection set to the given value. The Old collection remains
   * unchanged. If you are initialising the collection in an encapsulated space before sharing a references to the collection the unsafeSetSome
   * method is preferred.*/
  def setSome(index: Int, value: A @uncheckedVariance): OptRefArr[A] =
  { val newArray = unsafeArray.clone()
    newArray(index) = value
    new OptRefArr[A](newArray)
  }

  def unsafeSet(index: Int, value: OptRef[A]): Unit = unsafeArray(index) = value.value
  def unsafeSetSome(index: Int, value: A @uncheckedVariance): Unit = unsafeArray(index) = value
  def unsafeSetNone(index: Int): Unit = unsafeArray(index) = null.asInstanceOf[A]
  def clone = new OptRefArr[A](unsafeArray.clone)

  def setOtherOptRefs[B <: AnyRef](operand: OptRefArr[B])(f: A => B): Unit =
  { var count = 0

    while (count < elemsLen)
    { val unsafe = unsafeArray(count)
      if (unsafe == null) operand.unsafeSetNone(count) else operand.unsafeSetSome(count, f(unsafe))
      count += 1
    }
  }


  def iForeach[U](f: (Option[A], Int) => U): Unit =
  { var count = 0
    while (count < elemsLen)
    { f(apply(count), count)
      count += 1
    }
  }

  def iForeach[U](startIndex: Int)(f: (Option[A], Int) => U): Unit =
  { var count = startIndex
    while (count < elemsLen)
    { f(apply(count), count)
      count += 1
    }
  }

  def foreachSome(f: A => Unit): Unit =
  { var count = 0
    while (count < elemsLen){ apply(count).foreach(f); count += 1}
  }

  def mapSomes[B, ArrT <: Arr[B]](f: A => B)(build: ArrMapBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    foreachSome(a => build.buffGrow(buff, f(a)))
    build.buffToSeqLike(buff)
  }
}

object OptRefArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): OptRefArr[A] = new OptRefArr[A](new Array[A](length))
}