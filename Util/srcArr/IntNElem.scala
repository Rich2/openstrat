/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A class that can be construct from a fixed number of [[Int]]s. Because of the fixed length of these elements they can be be stored as and
 * reconstructed from a single Array[Int] of primitive values. */
trait IntNElem extends Any with ValueNElem
{ /** Performs the side effecting function on each [[Double]] in this Product element. */
  def intForeach(f: Int => Unit): Unit

  /** Utility method to append this element on to an [[ArrayBuffer]][Int]. End users should rarely need to use this method. This is useful for methods
   *  like filter. */
  def intBufferAppend(buffer: ArrayBuffer[Int]): Unit
}

trait SeqLikeIntN[A <: IntNElem] extends Any with SeqLikeValueN[A] with ArrayIntBacked
{ type ThisT <: SeqLikeIntN[A]

  /** Constructs the final type of these [[SeqLikeIntN]] from an [[Array]][Int]. Mostly you will access this capability from the companion object or
   *  the appropriate builder, but it can be useful to access this from the class itself. */
  def fromArray(array: Array[Int]): ThisT

  /** Method for creating a new Array[Int] backed collection class of this collection class's final type. */
  final def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Int](length * elemProdSize))
}

trait SeqSpecIntN[A <: IntNElem] extends Any with SeqLikeIntN[A] with SeqSpecValueN[A] with ArrayIntBacked
{ type ThisT <: SeqSpecIntN[A]

  override def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(ssLength)
    ssIForeach({ (i, el) => res.setElemUnsafe(ssLength - 1 - i, el)})
    res
  }
}

/** An immutable collection of Elements that inherit from a Product of an Atomic value: Double, Int, Long or Float. They are stored with a backing
 * Array[Int] They are named ProductInts rather than ProductIs because that name can easlily be confused with ProductI1s. */
trait ArrIntN[A <: IntNElem] extends Any with ArrValueN[A] with SeqLikeIntN[A]
{ /** The final type of this Array[Int] backed collection class. */
  type ThisT <: ArrIntN[A]

  final override def reverse: ThisT =
  { val res: ThisT = unsafeSameSize(length)
    iForeach({ (i, el) => res.setElemUnsafe(length - 1 - i, el) })
    res
  }

  final override def drop(n: Int): ThisT =
  { val nn = n.max0.min(length)
    val newLen = (unsafeLength - elemProdSize * nn)
    val newArray = new Array[Int](newLen)
    unsafeArray.copyDropToArray(nn * elemProdSize, newArray)
    fromArray(newArray)
  }

  final override def dropRight(n: Int): ThisT =
  { val nn = n.max0.min(length)
    val newArrayLen = (unsafeLength - elemProdSize * nn)
    val newArray = new Array[Int](newArrayLen)
    unsafeArray.copyToArray(newArray)
    fromArray(newArray)
  }

