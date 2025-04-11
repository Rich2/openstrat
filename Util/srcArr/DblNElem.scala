/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[ArrDblN]] Array[Double] based collections. */
trait DblNElem extends Any with ValueNElem
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def dblForeach(f: Double => Unit): Unit

  /** Utility method to append this as an element to an [[ArrayBuffer]][Double]. End users should rarely need to use this method. */
  def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit
}

trait SeqLikeDblNImut[+A <: DblNElem] extends Any, SeqLikeValueNImut[A], ArrayDblBacked
{ type ThisT <: SeqLikeDblNImut[A]
  def fromArray(array: Array[Double]): ThisT
  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Double](length * elemProdSize))
}

/** Base trait for classes that are defined by collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. As well as
 * [[ArrDblN]] classes this is also the base trait for classes like polygons that are defined by a collection of points. */
trait SeqSpecDblN[+A <: DblNElem] extends Any, SeqLikeDblNImut[A], SeqSpecValueN[A], ArrayDblBacked
{ type ThisT <: SeqSpecDblN[A]

  override def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(numElems)
    iForeach({ (i, el) => res.setElemUnsafe(numElems - 1 - i, el)})
    res
  }

  /** Reverses the order of the elements in a new Array[Double] which is returned. */
  def unsafeReverseArray: Array[Double] =
  { val res = new Array[Double](arrayLen)
    iUntilForeach(numElems){ i =>
      val origIndex = i * elemProdSize
      val resIndex = (numElems - i - 1) * elemProdSize
      iUntilForeach(elemProdSize){j => res(resIndex + j) = arrayUnsafe(origIndex + j) }
    }
    res
  }

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  { val acc = new Array[Double](arrayLen + appendProductsLength * elemProdSize)
    arrayUnsafe.copyToArray(acc)
    acc
  }
}

/** Base trait for collections of elements that are products of [[Double]]s, backed by an underlying Array[Double]. */
trait ArrDblN[A <: DblNElem] extends Any with SeqLikeDblNImut[A] with ArrValueN[A]
{ type ThisT <: ArrDblN[A]

  /** Not sure about this method. */
  def foreachArr(f: DblArr => Unit): Unit

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

  final override def drop(n: Int): ThisT =
  { val nn = n.max0.min(length)
    val newLen = (arrayLen - elemProdSize * nn)
    val newArray = new Array[Double](newLen)
    arrayUnsafe.copyDropToArray(nn * elemProdSize, newArray)
    fromArray(newArray)
  }

  final override def dropRight(n: Int): ThisT =
  { val nn = n.max0
    val newArray = new Array[Double]((arrayLen - elemProdSize * nn).max0)
    iUntilForeach(arrayLen - elemProdSize * nn) { i => newArray(i) = arrayUnsafe(i) }
    fromArray(newArray)
  }

  @targetName("append") final override def ++(operand: ThisT): ThisT =
  { val newArray: Array[Double] = new Array(arrayLen + operand.arrayLen)
    arrayUnsafe.copyToArray(newArray)
    operand.arrayUnsafe.copyToArray(newArray, arrayLen)
    fromArray(newArray)
  }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait BuffDblN[A <: DblNElem] extends Any with BuffValueN[A]
{ type ArrT <: ArrDblN[A]
  def bufferUnsafe: ArrayBuffer[Double]
  def length: Int = bufferUnsafe.length / elemProdSize
  def toArray: Array[Double] = bufferUnsafe.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { bufferUnsafe.addAll(newElems.arrayUnsafe); () }
  def toArr(implicit build: BuilderArrDblNMap[A, ArrT]): ArrT = build.fromDblArray(bufferUnsafe.toArray)
}

/** A builder for all [[SeqLike]] classes that can be constructed from an Array of Doubles. */
trait BuilderSeqLikeDblN[BB <: SeqLike[?]] extends BuilderSeqLikeValueN[BB]
{ type BuffT <: BuffDblN[?]
  def fromDblArray(array: Array[Double]): BB
  def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = buffFromBufferDbl(new ArrayBuffer[Double](length * elemProdSize))
  final override def buffToSeqLike(buff: BuffT): BB = fromDblArray(buff.bufferUnsafe.toArray)
}

trait BuilderSeqLikeDblNMap[B <: DblNElem, BB <: SeqLikeDblNImut[B]] extends BuilderSeqLikeDblN[BB] with BuilderSeqLikeMap[B, BB]
{ type BuffT <: BuffDblN[B]
  final override def uninitialised(length: Int): BB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffGrow(buff: BuffT, newElem: B): Unit = newElem.dblForeach(buff.bufferUnsafe.append(_))

  override def indexSet(seqLike: BB, index: Int, newElem: B): Unit =
  { var ii = 0
    newElem.dblForeach { d => seqLike.arrayUnsafe(index * elemProdSize + ii); ii += 1}
  }
}

trait BuilderArrDblN[ArrB <: ArrDblN[?]] extends BuilderSeqLikeDblN[ArrB]

/** Trait for creating the sequence builder type class instances for [[ArrDblN]] final classes. Instances for the [[BuilderArrMap]] type class, for classes /
 * traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 * ```map(f: A => B): ArrB``` function. */
trait BuilderArrDblNMap[B <: DblNElem, ArrB <: ArrDblN[B]] extends BuilderSeqLikeDblNMap[B, ArrB] with BuilderArrValueNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrDblN]] final classes. Instances for the [[BuilderArrMap]] type class,
 * for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion object the ArrT final
 * class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDblNFlat[ArrB <: ArrDblN[?]] extends BuilderSeqLikeDblN[ArrB] with BuilderArrValueNFlat[ArrB]
{ //final override def buffToBB(buff: BuffT): ArrB = fromDblArray(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.bufferUnsafe.addAll(arr.arrayUnsafe); () }
}

/** Helper trait for Companion objects of [[ArrDblN]] classes. */
trait CompanionSeqLikeDblN[A <: DblNElem, AA <: SeqLikeDblNImut[A]]
{ /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def numElemDbls: Int

  /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArray(array: Array[Double]): AA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  def uninitialised(length: Int): AA = fromArray(new Array[Double](length * numElemDbls))

  def empty: AA = fromArray(new Array[Double](0))

  /** Factory method for creating the sequence defined object from raw double values. This will throw if the number of parameter [[Doubles]] is incorrect. */
  def dbls(inp: Double*): AA =
  { val arrLen: Int = inp.length
    if (arrLen % numElemDbls != 0) excep(
      s"$arrLen Double values is not a correct number for the creation of this objects defining sequence, must be a multiple of $numElemDbls")

    val array = new Array[Double](inp.length)
    var i: Int = 0

    while (i < arrLen)
    { val newEl = inp(i)
      array(i) = newEl
      i += 1
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