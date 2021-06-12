/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Not sure how useful this trait is. It has been created for the OptRefs trait because the Scala2 compiler has problems extending ArrayLike.  */
trait ArrayLikeBase[A] extends Any
{
  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array maybe longer by a multiple of this number. */
  def elemsLen: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def lenStr: String = elemsLen.toString

  /** Performs the effectual function on each member of the collection. */
  def foreach[U](f: A => U): Unit

  /** foreach with index starting at 0. */
  def iForeach[U](f: (A, Int) => U): Unit

  /** foreach with index. */
  def iForeach[U](startIndex: Int)(f: (A, Int) => U): Unit
}