  @targetName("appendArr") final override def ++(operand: ThisT): ThisT =
  { val newArray: Array[Int] = new Array(unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    operand.unsafeArray.copyToArray(newArray, unsafeLength)
    fromArray(newArray)
  }

  final def filter(f: A => Boolean): ThisT =
  { val buff = new ArrayBuffer[Int](4 * elemProdSize)
    foreach { a => if (f(a)) a.intBufferAppend(buff) }
    fromArray(buff.toArray)
  }

  /** Returns an empty [[Arr]] if this is empty else returns an [[Arr]] containing only the last element. */
  def lasts: ThisT =
  { val array: Array[Int] =
      if(length == 0) new Array[Int](0)
      else
      { val array = new Array[Int](elemProdSize)
        iUntilForeach(elemProdSize){i => array(i) = unsafeArray(unsafeLength - elemProdSize + i) }
        array
      }
    fromArray(array)
  }
}

trait BuilderAllSeqLikeIntN[BB <: SeqLike[_]] extends BuilderAllSeqLikeValueN[BB]
{ type BuffT <:  BuffIntN[_]
  def fromIntBuffer(buffer: ArrayBuffer[Int]): BuffT
  def fromIntArray(array: Array[Int]): BB
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
}

/** Constructs [[SeqLikeIntN]] objects via map method. Type of element known at at call site. Hence implicit look up will be in the element
 * companion object. */
trait BuilderSeqLikeIntNMap[B <: IntNElem, BB <: SeqLikeIntN[B]] extends BuilderAllSeqLikeIntN[BB] with BuilderMapSeqLikeValueN[B, BB]
{ type BuffT <:  BuffIntN[B]
  final override def uninitialised(length: Int): BB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def buffToSeqLike(buff: BuffT): BB = fromIntArray(buff.unsafeBuffer.toArray)
}

/** Constructs [[SeqLikeIntN]] objects via flatMap method. Type of element known not known at at call site. Hence implicit look up will be in the
 * in the [[SeqLike]]'s companion object. */
trait BuilderSeqLikeIntNFlat[BB <: SeqLikeIntN[_]] extends BuilderAllSeqLikeIntN[BB] with BuilderFlatSeqLikeValueN[BB]

/** Trait for creating the ArrTBuilder type class instances for [[ArrIntN]] final classes. Instances for the [[BuilderMapArr]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderArrIntNMap[B <: IntNElem, ArrB <: ArrIntN[B]] extends BuilderSeqLikeIntNMap[B, ArrB] with BuilderMapArrValueN[B, ArrB]

/** Trait for creating the ArrTFlatBuilder type class instances for [[ArrIntN]] final classes. Instances for [[BuilderFlatArr] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderFlatArrIntN[ArrB <: ArrIntN[_]] extends BuilderAllSeqLikeIntN[ArrB] with BuilderFlatArrValueN[ArrB]
{  final override def buffToSeqLike(buff: BuffT): ArrB = fromIntArray(buff.unsafeBuffer.toArray)
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.unsafeBuffer.addAll(arr.unsafeArray); () }
}

/** Specialised flat ArrayBuffer[Int] based collection class. */
trait BuffIntN[A <: IntNElem] extends Any with BuffValueN[A]
{ type ArrT <: ArrIntN[A]
  def unsafeBuffer: ArrayBuffer[Int]
  def toArray: Array[Int] = unsafeBuffer.toArray[Int]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { unsafeBuffer.addAll(newElems.unsafeArray); () }
  override def length = unsafeBuffer.length / elemProdSize
}

/**  Class to [[Unshow]] specialised flat Array[Int] based collections. */
trait UnshowIntNSeqLike[A <: IntNElem, M <: SeqLikeIntN[A]] extends UnshowSeqLikeValueN[A, M]
{ type VT = Int
  override def fromBuffer(buf: ArrayBuffer[Int]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Int] = BuffInt(0)
}

/** Helper trait for Companion objects of [[ArrIntN]] collection classes, where the type parameter ArrA is the [[IntNElem]] type of the of the
 *  collection class. */
trait CompanionSeqLikeIntN[A <: IntNElem, AA <: SeqLikeIntN[A]]
{ /** The number of [[Int]]s that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an ArrayBuffer[Int]. */
  def fromBuffer(buffer: ArrayBuffer[Int]): AA = fromArray(buffer.toArray[Int])

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  def fromArray(array: Array[Int]): AA

  def fromBuff(buff: BuffIntN[A]): AA = fromArray(buff.unsafeBuffer.toArray)

  /** returns a collection class of type ArrA, whose backing Array[Int] is uninitialised. */
  def uninitialised(length: Int): AA = fromArray(new Array[Int](length * elemNumInts))

  def ints(inp: Int*): AA =
  { if (inp.length %% elemNumInts != 0) excep(s"Wrong number of Ints, ${inp.length} is not divisible by $elemNumInts.")
    fromArray(inp.toArray)
  }
}

/** Helper trait for [[BuffIntN]] companion objects. Facilitates factory apply methods. */
trait CompanionBuffIntN[A <: IntNElem, AA <: BuffIntN[A]]
{ /** apply factory method for [[BuffIntN]] final classes */
  def apply(elems: A*): AA

  /** Number of [[Int]]s required to construct an element */
  def elemNumInts: Int

  def fromBuffer(buffer: ArrayBuffer[Int]): AA
}