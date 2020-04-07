package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

class OptRefs[A <: AnyRef](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with ArrayBase[OptRef[A]]
{ @inline def length: Int = unsafeArray.length
  def apply(index: Int): OptRef[A] = OptRef(unsafeArray(index))

  def setSome(index: Int, value: A @uncheckedVariance): OptRefs[A] =
  { val newArray = unsafeArray.clone()
    newArray(index) = value
    new OptRefs[A](newArray)
  }

  def unsafeSet(index: Int, value: OptRef[A]): Unit = unsafeArray(index) = value.value
  def unsafeSetSome(index: Int, value: A @uncheckedVariance): Unit = unsafeArray(index) = value
  def unsafeSetNone(index: Int): Unit = unsafeArray(index) = null.asInstanceOf[A]
  def clone = new OptRefs[A](unsafeArray.clone)

  def setOtherOptRefs[B <: AnyRef](operand: OptRefs[B])(f: A => B): Unit =
  { var count = 0

    while (count < length)
    { apply(count).foldDo { operand.unsafeSetNone(count) } { a => operand.unsafeSetSome(count, f(a)) }
      count += 1
    }
  }

  override def foreach[U](f: OptRef[A] => U): Unit =
  { var count = 0
    while (count < length)
    { f(apply(count))
      count += 1
    }
  }

  def foreachSome(f: A => Unit): Unit =
  { var count = 0
    while (count < length){ apply(count).foreach(f); count += 1}
  }

  def mapSomes[B, ArrT <: Arr[B]](f: A => B)(build: ArrBuild[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    foreachSome(a => build.buffGrow(buff, f(a)))
    build.buffToArr(buff)
  }
}

object OptRefs
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): OptRefs[A] = new OptRefs[A](new Array[A](length))
}
