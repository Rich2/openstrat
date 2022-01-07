/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[ArrDblNs]] Array[Double] based collections. */
trait ElemDblN extends Any with ElemValueN
{ //def defaultDelta: Double = 1e-12
}

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any
{ def unsafeArray: Array[Double]
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As
 *  well as [[ArrDblNs]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait DataDblNs[A <: ElemDblN] extends Any with DataValueNs[A] with ArrayDblBacked
{ type ThisT <: DataDblNs[A]
  @inline override def arrLen = unsafeArray.length
  def unsafeFromArray(array: Array[Double]): ThisT
  final override def unsafeSameSize(length: Int): ThisT = unsafeFromArray(new Array[Double](length * elemProdSize))
  //def unsafeCopyFromArray(opArray: Array[Double], offset: Int = 0): Unit = { opArray.copyToArray(arrayUnsafe, offset * elemProdSize); () }


  override def reverseData: ThisT =
  { val res: ThisT = unsafeSameSize(dataLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(dataLength - 1 - i, el)})
    res
  }

  /** Reverses the order of the elements in a new Array[Double] which is returned. */
  def unsafeReverseArray: Array[Double] = {
    val res = new Array[Double](arrLen)
    iUntilForeach(0, dataLength){ i =>
      val origIndex = i * elemProdSize
      val resIndex = (dataLength - i - 1) * elemProdSize
      iUntilForeach(0, elemProdSize){j => res(resIndex + j) = unsafeArray(origIndex + j) }
    }
    res
  }
}

/** Base trait for collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. */
trait ArrDblNs[A <: ElemDblN] extends Any with ArrValueNs[A] with DataDblNs[A]
{ type ThisT <: ArrDblNs[A]

  /** Not sure about this method. */
  def foreachArr(f: Dbls => Unit): Unit

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * elemProdSize)
    unsafeArray.copyToArray(acc)
    acc
  }

  def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(dataLength)
    iForeach({(i, el) => res.unsafeSetElem(dataLength - 1 - i, el)})
    res
  }
}

/** Trait for creating the sequence builder type class instances for [[ArrDblNs]] final classes. Instances for the [[ArrBuilder]] type class, for
 *  classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait ArrDblNsBuilder[B <: ElemDblN, ArrB <: ArrDblNs[B]] extends ArrValueNsBuilder[B, ArrB]
{ type BuffT <: BuffDblNs[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def newArr(length: Int): ArrB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrDblNs]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ArrDblNsFlatBuilder[B <: ElemDblN, ArrB <: ArrDblNs[B]] extends ArrValueNsFlatBuilder[B, ArrB]
{ type BuffT <: BuffDblNs[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait BuffDblNs[A <: ElemDblN] extends Any with BuffValueNs[A]
{ type ArrT <: ArrDblNs[A]
  def unsafeBuffer: ArrayBuffer[Double]

  def dataLength: Int = unsafeBuffer.length / elemProdSize
  def toArray: Array[Double] = unsafeBuffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  def toArr(implicit build: ArrDblNsBuilder[A, ArrT]): ArrT = build.fromDblArray(unsafeBuffer.toArray)
}

/** Helper trait for Companion objects of [[ArrDblNs]] classes. */
trait DataDblNsCompanion[A <: ElemDblN, ArrA <: DataDblNs[A]] extends DataValueNsCompanion[A, ArrA]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArrayDbl(array: Array[Double]): ArrA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  override implicit def uninitialised(length: Int): ArrA = fromArrayDbl(new Array[Double](length * elemProdSize))

  def empty: ArrA = fromArrayDbl(new Array[Double](0))
}

/** Persists [[ArrDblNs]]s. */
trait DataDblNsPersist[A <: ElemDblN, M <: DataDblNs[A]] extends DataValueNsPersist[A, M] with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqT(m1: M, m2: M): Boolean = m1.unsafeArray === m2.unsafeArray
}