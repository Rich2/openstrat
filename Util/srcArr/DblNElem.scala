/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[ArrDblN]] Array[Double] based collections. */
trait DblNElem extends Any with ValueNElem
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def dblForeach(f: Double => Unit): Unit

  /** Utility method to append this as an element to an [[ArrayBuffer]][Double]. End users should rarely need to use this method. */
  def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit
}

trait SeqLikeDblN[A <: DblNElem] extends Any with SeqLikeValueN[A] with ArrayDblBacked
{ type ThisT <: SeqLikeDblN[A]
  def fromArray(array: Array[Double]): ThisT

  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Double](length * elemProdSize))
  @inline final def unsafeLength: Int = unsafeArray.length
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As
 *  well as [[ArrDblN]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait SeqSpecDblN[A <: DblNElem] extends Any with SeqLikeDblN[A] with SeqSpecValueN[A] with ArrayDblBacked
{ type ThisT <: SeqSpecDblN[A]

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
trait ArrDblN[A <: DblNElem] extends Any with SeqLikeDblN[A] with ArrValueN[A]
{ type ThisT <: ArrDblN[A]

  /** Not sure about this method. */
  def foreachArr(f: ArrDbl => Unit): Unit

  final def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(length)
    iForeach({(i, el) => res.setElemUnsafe(length - 1 - i, el)})
    res
  }

  final def filter(f: A => Boolean): ThisT =
  { val buff = new ArrayBuffer[Double](4 * elemProdSize)
    foreach { a => if (f(a)) a.dblBufferAppend(buff) }
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
trait BuffDblN[A <: DblNElem] extends Any with BuffValueN[A]
{ type ArrT <: ArrDblN[A]
  def unsafeBuffer: ArrayBuffer[Double]

  def length: Int = unsafeBuffer.length / elemProdSize
  def toArray: Array[Double] = unsafeBuffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  def toArr(implicit build: BuilderArrDblNMap[A, ArrT]): ArrT = build.fromDblArray(unsafeBuffer.toArray)
}

/** A builder for all [[SeqLike]] classes that can be constructed from an Array of Doubles. */
trait BuilderSeqLikeDblN[BB <: SeqLike[_]] extends BuilderSeqLikeValueN[BB]
{ type BuffT <: BuffDblN[_]
  def fromDblArray(array: Array[Double]): BB
  def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = buffFromBufferDbl(new ArrayBuffer[Double](length * elemProdSize))
  final override def buffToSeqLike(buff: BuffT): BB = fromDblArray(buff.unsafeBuffer.toArray)
}

trait BuilderSeqLikeDblNMap[B <: DblNElem, BB <: SeqLikeDblN[B]] extends BuilderSeqLikeDblN[BB] with BuilderSeqLikeMap[B, BB]
{ type BuffT <: BuffDblN[B]
  final override def uninitialised(length: Int): BB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffGrow(buff: BuffT, newElem: B): Unit = newElem.dblForeach(buff.unsafeBuffer.append(_))

  override def indexSet(seqLike: BB, index: Int, elem: B): Unit =
  { var ii = 0
    elem.dblForeach {d => seqLike.unsafeArray(index * elemProdSize + ii); ii += 1}
  }
}

trait BuilderArrDblN[ArrB <: ArrDblN[_]] extends BuilderSeqLikeDblN[ArrB]

/** Trait for creating the sequence builder type class instances for [[ArrDblN]] final classes. Instances for the [[BuilderArrMap]] type class, for
 *  classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderArrDblNMap[B <: DblNElem, ArrB <: ArrDblN[B]] extends BuilderSeqLikeDblNMap[B, ArrB] with BuilderArrValueNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrDblN]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDblNFlat[ArrB <: ArrDblN[_]] extends BuilderSeqLikeDblN[ArrB] with BuilderArrValueNFlat[ArrB]
{ //final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Helper trait for Companion objects of [[ArrDblN]] classes. */
trait CompanionSeqLikeDblN[A <: DblNElem, AA <: SeqLikeDblN[A]]
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

/** Helper trait for [[BuffDblN]] companion objects. Facilitates factory apply methods. */
trait CompanionBuffDblN[A <: DblNElem, AA <: BuffDblN[A]]
{ /** apply factory method for [[BuffDblN]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Double]]s required to construct an element */
  def elemNumDbls: Int

  /** Allows the creation of the final [[Buff]] class from an [[ArrayBuffer]][Double]. */
  def fromBuffer(buffer: ArrayBuffer[Double]): AA
}