package ostrat

/** Not sure how useful this trait is. It has been created for the OptRefs trait because the Scala2 compiler has problems extending ArrayLike.  */
trait ArrayLikeBase[A] extends Any
{
  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array maybe longer by a multiple of this number. */
  def elemsLen: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def lenStr: String = elemsLen.toString

  /** Performs the effectful function on each member of the collection. */
  def foreach[U](f: A => U): Unit
}
