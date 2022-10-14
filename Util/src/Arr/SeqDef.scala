/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all immutable classes that use a backing Array for efficient storage. This includes immutable sequences [[SeqImut]], but also
 *  polygons and line paths that are specified by data sequences. */
trait SeqDef[+A] extends Any with SeqLike[A @uncheckedVariance]
{ type ThisT <: SeqDef[A]

  /** Performs a side effecting function on each element of this sequence in order. */
  def dataForeach[U](f: A => U): Unit = {
    var i = 0
    while (i < sdLength) {
      f(sdIndex(i))
      i = i + 1
    }
  }

  /** Foreachs over the tail of the data sequence. */
  def dataTailForeach[U](f: A => U): Unit = {
    var count = 1
    while (count < sdLength) {
      f(sdIndex(count)); count += 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a
   * function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just
   * to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for
   * the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set
   * the start value of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as
   * seen for example in fold methods' (accumulator, element) => B signature. */
  def dataIForeach[U](f: (Int, A) => Any): Unit = {
    var i = 0
    while (i < sdLength) {
      f(i, sdIndex(i))
      i = i + 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a
   * function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just
   * to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for
   * the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set
   * the start value of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as
   * seen for example in fold methods' (accumulator, element) => B signature. */
  def dataIForeach[U](initIndex: Int)(f: (Int, A) => U): Unit = {
    var i = 0
    while (i < sdLength) {
      f(i + initIndex, sdIndex(i))
      i = i + 1
    }
  }

  /** Specialised map to an immutable [[SeqImut]] of B. For [[Sequ]] dataMap is the same as map, but for other structures it will be different, for
   * example a PolygonLike will map to another PolgonLike. */
  def dataMap[B, ArrB <: SeqImut[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB = {
    val res = ev.newArr(sdLength)
    dataIForeach((i, a) => ev.arrSet(res, i, f(a)))
    res
  }

  /** defining -sequence fold. */
  def dataFold[B](initVal: B)(f: (B, A) => B): B = {
    var res = initVal
    dataForeach(a => res = f(res, a))
    res
  }

  /** Performs a side effecting function on each element of the defining-sequence in reverse order. The function may return Unit. If it does return a
   *  non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method
   *  users. */
  def dsReverseForeach[U](f: A => U): Unit = {
    var count = sdLength
    while (count > 0) {
      count -= 1; f(sdIndex(count))
    }
  }

  /** Last element of the defining sequence. */
  def dsLast: A = sdIndex(sdLength - 1)

  /** FoldLeft over the tail of the defining sequence. */
  def dsTailfold[B](initial: B)(f: (B, A) => B) = {
    var acc: B = initial
    dataTailForeach(a => acc = f(acc, a))
    acc
  }

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  override def elemsStr: String = dataMap(fElemStr).mkString("; ").enParenth
}

trait RefsSeqDefImut[+A] extends Any with SeqDef[A]
{ type ThisT <: RefsSeqDefImut[A]
  def unsafeArray: Array[A] @uncheckedVariance

  def fromArray(array: Array[A] @uncheckedVariance): ThisT

  /** Copy's the backing Array[[AnyRef]] to a new Array[AnyRef]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): ThisT = fromArray(new Array[AnyRef](length).asInstanceOf[Array[A]])

  override final def sdLength: Int = unsafeArray.length
  override final def fElemStr: A @uncheckedVariance => String = _.toString
  override final def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = unsafeArray(i) = value
  override final def sdIndex(index: Int): A = unsafeArray(index)
}