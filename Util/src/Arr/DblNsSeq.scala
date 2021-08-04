/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[DblNsSeq]] Array[Double] based collections. */
trait DblNElem extends Any with ValueNElem
{ //def defaultDelta: Double = 1e-12
}

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any
{ def arrayUnsafe: Array[Double]
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As
 *  well as [[DblNsSeq]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait DblNsData[A <: DblNElem] extends Any with ValueNsData[A] with ArrayDblBacked

/** Base trait for collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. */
trait DblNsSeq[A <: DblNElem] extends Any with ValueNsSeq[A] with DblNsData[A]
{ type ThisT <: DblNsSeq[A]

  def unsafeFromArray(array: Array[Double]): ThisT
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Double](length * elemProductNum))
  def unsafeCopyFromArray(opArray: Array[Double], offset: Int = 0): Unit = { opArray.copyToArray(arrayUnsafe, offset * elemProductNum); () }
  def arrLen = arrayUnsafe.length

  /** Not sure about this method. */
  def foreachArr(f: Dbls => Unit): Unit

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * elemProductNum)
    arrayUnsafe.copyToArray(acc)
    acc
  }

  def reverse: ThisT =
  { val res: ThisT = unsafeNew(elemsLen)
    iForeach{(el, i) => res.unsafeSetElem(elemsLen - 1 - i, el)}
    res
  }
}

/** Trait for creating the ArrTBuilder type class instances for [[DblNsSeq]] final classes. Instances for the [[ArrTBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait DblNsArrBuilder[B <: DblNElem, ArrB <: DblNsSeq[B]] extends ValueNsArrBuilder[B, ArrB]
{ type BuffT <: DblNsBuffer[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemSize))
  final override def newArr(length: Int): ArrB = fromDblArray(new Array[Double](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuff.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuff.addAll(arr.arrayUnsafe); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[DblNsSeq]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrTFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait DblNsArrFlatBuilder[B <: DblNElem, ArrB <: DblNsSeq[B]] extends ValueNsArrFlatBuilder[B, ArrB]
{ type BuffT <: DblNsBuffer[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuff.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuff.addAll(arr.arrayUnsafe); () }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait DblNsBuffer[A <: DblNElem] extends Any with ValueNsBuffer[A]
{ type ArrT <: DblNsSeq[A]
  def unsafeBuff: ArrayBuffer[Double]

  def elemsLen: Int = unsafeBuff.length / elemSize
  def toArray: Array[Double] = unsafeBuff.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuff.addAll(newElems.arrayUnsafe); () }
}

/** Helper trait for Companion objects of [[DblNsSeq]] classes. */
trait DblNsArrCompanion[A <: DblNElem, ArrA <: DblNsSeq[A]] extends ValueNArrCompanion[A, ArrA]
{ /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArrayDbl(array: Array[Double]): ArrA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  override implicit def uninitialised(length: Int): ArrA = fromArrayDbl(new Array[Double](length * elemSize))
}

/** Persists [[DblNsSeq]]s. */
abstract class DblNsDataPersist[A <: DblNElem, M <: DblNsData[A]](typeStr: String) extends ValueNsArrPersist[A, M](typeStr) with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqv(m1: M, m2: M): Boolean = m1.arrayUnsafe equ m2.arrayUnsafe
}