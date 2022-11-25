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

trait ArrSingle[+A] extends Any with Arr[A]
{
  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  def unsafeSameSize(length: Int): ThisT

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match {
    case -1 => returnThis
    case n => {
      val newArr = unsafeSameSize(length - 1)
      iUntilForeach(n)(i => newArr.unsafeSetElem(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.unsafeSetElem(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A@uncheckedVariance, newValue: A@uncheckedVariance): ThisT = {
    val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(orig == oldValue, newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def replaceAll(pred: A => Boolean, newValue: A@uncheckedVariance): ThisT = {
    val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length) {
      val orig = apply(count)
      val finalVal = ife(pred(orig), newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Modifies all instances of the old value that fulfill predicate, with a new value by applying the parameter function. */
  def modifyAll(pred: A => Boolean, fNewValue: A => A@uncheckedVariance): ThisT = {
    val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length) {
      val orig = apply(count)
      val finalVal = ife(pred(orig), fNewValue(orig), orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }
}

/** This trait is for all the [[ArrSingle]] classes except [[RArr]]. All the final classes of this
 * trait have no type parameters. The primary motivation of this trait is to allow common
 * extractors. */
trait ArrNoParam[A] extends Any with ArrSingle[A]
{ type ThisT <: ArrNoParam[A]

  def drop(n: Int): ThisT

  final def tail: ThisT = drop(1)

  /** Reverses the order of the elements of this sequence. */
  def reverse: ThisT

  /** append. Appends an [[Arr]] of the same final type of this [[Arr]]. */
  @targetName("appendArr") def ++(operand: ThisT): ThisT
}