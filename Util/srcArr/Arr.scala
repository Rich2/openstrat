/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, scala.reflect.ClassTag, unchecked.uncheckedVariance

/** Base trait for specialised immutable sequences. "Arr" is the prescript for all immutable sequence classes backed by underlying Arrays. The final
 *  classes extend AnyVal using standard Java /Javascript Arrays for their underlying storage. A lot of the time this is a compile time wrapper with
 *  no boxing run cost. */
trait Arr[+A] extends Any with Sequ[A]
{ override type ThisT <: Arr[A]

  /** Sets / mutates the head element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetHead(value: A @uncheckedVariance): Unit = setElemUnsafe(0, value)

  /** Sets / mutates the last element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetLast(value: A @uncheckedVariance): Unit = setElemUnsafe(length -1, value)

  def unsafeSetElemSeq(index: Int, elems: Iterable[A] @uncheckedVariance): Unit = elems.iForeach(index){(i, a) => setElemUnsafe(i, a) }

  def headOrNone: Any = ife(length ==0, None, apply(0))
}

/** [[Show] type class for showing [[Arr]][A] objects. */
trait ShowArr[A, R <: Arr[A]] extends ShowSeqLike[A, R]
{ override def syntaxDepthT(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))

  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + obj.map(a => evA.showDecT(a, ShowCommas, maxPlaces, minPlaces)).mkStr("; ").enParenth
}

case class ArrCounters[A](arr: Arr[A])
{ val counters: Array[Int] = new Array[Int](arr.length)

  def apply(el: A): Int =
  { val  i = arr.indexOf(el)
    val res = counters(i) + 1
    counters(i) = res
    res
  }
}