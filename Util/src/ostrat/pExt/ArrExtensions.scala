package ostrat
package pExt
import collection.immutable.ArraySeq, scala.reflect.ClassTag

class ArrExtensions[A](thisArr: Arr[A])
{
  def mapWith1[B, C](initC: C)(f: (A, C) => (B, C))(implicit ct: ClassTag[B]): Arr[B] =
  {
    val accB: Buff[B] = newBuff()
    var accC: C = initC
    thisArr.foreach { a =>
      val (newB, newC) = f(a, accC)
      accB += newB
      accC = newC
    }
    ArraySeq.unsafeWrapArray[B](accB.toArray)
  }

  def removeFirst(f: A => Boolean)(implicit ct: ClassTag[A]): Arr[A] = thisArr.indexWhere(f) match
  { case -1 => thisArr
    case i =>
    {
      val mutArr = new Array[A](thisArr.length - 1)
      (0 until i).foreach(i => mutArr(i) = thisArr(i))
      (i + 1).until(thisArr.length).foreach(i => mutArr(i - 1) = thisArr(i))
      mutArr.toArr
    }
  }
  /** Replaces all instances of the old value with the new value */
  def replace(oldValue: A, newValue: A): Arr[A] = thisArr.map { it => if (it == oldValue) newValue else it }

  def ifAppendArr[B >: A](b: Boolean, newElems: => Arr[B]): Arr[B] = ife(b, thisArr ++ newElems, thisArr)
}
