/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Int]]. These are used in [[ArrInt1]] Array[Int] based collections. */
trait Int1Elem extends Any with IntNElem
{ /* The single [[int]] value from which the final class can be constructed. */
  def int1: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]) : Unit = { buffer.append(int1) }
}

/** [[SeqLike]] trait with [[Int1Elem]]s. */
trait SlInt1[A <: Int1Elem] extends Any with SlValueN[A]
{ /** Constructs an element of the specifying sequence from an [[Int]] value. */
  def elemFromInt(intValue: Int): A

  final override def elemProdSize: Int = 1
  final override def elemEq(a1: A, a2: A): Boolean = a1.int1 == a2.int1
}

/** [[SeqLikeImut]] trait for classes specified by a single [[Int]]. */
trait SlInt1Imut[A <: Int1Elem] extends Any, SlImutIntN[A], SlInt1[A]
{ final override def setElemUnsafe(index: Int, newElem: A): Unit = { arrayUnsafe(index) = newElem.int1 }
  final override def numElems: Int = arrayLen
  final override def elem(index: Int): A = elemFromInt(arrayUnsafe(index))
}

/** A specialised immutable, flat Array[Int] based trait defined by a data sequence of a type of [[Int1Elem]]s. */
trait SeqSpecInt1[A <: Int1Elem] extends Any with SlInt1Imut[A] with SsIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int1Elem]]s. */
trait ArrInt1[A <: Int1Elem] extends Any with ArrIntN[A] with SlInt1Imut[A]
{ final override def length: Int = arrayUnsafe.length

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand.int1
    fromArray(newArray)
  }

  final override def apply(index: Int): A = elemFromInt(arrayUnsafe(index))
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int1Elem]]s collections. */
trait BuffInt1[A <: Int1Elem] extends Any, BuffIntN[A], SlInt1[A]
{ type ThisT <: BuffInt1[A]

  final override def length: Int = bufferUnsafe.length
  final override def numElems: Int = bufferUnsafe.length
  final override def apply(i1: Int): A = elemFromInt(bufferUnsafe(i1))
  final override def elem(index: Int): A = elemFromInt(bufferUnsafe(index))
  override def grow(newElem: A): Unit = { bufferUnsafe.append(newElem.int1); () }
  override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe(index) = newElem.int1
}

/** [[BuilderBoth]] trait for constructing [[Arr]]s with [[Int1Elem]] elements via both map and flatMap methods. */
trait BuilderArrInt1[ArrB <: ArrInt1[?]] extends BuilderSlIntN[ArrB]
{ type BuffT <: BuffInt1[?]
  final override def elemProdSize: Int = 1
}

/** [[BuilderMap]] trait for constructing [[Arr]]s of [[Int1Elem]]s. impliciit instances for the builder type class, should go in the companion object of B. */
trait BuilderMapArrInt1[A <: Int1Elem, ArrT <: ArrInt1[A]] extends BuilderArrInt1[ArrT] with BuilderArrIntNMap[A, ArrT]
{ type BuffT <: BuffInt1[A]
  final override def indexSet(seqLike: ArrT, index: Int, newElem: A): Unit =  seqLike.arrayUnsafe(index) = newElem.int1
  final override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.bufferUnsafe.append(newElem.int1); () }
}

/** [[BuilderFlat]] trait for constructing [[Arr]]s with [[Int1Elem]]s via the flatMap method. implicit type class instances for types, should go in the
 * companion object of the [[Arr]]s. */
trait BuilderFlatArrIn1[ArrB <: ArrInt1[?]] extends BuilderArrInt1[ArrB] with BuilderArrIntNFlat[ArrB]

/** Helper trait for companion objects of [[SeqLikeImut]]s, with [[Int1Elem]]s. */
trait CompanionSlInt1[A <: Int1Elem, ArrA <: SlInt1Imut[A]] extends CompanionSlIntN[A, ArrA]
{ final override def elemNumInts: Int = 1

  /** Apply factory method */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count).int1
      count += 1
    }
    res
  }
}