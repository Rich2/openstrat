/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all efficient classes backed by Arrays, ArrayBuffers etc. Includes sequences and classes such as polygons and line paths that are
 * encoded using sequence data. include immutable and expandable buffers. */
trait DataGen[A] extends Any
{
  /** The number of data elements in this data sequence base class. These collections use underlying mutable Arrays and ArrayBuffers. The length of
   *  the underlying Array will be this number or a multiple of this number. */
  def dataLength: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def dataLengthStr: String = dataLength.toString

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   *  methods. */
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def unsafeSetElems(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach(index){ (i, a) => unsafeSetElem(i, a) }

  def fElemStr: A @uncheckedVariance => String

  /** String specifying the type of this object. */
  def typeStr: String

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  final def elemsStr: String = dataMap(fElemStr).mkString("; ").enParenth

  final override def toString: String = typeStr + elemsStr

  /** apply method accesses the individual elements of the sequence by 0 based index. */
  @inline def indexData(index: Int): A

  /** Performs a side effecting function on each element of this sequence in order. */
  def dataForeach[U](f: A => U): Unit =
  { var i = 0
    while(i < dataLength)
    { f(indexData(i))
      i = i + 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a
   *  function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just
   *  to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for
   *  the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set
   *  the start value of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as
   *  seen for example in fold methods' (accumulator, element) => B signature. */
  def dataIForeach[U](f: (Int, A) => Any): Unit =
  { var i = 0
    while(i < dataLength)
    { f(i, indexData(i))
      i = i + 1
    }
  }

  /** Index with foreach on the data elements. Performs a side effecting function on the index and each element of the data sequence. It takes a
   *  function as a parameter. The function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just
   *  to avoid warnings about discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for
   *  the index is 0 if just the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set
   *  the start value of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as
   *  seen for example in fold methods' (accumulator, element) => B signature. */
  def dataIForeach[U](initIndex: Int)(f: (Int, A) => U): Unit =
  { var i = 0
    while(i < dataLength)
    { f(i + initIndex, indexData(i))
      i = i + 1
    }
  }

  /** Foreachs over the tail of the data sequence. */
  def dataTailForeach[U](f: A => U): Unit =
  { var count = 1
    while(count < dataLength) { f(indexData(count)); count += 1 }
  }

  /** Specialised map to an immutable [[SeqImut]] of B. For [[SeqGen]] dataMap is the same as map, but for other structures it will be different, for
   * example a PolygonLike will map to another PolgonLike. */
  def dataMap[B, ArrB <: SeqImut[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(dataLength)
    dataIForeach((i, a) => ev.arrSet(res, i, f(a)))
    res
  }

  def dataFold[B](initVal: B)(f: (B, A) => B): B =
  { var res = initVal
    dataForeach(a => res = f(res, a))
    res
  }

  /** foldLeft over the tail of the data sequence. */
  def dataTailfold[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    dataTailForeach(a => acc = f(acc, a))
    acc
  }

  def dataLast: A = indexData(dataLength - 1)
}

/** [[ShowTDec] type class for showing [[DataGen]][A] objects. */
class DataGenShowT[A, R <: DataGen[A]](val evA: ShowTDec[A]) extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.dataFold(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))
  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + evA.typeStr.enSquare + obj.dataMap(a => evA.showDecT(a, style, maxPlaces, minPlaces))
}