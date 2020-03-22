package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

class OptRefs[+A <: AnyRef](val unsafeArr: Array[A] @uncheckedVariance) extends AnyVal
{
  def apply(index: Int): OptRef[A] = OptRef(unsafeArr(index))
  def setSome(index: Int, value: A @uncheckedVariance): Unit = unsafeArr(index) = value
  def setNone(index: Int): Unit = unsafeArr(index) = null.asInstanceOf[A]
}

object OptRefs
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): OptRefs[A] = new OptRefs[A](new Array[A](length))
}
