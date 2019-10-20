package ostrat
import annotation.unchecked.uncheckedVariance

trait ArrImut[+A] extends Any with ArrayBased[A]
{ type ThisT <: ArrImut[A]
  def buildThis(length: Int): ThisT
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit
  def unsafeSetHead(value: A @uncheckedVariance): Unit = unsafeSetElem(0, value)
  def unsafeSetLast(value: A @uncheckedVariance): Unit = unsafeSetElem(length -1, value)

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = buildThis(length - 1)
      iUntilForeach(0, n)(i => newArr.unsafeSetElem(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.unsafeSetElem(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A @uncheckedVariance, newValue: A@uncheckedVariance): ThisT =
  { val newArr = buildThis(length)
    foreach( el => ife(el == oldValue, newValue, el))
    newArr
  }
}

trait BuffArr[A] extends Any with ArrayBased[A]

