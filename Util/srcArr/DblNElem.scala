/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[DblNArr]] Array[Double] based collections. */
trait DblNElem extends Any with ValueNElem
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def dblForeach(f: Double => Unit): Unit
}

trait DblNSeqLike[A <: DblNElem] extends Any with ValueNSeqLike[A] with ArrayDblBacked
{ type ThisT <: DblNSeqLike[A]
  def fromArray(array: Array[Double]): ThisT

  /** Utility method to append element on to an [[ArrayBuffer]][Double]. End users should rarely need to use this method. */
  def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A): Unit

  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Double](length * elemProdSize))
  @inline final def unsafeLength: Int = unsafeArray.length
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As
 *  well as [[DblNArr]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait DblNSeqSpec[A <: DblNElem] extends Any with DblNSeqLike[A] with ValueNSeqSpec[A] with ArrayDblBacked
{ type ThisT <: DblNSeqSpec[A]

  override def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(ssLength)
    ssIForeach({ (i, el) => res.setElemUnsafe(ssLength - 1 - i, el)})
    res
  }

  /** Reverses the order of the elements in a new Array[Double] which is returned. */
  def unsafeReverseArray: Array[Double] =
  { val res = new Array[Double](unsafeLength)
    iUntilForeach(ssLength){ i =>
      val origIndex = i * elemProdSize
      val resIndex = (ssLength - i - 1) * elemProdSize
      iUntilForeach(elemProdSize){j => res(resIndex + j) = unsafeArray(origIndex + j) }
    }
    res
  }

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  { val acc = new Array[Double](unsafeLength + appendProductsLength * elemProdSize)
    unsafeArray.copyToArray(acc)
    acc
  }
}

/** Base trait for collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. */
trait DblNArr[A <: DblNElem] extends Any with DblNSeqLike[A] with ValueNArr[A]
{ type ThisT <: DblNArr[A]

  /** Not sure about this method. */
  def foreachArr(f: DblArr => Unit): Unit

  final def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(length)
    iForeach({(i, el) => res.setElemUnsafe(length - 1 - i, el)})
    res
  }

  final def filter(f: A => Boolean): ThisT =
  { val buff = new ArrayBuffer[Double](4 * elemProdSize)
    foreach { a => if (f(a)) dblBufferAppend(buff, a) }
    fromArray(buff.toArray)
  }

  final override def drop(n: Int): ThisT = {
    val nn = n.max0.min(length)
    val newLen = (unsafeLength - elemProdSize * nn)
    val newArray = new Array[Double](newLen)
    unsafeArray.copyDropToArray(nn * elemProdSize, newArray)
    fromArray(newArray)
  }

  final override def dropRight(n: Int): ThisT =
  { val nn = n.max0
    val newArray = new Array[Double]((unsafeLength - elemProdSize * nn).max0)
    iUntilForeach(unsafeLength - elemProdSize * nn) { i => newArray(i) = unsafeArray(i) }
    fromArray(newArray)
  }

  @targetName("appendArr") final override def ++(operand: ThisT): ThisT =
  { val newArray: Array[Double] = new Array(unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, unsafeLength)
    fromArray(newArray)
  }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait DblNBuff[A <: DblNElem] extends Any with ValueNBuff[A]
{ type ArrT <: DblNArr[A]
  def unsafeBuffer: ArrayBuffer[Double]

  def length: Int = unsafeBuffer.length / elemProdSize
  def toArray: Array[Double] = unsafeBuffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  def toArr(implicit build: DblNArrMapBuilder[A, ArrT]): ArrT = build.fromDblArray(unsafeBuffer.toArray)
}

/** A builder for all [[SeqLike]] classes that can be constructed from an Array of Doubles. */
trait DblNSeqLikeCommonBuilder[BB <: SeqLike[_]] extends ValueNSeqLikeCommonBuilder[BB]
{ type BuffT <: DblNBuff[_]
  def fromDblArray(array: Array[Double]): BB
  def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = buffFromBufferDbl(new ArrayBuffer[Double](length * elemProdSize))
  final override def buffToSeqLike(buff: BuffT): BB = fromDblArray(buff.unsafeBuffer.toArray)
}

trait DblNSeqLikeMapBuilder[B <: DblNElem, BB <: DblNSeqLike[B]] extends DblNSeqLikeCommonBuilder[BB] with SeqLikeMapBuilder[B, BB]
{ type BuffT <: DblNBuff[B]
  final override def uninitialised(length: Int): BB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffGrow(buff: BuffT, newElem: B): Unit = newElem.dblForeach(buff.unsafeBuffer.append(_))

  override def indexSet(seqLike: BB, index: Int, elem: B): Unit =
  { var ii = 0
    elem.dblForeach {d => seqLike.unsafeArray(index * elemProdSize + ii); ii += 1}
  }
}

trait DblNArrCommonBuilder[ArrB <: DblNArr[_]] extends DblNSeqLikeCommonBuilder[ArrB]

/** Trait for creating the sequence builder type class instances for [[DblNArr]] final classes. Instances for the [[ArrMapBuilder]] type class, for
 *  classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait DblNArrMapBuilder[B <: DblNElem, ArrB <: DblNArr[B]] extends DblNSeqLikeMapBuilder[B, ArrB] with ValueNArrMapBuilder[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[DblNArr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait DblNArrFlatBuilder[ArrB <: DblNArr[_]] extends DblNSeqLikeCommonBuilder[ArrB] with ValueNArrFlatBuilder[ArrB]
{ //final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Helper trait for Companion objects of [[DblNArr]] classes. */
trait DblNSeqLikeCompanion[A <: DblNElem, AA <: DblNSeqLike[A]]// extends SeqLikeCompanion[A, AA]
{ /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int

  /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArray(array: Array[Double]): AA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  def uninitialised(length: Int): AA = fromArray(new Array[Double](length * elemNumDbls))

  def empty: AA = fromArray(new Array[Double](0))

  /** Factory method for creating the sequence defined object from raw double values. This will throw if the number of parameter [[Doubles]] is
   *  incorrect. */
  def fromDbls(elems: Double*): AA =
  { val arrLen: Int = elems.length
    if (arrLen % elemNumDbls != 0) excep(
      s"$arrLen Double values is not a correct number for the creation of this objects defining sequence, must be a multiple of $elemNumDbls")

    val array = Array[Double](elems.length)
    var count: Int = 0

    while (count < arrLen)
    { array(count) = elems(count)
      count += 1
    }
    fromArray(array)
  }
}

/** Persists [[DblNArr]]s. */
trait DataDblNsPersist[A <: DblNElem, M <: DblNSeqLike[A]] extends PersistValueNSeqLike[A, M] with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqT(m1: M, m2: M): Boolean = m1.unsafeArray === m2.unsafeArray
}

/** Helper trait for [[IntNBuff]] companion objects. Facilitates factory apply methods. */
/*
trait DblNBuffCompanion[A <: DblNElem, AA <: DblNBuff[A]]
{ /** apply factory method for [[DblNBuff]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Double]]s required to construct an element */
  def elemNumInts: Int

  def fromBuffer(buffer: ArrayBuffer[Double]): AA
}*/
