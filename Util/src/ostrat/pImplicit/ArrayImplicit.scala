/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pImplicit

/** Extension methods for Array[Double] class. Brought into scope by the arrayDoubleToImplicit method in package object. */
class ArrayDoubleImplicit(val thisArray:Array[Double]) extends AnyVal
{ /** Foreach with iterator. */ 
  def iForeach(f: (Int, Double) => Unit, indexStart: Int = 0): Unit =
  { var index = indexStart
    thisArray.foreach{d => f(index, d); index += 1 }
  }
  
  def str: String = thisArray.foldLeft("Array(")(_ + _.toString - ",")
}

/** Extension methods for Array[A] class */
class ArrayImplicit[A <: AnyRef](val thisArray: Array[A]) extends AnyVal
{ //s def str: String = "Array
  /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the 
  *  tail value */
  def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisArray.length == 0) ifEmpty else fNonEmpty(thisArray(0))
   
  def valueProducts[B <: ProductValues[A]](implicit factory: Int => B): B =
  { val length = thisArray.length
    val valProds = factory(length)
    var count = 0
    while (count < length)
    { valProds.setElem(count, thisArray(count))
      count += 1
    }
    valProds
  }
//  def toStrFold(seperator: String = "", f: A => String = _.toString): String =
//    thisArray.ifEmpty("", thisArray.tail.foldLeft(f(thisArray.head))(_ - seperator - f(_)))   
//  def commaFold(fToStr: A => String = _.toString): String = thisArray.toStrFold(", ", fToStr)
//  def semiFold(fToStr: A => String = _.toString): String = thisArray.toStrFold("; ", fToStr)
//  def toStringAlt: String = "Array" + thisArray.semiFold
}