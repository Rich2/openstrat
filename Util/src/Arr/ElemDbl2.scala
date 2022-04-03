/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[Dbl2Arr]] Array[Double] based collections. */
trait ElemDbl2 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: ElemDbl2): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: ElemDbl2, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
}

/** A sequence-defined specialised immutable, flat Array[Double] based trait defined by a sequence of a type of [[ElemDbl2]]s. */
trait Dbl2SeqDef[A <: ElemDbl2] extends Any with DblNSeqDef[A]
{
  /** Method for creating new data elements from 2 [[Double]]s In the case of [[Dbl2Arr]] this will be thee type of the elements of the sequence. */
  def seqDefElem(d1: Double, d2: Double): A

  override def elemProdSize: Int = 2

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { unsafeArray(2 * index) = elem.dbl1
    unsafeArray(2 * index + 1) = elem.dbl2
  }

  override def sdIndex(index: Int): A = seqDefElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](sdLength)
    var count = 0
    while(count < sdLength){ res(count) = unsafeArray(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](sdLength)
    var count = 0
    while(count < sdLength){ res(count) = unsafeArray(count * 2 + 1); count += 1 }
    res
  }

  def dataForeachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < sdLength) { f(unsafeArray(count * 2), unsafeArray(count * 2 + 1)); count += 1 }
  }

  /** Maps the 2 [[Double]]s of each element to a new [[Array]][Double]. */
  def unsafeMap(f: A => A): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, arrLen, 2){ i =>
      val newElem = f(seqDefElem(unsafeArray(i), unsafeArray(i + 1)))
      newArray(i) = newElem.dbl1
      newArray(i + 1) = newElem.dbl2
    }
    newArray
  }

  /** Maps the 1st [[Double]] of each element to a new [[Array]][Double], copies the 2nd elements. */
  def unsafeD1Map(f: Double => Double): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, arrLen, 2){ i => newArray(i) = f(unsafeArray(i)) }
    iUntilForeach(1, arrLen, 2){ i => newArray(i) = unsafeArray(i) }
    newArray
  }

  /** Maps the 2nd [[Double]] of each element with the parameter function to a new [[Array]][Double], copies the 1st [[Double]] of each element. */
  def unsafeD2Map(f: Double => Double): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, arrLen, 2){ i => newArray(i) = unsafeArray(i) }
    iUntilForeach(1, arrLen, 2){ i => newArray(i) = f(unsafeArray(i)) }
    newArray
  }
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[ElemDbl2]]s. */
trait Dbl2Arr[A <: ElemDbl2] extends Any with DblNArr[A] with Dbl2SeqDef[A]
{ type ThisT <: Dbl2Arr[A]
  final override def length: Int = unsafeArray.length / 2
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def getPair(index: Int): (Double, Double) = (unsafeArray(2 * index), unsafeArray(2 * index + 1))

  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < sdLength) { f(unsafeArray(count * 2), unsafeArray(count * 2 + 1)); count += 1 }
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Double](sdLength + elemProdSize)
    unsafeArray.copyToArray(newArray)
    newArray(sdLength) = op.dbl1
    newArray(sdLength + 1) = op.dbl2
    unsafeFromArray(newArray)
  }

  override def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl2]]. The first type parameter is
 *  called B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2ArrBuilder[B <: ElemDbl2, ArrB <: Dbl2Arr[B]] extends DblNArrBuilder[B, ArrB]
{ type BuffT <: Dbl2Buff[B]
  final override def elemProdSize = 2
  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.unsafeArray(index * 2) = value.dbl1; arr.unsafeArray(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[Dbl2Arr]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because it corresponds to the B in ```map[B](f: A => B)(implicit
 *  build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2ArrFlatBuilder[B <: ElemDbl2, ArrB <: Dbl2Arr[B]] extends DblNArrFlatBuilder[B, ArrB]
{ type BuffT <: Dbl2Buff[B]
  final override def elemProdSize = 2
}

/** Class for the singleton companion objects of [[Dbl2Arr]] final classes to extend. */
trait Dbl2SeqDefCompanion[A <: ElemDbl2, ArrA <: Dbl2SeqDef[A]] extends DblNSeqDefCompanion[A, ArrA]
{ final def elemNumDbls: Int = 2

  /** Apply factory method for creating Arrs of [[ElemDbl2]]s. */
  def apply(elems: A*): ArrA =
  {
    val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 2) = elems(count).dbl1
      res.unsafeArray(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }

  def fromList(list: List[A]): ArrA =
  {
    val arrLen: Int = list.length * 2
    val res = uninitialised(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.unsafeArray(count) = rem.head.dbl1
      count += 1
      res.unsafeArray(count) = rem.head.dbl2
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Persists and assists in building [[DblNArr]]s. */
abstract class Dbl2SeqDefPersist[A <: ElemDbl2, M <: Dbl2SeqDef[A]](val typeStr: String) extends DataDblNsPersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
  }

  override def syntaxDepthT(obj: M): Int = 3
  override def showDecT(obj: M, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = typeStr //+ obj.dataF Map(_.toString).toString
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl2]]s collections. */
trait Dbl2Buff[A <: ElemDbl2] extends Any with DblNBuff[A]
{ type ArrT <: Dbl2Arr[A]
  final override def length: Int = unsafeBuffer.length / 2
  override def elemProdSize: Int = 2
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2); () }
  def dblsToT(d1: Double, d2: Double): A
  override def sdIndex(index: Int): A = dblsToT(unsafeBuffer(index * 2), unsafeBuffer(index * 2 + 1))
  override def unsafeSetElem(i: Int, value: A): Unit = { unsafeBuffer(i * 2) = value.dbl1; unsafeBuffer(i * 2 + 1) = value.dbl2 }
  override def fElemStr: A => String = _.toString
}