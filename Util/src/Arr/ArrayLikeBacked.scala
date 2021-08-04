/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all classes backed by Arrays, ArrayBuffers etc. */
trait ArrayLikeBacked[A] extends Any
{
  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array will be this number or a multiple of this number. */
  def elemsLen: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def elemsLenStr: String = elemsLen.toString

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   *  methods. */
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit
}