/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

/** OptRefs is an array based collection of optional values, that uses nulls for implementation. The collection use should not have to interact with
 *  the null values directly.  */
final class OptRefArr[A <: AnyRef](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with Arr[Option[A]]
{ override type ThisT = OptRefArr[A]
  override def typeStr: String = "OptRefArr"
  override def unsafeSetElem(i: Int, value: Option[A]): Unit = ???
  @inline def length: Int = unsafeArray.length
  override def fElemStr: Option[A] => String = ???
  def apply(index: Int): Option[A] = unsafeApply(index)

  def unsafeApply(index: Int): Option[A] = {
    val unsafe = unsafeArray(index)
    ife(unsafe == null, None, Some(unsafe))
  }
  override def elemsStr: String = ???

  override def foreach[U](f: Option[A] => U): Unit =
  { var count = 0
    while (count < unsafeArray.length) {
      f(unsafeApply(count))
      count += 1
    }
  }

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

    while (count < length)
    { val unsafe = unsafeArray(count)
      if (unsafe == null) operand.unsafeSetNone(count) else operand.unsafeSetSome(count, f(unsafe))
      count += 1
    }
  }

  override def iForeach[U](f: (Int, Option[A]) => U): Unit =
  { var i = 0
    while (i < length)
    { f(i, apply(i))
      i += 1
    }
  }

  override def iForeach[U](startIndex: Int)(f: (Int, Option[A]) => U): Unit =
  { var i = startIndex
    while (i < length)
    { f(i, apply(i))
      i += 1
    }
  }

  def foreachSome(f: A => Unit): Unit =
  { var count = 0
    while (count < length){ apply(count).foreach(f); count += 1}
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