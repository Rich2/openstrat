/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for specialised immutable Arrays. The final classes extend AnyVal using standard Java /Javascript Arrays for their underlying storage.
 *  A lot of the time this is a compile time wrapper with no boxing run cost. */
trait ArrImut[+A] extends Any with ArrayLike[A]
{ override type ThisT <: ArrImut[A]

  /** String specifying the type of this object. */
  def typeStr: String

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised ArrT of the this [[ArrImut]]'s final type. */
  def unsafeNew(length: Int): ThisT

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   *  methods. */
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def unsafeSetElems(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach((a, i) => unsafeSetElem(i, a), index)

  /** Sets / mutates the head element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetHead(value: A @uncheckedVariance): Unit = unsafeSetElem(0, value)

  /** Sets / mutates the last element in the Arr. This method should rarely be needed by end users, but is used by initialisation and factory
   * methods. */
  def unsafeSetLast(value: A @uncheckedVariance): Unit = unsafeSetElem(elemsLen -1, value)

  def unsafeSetElemSeq(index: Int, elems: Iterable[A] @uncheckedVariance): Unit = elems.iForeach((a, i) => unsafeSetElem(i, a), index)
  def fElemStr: A @uncheckedVariance => String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  final def elemsStr: String = map(fElemStr).mkString("; ").enParenth

  final override def toString: String = typeStr + elemsStr

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = unsafeNew(elemsLen - 1)
      iUntilForeach(0, n)(i => newArr.unsafeSetElem(i, apply(i)))
      iUntilForeach(n + 1, elemsLen)(i => newArr.unsafeSetElem(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A @uncheckedVariance, newValue: A@uncheckedVariance): ThisT =
  { val newArr = unsafeNew(elemsLen)
    var count = 0

    while (count < elemsLen)
    { val orig = apply(count)
      val finalVal = ife(orig == oldValue, newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def replaceWhere(pred: A => Boolean, newValue: A@uncheckedVariance): ThisT =
  { val newArr = unsafeNew(elemsLen)
    var count = 0

    while (count < elemsLen)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def modifyWhere(pred: A => Boolean, fNewValue: A => A @uncheckedVariance): ThisT =
  { val newArr = unsafeNew(elemsLen)
    var count = 0

    while (count < elemsLen)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), fNewValue(orig), orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }
  
  def find(f: A => Boolean): Option[A] =
  {
    var count = 0
    var res: Option[A] = None
    while (count < elemsLen & res.isEmpty)
    {
      val el = apply(count)
      if (f(el)) res = Some(el)
      else count += 1
    }
    res
  }
}