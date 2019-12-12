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

  def optAppend(optElems: Option[Arr[A]]): Arr[A] = optElems.fold[Arr[A]](thisArr)(bs => thisArr ++ bs)

  /** Concatenates Arr if Some. Returns original Arr if operand is None. */
  def optConcat[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
}