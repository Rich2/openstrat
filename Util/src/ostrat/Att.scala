package ostrat
import reflect.ClassTag

/*
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

  //def flatMap[B](f: A => B)(implicit ct: ClassTag[B]): Att[B] =

  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed */
  def mapWithAcc[B, C](initC: C)(f: (A, C) => (B, C))(implicit ct: ClassTag[B]): Arr[B] =
  { val accB: Buff[B] = Buff()
    var accC: C = initC
    foreach { a =>
      val (newB, newC) = f(a, accC)
      accB += newB
      accC = newC
    }
    accB.toArr
  }

  /** Replaces all instances of the old value with the new value */
 // def replace(oldValue: A, newValue: A): Att[A] = map { it => if (it == oldValue) newValue else it }

  def reverseForeach(f: A => Unit): Unit =
  { var count = length - 1
    while(count >= 0){ f(apply(count)); count -= 1}
  }

  /*def ifAppendArr[B >: A](b: Boolean, newElems: => Att[B]): Att[B] = ife(b, this ++ newElems, this)
  def optAppend[B >: A](optElem: Option[B]): Att[B] = optElem.fold[Arr[B]](this)(b => this :+ b)
  def optAppends[B >: A](optElems: Option[Att[B]]): Arr[B] = optElems.fold[Arr[B]](this)(bs => this ++ bs)*/
}

*/
