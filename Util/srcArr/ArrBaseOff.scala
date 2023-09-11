/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait ArrBaseOff[A, ArrT <: Arr[A]] extends Any
{ def offset0: Int
  @inline def apply(index: Int)(implicit arr: ArrT): A
  def lenStr(implicit arr: ArrT): String = length.toString
  def length(implicit arr: ArrT): Int
  inline def offset1: Int = offset0 + 1
  inline def offset2: Int = offset0 + 2
  inline def offset3: Int = offset0 + 3
  inline def offset4: Int = offset0 + 4
  def forall(p: A => Boolean)(implicit arr: ArrT): Boolean = ??? //forall(0, length - 1)(p)

  def forN(endIndex: Int, p: A => Boolean)(implicit arr: ArrT): Boolean =
  {
    if(endIndex >= length) false
    else
    { var count = 0
      var acc = true
      while(acc == true & count <= endIndex)
        if (p(apply(count))) count += 1
        else acc = false
      acc
    }
  }
  def forRange(startIndex: Int, endIndex: Int, p: A => Boolean)(implicit arr: ArrT): Boolean = ???

  def notPredicateLength(p: A => Boolean)(implicit arr: ArrT): Int = predicateLength(a => !p(a))

  def predicateLength(p: A => Boolean)(implicit arr: ArrT): Int =
  { var count = 0
    var continue = true
    while(count < length & continue)
    { if (p(apply(count))) count += 1 else continue = false }
    count
  }
}