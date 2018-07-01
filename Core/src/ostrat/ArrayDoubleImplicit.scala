/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Not sure if this useful */
class ArrayDoubleImplicit(val thisArray:Array[Double]) extends AnyVal
{
   def iForeach(f: (Int, Double) => Unit, indexStart: Int = 0): Unit =
   {
      var index = indexStart
      thisArray.foreach{d => f(index, d); index += 1 }
   }
   def copyToRight(otherArray: Array[Double], offset: Int): Unit = iForeach((i, d) => otherArray(i + offset) = d)   
   def setProd2(index: Int, newValue: ProdD2): Unit = {thisArray(index) = newValue._1; thisArray(index + 1) = newValue._2 }
   def str: String = thisArray.foldLeft("Array(")(_ + _.toString - ",")
   def printStr(): Unit = println(str)
}