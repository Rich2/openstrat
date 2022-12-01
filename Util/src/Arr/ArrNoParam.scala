/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._

/** This trait is for all the [[ArrSingle]] classes except [[RArr]]. All the final classes of this
 * trait have no type parameters. The primary motivation of this trait is to allow common
 * extractors. */
trait ArrNoParam[A] extends Any with Arr[A]
{ type ThisT <: ArrNoParam[A]

  def drop(n: Int): ThisT

  final def tail: ThisT = drop(1)

  def dropRight(n: Int): ThisT = ???

  final def init: ThisT = dropRight(1)

  /** Reverses the order of the elements of this sequence. */
  def reverse: ThisT

  /** append. Appends an [[Arr]] of the same final type of this [[Arr]]. */
  @targetName("appendArr") def ++(operand: ThisT): ThisT

  /** append. appends element to this [[Arr]]. */
  @targetName("append") def +%(operand: A): ThisT

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  def unsafeSameSize(length: Int): ThisT

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n => {
      val newArr = unsafeSameSize(length - 1)
      iUntilForeach(n)(i => newArr.unsafeSetElem(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.unsafeSetElem(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A, newValue: A): ThisT =
  { val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length) {
      val orig = apply(count)
      val finalVal = ife(orig == oldValue, newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def replaceAll(pred: A => Boolean, newValue: A): ThisT =
  { val newArr = unsafeSameSize(length)
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
  def modifyAll(pred: A => Boolean, fNewValue: A => A): ThisT =
  { val newArr = unsafeSameSize(length)
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