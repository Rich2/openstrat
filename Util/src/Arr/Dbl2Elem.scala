/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[Dbl2Arr]] Array[Double] based collections. */
trait Dbl2Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: Dbl2Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: Dbl2Elem, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2) }
}

/** A Sequence like class of [[Dbl2Elem]] elements that can be constructed from 2 [[Double]]s. */
trait Dbl2SeqLike[A <: Dbl2Elem] extends Any with DblNSeqLike[A]
{ override def elemProdSize: Int = 2
  override def unsafeSetElem(index: Int, newElem: A): Unit = { unsafeArray(2 * index) = newElem.dbl1; unsafeArray(2 * index + 1) = newElem.dbl2 }
  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A) : Unit = { buffer.append(elem.dbl1); buffer.append(elem.dbl2) }
}

/** A sequence-defined specialised immutable, flat Array[Double] based trait defined by a sequence of a type of [[Dbl2Elem]]s. */
trait Dbl2SeqSpec[A <: Dbl2Elem] extends Any with Dbl2SeqLike[A] with DblNSeqSpec[A]
{ /** Method for creating new specifying sequence elements from 2 [[Double]]s In the case of [[Dbl2Arr]] this will be thee type of the elements of the
   *  sequence. */
  def ssElem(d1: Double, d2: Double): A


  override def ssIndex(index: Int): A = ssElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))
  override def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2)

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](ssLength)
    var count = 0
    while(count < ssLength){ res(count) = unsafeArray(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](ssLength)
    var count = 0
    while(count < ssLength){ res(count) = unsafeArray(count * 2 + 1); count += 1 }
    res
  }

  def ssForeachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < ssLength) { f(unsafeArray(count * 2), unsafeArray(count * 2 + 1)); count += 1 }
  }

  /** Maps the 2 [[Double]]s of each element to a new [[Array]][Double]. */
  def unsafeMap(f: A => A): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, unsafeLength, 2){ i =>
      val newElem = f(ssElem(unsafeArray(i), unsafeArray(i + 1)))
      newArray(i) = newElem.dbl1
      newArray(i + 1) = newElem.dbl2
    }
    newArray
  }

  /** Maps the 1st [[Double]] of each element to a new [[Array]][Double], copies the 2nd elements. */
  def unsafeD1Map(f: Double => Double): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, unsafeLength, 2){ i => newArray(i) = f(unsafeArray(i)) }
    iUntilForeach(1, unsafeLength, 2){ i => newArray(i) = unsafeArray(i) }
    newArray
  }

  /** Maps the 2nd [[Double]] of each element with the parameter function to a new [[Array]][Double], copies the 1st [[Double]] of each element. */
  def unsafeD2Map(f: Double => Double): Array[Double] = {
    val newArray: Array[Double] = new Array[Double](unsafeArray.length)
    iUntilForeach(0, unsafeLength, 2){ i => newArray(i) = unsafeArray(i) }
    iUntilForeach(1, unsafeLength, 2){ i => newArray(i) = f(unsafeArray(i)) }
    newArray
  }
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl2Elem]]s. */
trait Dbl2Arr[A <: Dbl2Elem] extends Any with DblNArr[A] with Dbl2SeqLike[A]
{ type ThisT <: Dbl2Arr[A]
  final override def length: Int = unsafeArray.length / 2
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def getPair(index: Int): (Double, Double) = (unsafeArray(2 * index), unsafeArray(2 * index + 1))

  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < length) { f(unsafeArray(count * 2), unsafeArray(count * 2 + 1)); count += 1 }
  }

  override def apply(index: Int): A = seqDefElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))

  override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](unsafeLength + 2)
    unsafeArray.copyToArray(newArray)
    newArray(unsafeLength) = operand.dbl1
    newArray(unsafeLength + 1) = operand.dbl2
    fromArray(newArray)
  }

  /** Method for creating new data elements from 2 [[Double]]s In the case of [[Dbl2Arr]] this will be thee type of the elements of the sequence. */
  def seqDefElem(d1: Double, d2: Double): A
  override def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2)))
}

trait Dbl2SeqLikeCommonBuilder[BB <: Dbl2SeqLike[_]] extends DblNSeqLikeCommonBuilder[BB]
{ type BuffT <: Dbl2Buff[_]
  final override def elemProdSize = 2
}

trait Dbl2SeqLikeMapBuilder[B <: Dbl2Elem, BB <: Dbl2SeqLike[B]] extends Dbl2SeqLikeCommonBuilder[BB] with DblNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl2Buff[B]
  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = { seqLike.unsafeArray(index * 2) = elem.dbl1; seqLike.unsafeArray(index * 2 + 1) = elem.dbl2}
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl2Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is
 *  called B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2ArrMapBuilder[B <: Dbl2Elem, ArrB <: Dbl2Arr[B]] extends Dbl2SeqLikeMapBuilder[B, ArrB] with DblNArrMapBuilder[B, ArrB]

/** Trait for creating the ArrTFlatBuilder type class instances for [[Dbl2Arr]] final classes. Instances for [[ArrFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because it corresponds to the B in ```map[B](f: A => B)(implicit
 *  build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2ArrFlatBuilder[ArrB <: Dbl2Arr[_]] extends Dbl2SeqLikeCommonBuilder[ArrB] with DblNArrFlatBuilder[ArrB]

/** Class for the singleton companion objects of [[Dbl2Arr]] final classes to extend. */
trait Dbl2SeqLikeCompanion[A <: Dbl2Elem, AA <: Dbl2SeqLike[A]] extends DblNSeqLikeCompanion[A, AA]
{ final def elemNumDbls: Int = 2

  /** Apply factory method for creating Arrs of [[Dbl2Elem]]s. */
  def apply(elems: A*): AA =
  { val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 2) = elems(count).dbl1
      res.unsafeArray(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }
}

/** Persists and assists in building [[Db2SeqDef]] objectsS. */
abstract class Dbl2SeqDefPersist[A <: Dbl2Elem, M <: Dbl2SeqLike[A]](val typeStr: String) extends DataDblNsPersist[A, M]
{ override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit = { buf += value.dbl1; buf += value.dbl2 }
  override def syntaxDepthT(obj: M): Int = 3
  override def showDecT(obj: M, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = typeStr //+ obj.dataF Map(_.toString).toString
}

/** [[Buff]] class for building [[Dbl2Elem]]s collections. */
trait Dbl2Buff[B <: Dbl2Elem] extends Any with DblNBuff[B]
{ type ArrT <: Dbl2Arr[B]

  /** Constructs a new element of this [[Buff]] from 2 [[Double]]s. */
  def newElem(d1: Double, d2: Double): B

  final override def length: Int = unsafeBuffer.length / 2
  final override def elemProdSize: Int = 2
  final override def grow(newElem: B): Unit = { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2); () }

  override def apply(index: Int): B = newElem(unsafeBuffer(index * 2), unsafeBuffer(index * 2 + 1))
  final override def unsafeSetElem(i: Int, newElem: B): Unit = { unsafeBuffer(i * 2) = newElem.dbl1; unsafeBuffer(i * 2 + 1) = newElem.dbl2 }
  override def fElemStr: B => String = _.toString
}