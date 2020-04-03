package ostrat

import scala.annotation.unchecked.uncheckedVariance

final class RefsArr[+A <: AnyRef](val unsafeArr: Array[Array[A]] @uncheckedVariance) extends AnyVal// with Arr[Refs[A]]
{ type ThisT = RefsArr[A] @uncheckedVariance
  def length: Int = unsafeArr.length
  def apply(index: Int): Refs[A] = new Refs(unsafeArr(index))
  def unsafeSetElem(i: Int, value: Refs[A] @uncheckedVariance): Unit = unsafeArr(i) = value.unsafeArr
}
