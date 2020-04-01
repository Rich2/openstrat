package ostrat
import annotation.unchecked.uncheckedVariance

/** Immutable Array. The final classes extend AnyVal using standard Java /Javascript Arrays for their underlying storage. A lot of the time this is a
 * compile time wrapper with no boxing run cost. Name will be shortened to Arr once the laias for ArraySeq has been removed. */
trait Arr[+A] extends Any with ArrayLike[A]
{ type ThisT <: Arr[A]
  //def unsafeNew(length: Int): ThisT
  def unsafeNew(length: Int): ThisT
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit
  def unsafeSetElems(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach((a, i) => unsafeSetElem(i, a), index)
  def unsafeSetHead(value: A @uncheckedVariance): Unit = unsafeSetElem(0, value)
  def unsafeSetLast(value: A @uncheckedVariance): Unit = unsafeSetElem(length -1, value)
  def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int ): Unit = ???
  def unsafeSetElemSeq(index: Int, elems: Iterable[A] @uncheckedVariance) = elems.iForeach((a, i) => unsafeSetElem(i, a), index)

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = unsafeNew(length - 1)
      iUntilForeach(0, n)(i => newArr.unsafeSetElem(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.unsafeSetElem(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A @uncheckedVariance, newValue: A@uncheckedVariance): ThisT =
  { val newArr = unsafeNew(length)
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
  def replaceWhere(pred: A => Boolean, newValue: A@uncheckedVariance): ThisT =
  { val newArr = unsafeNew(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), newValue, orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }

  /** Replaces all instances of the old value that fulfill predicate with the new value. */
  def modifyWhere(pred: A => Boolean, fNewValue: A => A @uncheckedVariance): ThisT =
  { val newArr = unsafeNew(length)
    var count = 0

    while (count < length)
    { val orig = apply(count)
      val finalVal = ife(pred(orig), fNewValue(orig), orig)
      newArr.unsafeSetElem(count, finalVal)
      count += 1
    }
    newArr
  }
}