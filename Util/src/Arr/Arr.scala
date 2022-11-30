/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance

/** Base trait for specialised immutable sequences. "Arr" is the prescript for all immutable sequence classes backed by underlying Arrays. The final
 *  classes extend AnyVal using standard Java /Javascript Arrays for their underlying storage. A lot of the time this is a compile time wrapper with
 *  no boxing run cost. */
trait Arr[+A] extends Any with Sequ[A]
{ override type ThisT <: Arr[A]

  /** Sets / mutates the head element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetHead(value: A @uncheckedVariance): Unit = unsafeSetElem(0, value)

  /** Sets / mutates the last element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetLast(value: A @uncheckedVariance): Unit = unsafeSetElem(length -1, value)

  def unsafeSetElemSeq(index: Int, elems: Iterable[A] @uncheckedVariance): Unit = elems.iForeach(index){(i, a) => unsafeSetElem(i, a) }
}

/** [[ShowT] type class for showing [[DataGen]][A] objects. */
class ArrShowT[A, R <: Arr[A]](val evA: ShowT[A]) extends ShowTSeqLike[A, R]
{ override def syntaxDepthT(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))

  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + evA.typeStr.enSquare + obj.map(a => evA.showDecT(a, style, maxPlaces, minPlaces))
}