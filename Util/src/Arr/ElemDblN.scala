/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[DblNArr]] Array[Double] based collections. */
trait ElemDblN extends Any with ElemValueN
{

}

trait DblNSeqLike[A <: ElemDblN] extends Any with ValueNSeqLike[A] with ArrayDblBacked
{
  def unsafeFromArray(array: Array[Double]): ThisT

  final override def unsafeSameSize(length: Int): ThisT = unsafeFromArray(new Array[Double](length * elemProdSize))

  @inline final def dsLen: Int = unsafeArray.length
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As
 *  well as [[DblNArr]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait DblNSeqDef[A <: ElemDblN] extends Any with DblNSeqLike[A] with ValueNSeqDef[A]
{ type ThisT <: DblNSeqDef[A]



  override def reverseData: ThisT =
  { val res: ThisT = unsafeSameSize(sdLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(sdLength - 1 - i, el)})
    res
  }

  /** Reverses the order of the elements in a new Array[Double] which is returned. */
  def unsafeReverseArray: Array[Double] = {
    val res = new Array[Double](dsLen)
    iUntilForeach(sdLength){ i =>
      val origIndex = i * elemProdSize
      val resIndex = (sdLength - i - 1) * elemProdSize
      iUntilForeach(elemProdSize){j => res(resIndex + j) = unsafeArray(origIndex + j) }
    }
    res
  }

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](dsLen + appendProductsLength * elemProdSize)
    unsafeArray.copyToArray(acc)
    acc
  }
}

/** Base trait for collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. */
trait DblNArr[A <: ElemDblN] extends Any with ValueNArr[A] with DblNSeqLike[A]
{ type ThisT <: DblNArr[A]

  /** Not sure about this method. */
  def foreachArr(f: DblArr => Unit): Unit

  /*override final def reverse: ThisT = {
    val res: ThisT = unsafeSameSize(sdLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(sdLength - 1 - i, el) })
    res
  }*/

  def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(sdLength)
    iForeach({(i, el) => res.unsafeSetElem(sdLength - 1 - i, el)})
    res
  }

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++(operand: ThisT)(implicit build: DblNArrBuilder[A, ThisT]): ThisT = {
    val newArray: Array[Double] = new Array(dsLen + operand.dsLen)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, dsLen)
    build.fromDblArray(newArray)
  }

  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  /*def :+[N <: ArrValueNs[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(dataLength + 1)
    iForeach((i, elem) => res.unsafeSetElem(i, elem))
    res.unsafeSetElem(dataLength, operand)
    res
  }*/
}

/** Trait for creating the sequence builder type class instances for [[DblNArr]] final classes. Instances for the [[ArrBuilder]] type class, for
 *  classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait DblNArrBuilder[B <: ElemDblN, ArrB <: DblNArr[B]] extends ValueNArrBuilder[B, ArrB]
{ type BuffT <: DblNBuff[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(buffer: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def newArr(length: Int): ArrB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[DblNArr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait DblNArrFlatBuilder[B <: ElemDblN, ArrB <: DblNArr[B]] extends ValueNArrFlatBuilder[B, ArrB]
{ type BuffT <: DblNBuff[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait DblNBuff[A <: ElemDblN] extends Any with ValueNBuff[A]
{ type ArrT <: DblNArr[A]
  def unsafeBuffer: ArrayBuffer[Double]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDef]] of the this [[SeqImut]]'s final type. */
  override def unsafeSameSize(length: Int): ThisT = ???

  def sdLength: Int = unsafeBuffer.length / elemProdSize
  def toArray: Array[Double] = unsafeBuffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  def toArr(implicit build: DblNArrBuilder[A, ArrT]): ArrT = build.fromDblArray(unsafeBuffer.toArray)
}

/** Helper trait for Companion objects of [[DblNArr]] classes. */
trait DblNSeqLikeCompanion[A <: ElemDblN, AA <: DblNSeqLike[A]] extends ValueNSeqLikeCompanion[A, AA]
{ /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int

  /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArray(array: Array[Double]): AA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  override def uninitialised(length: Int): AA = fromArray(new Array[Double](length * elemNumDbls))

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
trait DataDblNsPersist[A <: ElemDblN, M <: DblNSeqLike[A]] extends ValueNSeqLikePersist[A, M] with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqT(m1: M, m2: M): Boolean = m1.unsafeArray === m2.unsafeArray
}


/** Helper trait for [[IntNBuff]] companion objects. Facilitates factory apply methods. */
trait DblNBuffCompanion[A <: ElemDblN, AA <: DblNBuff[A]]
{ /** apply factory method for [[DblNBuff]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Double]]s required to construct an element */
  def elemNumInts: Int

  def fromBuffer(buffer: ArrayBuffer[Double]): AA
}