/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProdI1 extends Any
{ def intValue: Int
}

trait ProductI1s[A <: ProdI1] extends Any with ProductInts[A]
{
  final override def productSize: Int = 1
  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(arr(index))
  final override def setElem(index: Int, elem: A): Unit = arr(index) = elem.intValue
  
  /** This method could be made more general. */
  def findIndex(value: A): OptInt =
  {
    var count = 0
    var acc: OptInt = NoInt
    var continue = true
    while (continue == true & count < length)
    {
      if (value.intValue == arr(count))
      {
        acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }
  def toArrs: Arr[Arr[Int]] = map(el => Arr(el.intValue))
  def foreachArr(f: Arr[Int] => Unit): Unit = foreach(el => f(Arr(el.intValue)))
}