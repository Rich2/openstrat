/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer

class ErrBiAcc[+E <: Throwable, +A](val errsArray: Array[E] @uncheckedVariance, val succsArray: Array[A] @uncheckedVariance)
{ /** The accumulated errors. */
  def errs: RArr[E] = new RArr(errsArray)

  /** The accumulated successes. */
  def succs: RArr[A] = new RArr(succsArray)
}

class ErrBiAccBuff[+E <: Throwable, +A](val errs: ArrayBuffer[E] @uncheckedVariance, val succsArray: Array[A] @uncheckedVariance)
{ 
}