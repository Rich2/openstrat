package ostrat
package pExt
import scala.reflect.ClassTag

class ArrExtensions[A](thisArr: Arr[A])
{ /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed */
  def mapWithAcc[B, C](initC: C)(f: (A, C) => (B, C))(implicit ct: ClassTag[B]): Arr[B] =
  { val accB: Buff[B] = Buff()
    var accC: C = initC
    thisArr.foreach { a =>
      val (newB, newC) = f(a, accC)
      accB += newB
      accC = newC
    }
    accB.toArr
  }

  /** Removes the first element that satisfies the function and returns a new Arr. Returns itself if no matching element found. */
  def removeFirst(f: A => Boolean)(implicit ct: ClassTag[A]): Arr[A] = thisArr.indexWhere(f) match
  { case -1 => thisArr
    case n =>
    { val mutArr = new Array[A](thisArr.length - 1)
      iUntilForeach(0, n)(i => mutArr(i) = thisArr(i))
      iUntilForeach(n + 1, thisArr.length)(i => mutArr(i - 1) = thisArr(i))
      mutArr.toArr
    }
  }

  /** Replaces all instances of the old value with the new value. */
  def replace(oldValue: A, newValue: A): Arr[A] = thisArr.map { it => if (it == oldValue) newValue else it }

  def reverseForeach(f: A => Unit): Unit =
  { var count = thisArr.length - 1
    while(count >= 0){ f(thisArr(count)); count -= 1}
  }

  def ifAppendArr[B >: A](b: Boolean, newElems: => Arr[B]): Arr[B] = ife(b, thisArr ++ newElems, thisArr)
  def optAppend[B >: A](optElem: Option[B]): Arr[B] = optElem.fold[Arr[B]](thisArr)(b => thisArr :+ b)
  def optAppends[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
}