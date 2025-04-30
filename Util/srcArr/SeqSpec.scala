/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Sequence specified objects. An immutable class that can be specified by a sequence of elements. Uses a backing Array for efficient storage. Examples include
 * polygons and line paths that can be specified by a sequence of points. It is possible for the specifying sequence to be a reference type. */
trait SeqSpec[+A] extends Any, SeqLike[A @uncheckedVariance]
{ type ThisT <: SeqSpec[A]
  
  /** Performs a side effecting function on each element of the specifying sequence in order. */
  override def foreach[U](f: A => U): Unit =
  { var i = 0
    while (i < numElems)
    { f(elem(i))
      i = i + 1
    }
  }

  /** Foreachs over the tail of the specifying sequence. Performs a side effecting function on each element of the tail of the specifying sequence in order. */
  def tailForeach[U](f: A => U): Unit =
  { var i = 1
    while (i < numElems)
    { f(elem(i))
      i += 1
    }
  }

  /** Foreachs over the inner of the specifying sequence, excludes the first and last element. Performs a side effecting function on each element of the tail of
   * the specifying sequence in order. */
  def innerForeach[U](f: A => U): Unit =
  { var i = 1
    while (i < numElems - 1)
    { f(elem(i));
      i += 1
    }
  }

  /** Index with foreach on the specifying sequence elements. Performs a side effecting function on the index and each element of the specifying sequence. It
   * takes a function as a parameter. The function may return Unit. If it does return a non-Unit value it is discarded. The [U] type parameter is there just to
   * avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0
   * if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value of the
   * index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   * (accumulator, element) => B signature. */
  def iForeach[U](f: (Int, A) => Any): Unit =
  { var i = 0
    while (i < numElems)
    { f(i, elem(i))
      i = i + 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a function as a
   * parameter. The function may return Unit. If it does return a non-Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   * discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just the function
   * parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value of the index. Note the function
   * signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   * (accumulator, element) => B signature. */
  def iForeach[U](initIndex: Int)(f: (Int, A) => U): Unit =
  { var i = 0
    while (i < numElems)
    { f(i + initIndex, elem(i))
      i = i + 1
    }
  }

  /** Specialised map to an immutable [[Arr]] of B. For [[Sequ]] dataMap is the same as map, but for other structures it will be different, for example a
   *  PolygonLike will map to another PolygonLike. */
  def mapArr[B, ArrB <: Arr[B]](f: A => B)(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val res = ev.uninitialised(numElems)
    iForeach((i, a) => ev.indexSet(res, i, f(a)))
    res
  }

  /** foldLeft over the specifying sequence. */
  def foldLeft[B](initVal: B)(f: (B, A) => B): B =
  { var res = initVal
    foreach(a => res = f(res, a))
    res
  }

  /** Performs a side effecting function on each element of the specifying-sequence in reverse order. The function may return Unit. If it does return a non-Unit
   * value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method users. */
  def reverseForeach[U](f: A => U): Unit =
  { var i = numElems
    while (i > 0)
    { i -= 1
      f(elem(i))
    }
  }

  /** Last element of the specifying sequence. */
  def last: A = elem(numElems - 1)

  /** FoldLeft over the tail of the specifying sequence. */
  def tailFold[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    tailForeach(a => acc = f(acc, a))
    acc
  }

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  override def elemsStr: String = mapArr(fElemStr).mkStr("; ").enParenth
}