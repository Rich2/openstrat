/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProdInt1 extends Any
{ def intValue: Int
  @inline def _1 : Int = intValue
}

trait ArrProdInt1[A <: ProdInt1] extends Any with ArrProdIntN[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(array(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = array(index) = elem.intValue

  /** This method could be made more general. */
  def findIndex(value: A): EMonInt =
  {
    var count = 0
    var acc: EMonInt = NoInt
    var continue = true
    while (continue == true & count < length)
    {
      if (value.intValue == array(count))
      { acc = GoodInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    array.copyToArray(newArray)
    newArray(length) = op._1
    unsafeFromArray(newArray)
  }
}

trait ProductI1sBuff[A <: ProdInt1] extends Any with BuffProdHomoInts[A]
{ override def grow(newElem: A): Unit = { buffer.append(newElem._1); () }
}