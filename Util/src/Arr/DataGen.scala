/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all efficient classes backed by Arrays, ArrayBuffers etc. Includes sequences and classes such as polygons and line paths that are
 * encoded using sequence data. include immutable and expandable buffers. */
trait DataGen[A] extends Any
{
  /** The number of elements in the collection. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying
   * Array will be this number or a multiple of this number. */
  def elemsNum: Int

  /** Just a handy short cut to give the length of this collection as a string. */
  def elemsLenStr: String = elemsNum.toString

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   *  methods. */
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit

  /** Sets / mutates elements in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory methods. */
  def unsafeSetElems(index: Int, elems: A @uncheckedVariance *): Unit = elems.iForeach((a, i) => unsafeSetElem(i, a), index)

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
  { var count = 0
    while(count < elemsNum)
    { f(indexData(count))
      count = count + 1
    }
  }

  /** Performs a side effecting function on each element of this sequence in order. */
  def dataIForeach[U](f: (A, Int) => U): Unit =
  { var count = 0
    while(count < elemsNum)
    { f(indexData(count), count)
      count = count + 1
    }
  }

  /** Foreachs over the tail of the data sequence. */
  def dataTailForeach[U](f: A => U): Unit =
  { var count = 1
    while(count < elemsNum) { f(indexData(count)); count += 1 }
  }

  /** Specialised map to an immutable ArrBase of B. */
  def dataMap[B, ArrB <: ArrBase[B]](f: A => B)(implicit ev: ArrBuilder[B, ArrB]): ArrB =
  { val res = ev.newArr(elemsNum)
    dataIForeach((a, i) => ev.arrSet(res, i, f(a)))
    res
  }

  /** foldLeft over the tail of the data sequence. */
  def dataTailfold[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    dataTailForeach(a => acc = f(acc, a))
    acc
  }

  def dataLast: A = indexData(elemsNum - 1)
}