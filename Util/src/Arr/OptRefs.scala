package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

class OptRefs[A <: AnyRef](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with ArrayBase[OptRef[A]]
{ @inline def length: Int = unsafeArray.length
  def apply(index: Int): OptRef[A] = OptRef(unsafeArray(index))
  def setSome(index: Int, value: A @uncheckedVariance): Unit = unsafeArray(index) = value
  def setNone(index: Int): Unit = unsafeArray(index) = null.asInstanceOf[A]

  def setOtherOptRefs[B <: AnyRef](operand: OptRefs[B])(f: A => B): Unit =
  { var count = 0

    while (count < length)
    { apply(count).foldDo { operand.setNone(count) } { a => operand.setSome(count, f(a)) }
      count += 1
    }
  }
  def foreachSome(f: A => Unit): Unit =
  { var count = 0
    while (count < length){ apply(count).foreach(f); count += 1}
  }

  def mapSomes[B, ArrT <: ArrImut[B]](f: A => B)(build: ArrBuild[B, ArrT]): ArrT =
  { val buff = build.buffNew()
    foreachSome(a => build.buffGrow(buff, f(a)))
    build.buffToArr(buff)
  }
}

object OptRefs
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): OptRefs[A] = new OptRefs[A](new Array[A](length))
}
