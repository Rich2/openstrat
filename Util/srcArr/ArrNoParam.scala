/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** This trait is for all the [[ArrSingle]] classes except [[RArr]]. All the final classes of this trait have no type parameters. The primary motivation of this
 * trait is to allow common extractors. */
trait ArrNoParam[A] extends Any, Arr[A]
{ /** The final type of this class many method operands and return values take this type. */
  type ThisT <: ArrNoParam[A]

  /** Returns new [[Arr]] with the first N elements dropped. */
  def drop(n: Int): ThisT

  /** Returns new [[Arr]] without the first element. */
  final def tail: ThisT = drop(1)

  /** Returns new [[Arr]] with the last N elements dropped. */
  def dropRight(n: Int): ThisT

  /** Returns new [[Arr]] without the last element. */
  final def init: ThisT = dropRight(1)

  /** Reverses the order of the elements of this sequence. */
  def reverse: ThisT

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  def unsafeSameSize(length: Int): ThisT

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = unsafeSameSize(length - 1)
      iUntilForeach(n)(i => newArr.setElemUnsafe(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.setElemUnsafe(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A, newValue: A): ThisT =
  { val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(orig == oldValue, newValue, orig)
      newArr.setElemUnsafe(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def replaceAll(pred: A => Boolean, newValue: A): ThisT =
  { val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), newValue, orig)
      newArr.setElemUnsafe(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Modifies all instances of the old value that fulfill predicate, with a new value by applying the parameter function. */
  def modifyAll(pred: A => Boolean, fNewValue: A => A): ThisT =
  { val newArr = unsafeSameSize(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), fNewValue(orig), orig)
      newArr.setElemUnsafe(count, finalVal)
      count += 1
    }
    newArr
  }
}