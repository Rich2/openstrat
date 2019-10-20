package ostrat
import annotation.unchecked.uncheckedVariance

trait ArrImut[+A] extends Any with ArrayBased[A]
{ type ThisT <: ArrImut[A]
  def buildThis(length: Int): ThisT

  def removeFirst(f: A => Boolean): ThisT = indexWhere(f) match
  { case -1 => returnThis
    case n =>
    { val newArr = buildThis(length - 1)
      iUntilForeach(0, n)(i => newArr.setUnsafe(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.setUnsafe(i - 1, apply(i)))
      newArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A @uncheckedVariance, newValue: A@uncheckedVariance): ThisT =
  { val newArr = buildThis(length)
    foreach( el => ife(el == oldValue, newValue, el))
    newArr
  }

 // def ifAppendArr[B >: A](b: Boolean, newElems: => Arr[B]): Arr[B] = ife(b, thisArr ++ newElems, thisArr)
 // def optAppend[B >: A](optElem: Option[B]): Arr[B] = optElem.fold[Arr[B]](thisArr)(b => thisArr :+ b)
 // def optAppends[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
}

trait BuffArr[A] extends Any with ArrayBased[A]

