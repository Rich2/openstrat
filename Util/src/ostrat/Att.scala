package ostrat
import reflect.ClassTag

/** Using Att as temporary name, can be switched to Arr later to replace type alias for ArraySeq. */
class Att[+A](val array: Array[A] @scala.annotation.unchecked.uncheckedVariance) extends AnyVal
{ @inline def length = array.length
  def apply(index: Int): A = array(index)
  @inline def foreach[U](f: A => U): Unit = array.foreach(f)

  def map[B](f: A => B)(implicit ct: ClassTag[B]): Att[B] =
  { var count = 0
    val newArray: Array[B] = new Array[B](length)
    while (count < length) { newArray(count) = f(array(count)); count += 1}
    new Att(newArray)
  }
}

object Att
{ def apply[A](inp: A*)(implicit ct: ClassTag[A]): Att[A] = new Att[A](inp.toArray)
}
