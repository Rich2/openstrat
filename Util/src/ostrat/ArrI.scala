package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

class ArrBuild

trait ArrN[+T] extends Any
{
  //@uncheckedVariance def array: Array[T]
  //def length: Int = array.length
}
object ArrB
{

}

class ArrR[+T <: AnyRef](val array: Array[T] @uncheckedVariance) extends AnyVal with
  ArrN[T]
{
  def length: Int = array.length
  @inline def apply(index: Int): T = array(index)
  def ++[AA >: T <: AnyRef](operand: ArrR[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrR[AA] =
  {
    var newArray: Array[AA] = new Array[AA](length + operand.length)
    var count = 0
    while (count < length)
    { newArray(count) = apply(count)
      count += 1
    }
    var count2 = 0
    while (count2 < operand.length)
    { newArray(count) = operand(count2)
      count += 1; count2 += 1
    }
    new ArrR(newArray)
  }
}

object ArrR
{
  def apply[A <: AnyRef](inp: A*)(implicit ct: ClassTag[A]): ArrR[A] = new ArrR[A](inp.toArray)
}


class ArrI(val array: Array[Int]) extends AnyVal with ArrN[Int]
{

}
class ArrD(val array: Array[Double]) extends AnyVal with ArrN[Double]
{

}