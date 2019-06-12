package ostrat
package pImplicit
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

  def removeFirst(f: A => Boolean): Arr[A] = ???
  def ifAppendArr[B >: A](b: Boolean, newElems: => Arr[B]): Arr[B] = ife(b, thisArr ++ newElems, thisArr)
}
