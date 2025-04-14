/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, reflect.ClassTag

/** Pair where the first component is an [[Int4Elem]]. This allows these pair elements to be stored efficiently in [[Int4PAirArr]]s, where the first
 * [[Int4Elem]] components are backed by a single [[Array]][Int]. */
trait PairInt4Elem[A1 <: Int4Elem, A2] extends PairIntNElem[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
  def a1Int3: Int
  def a1Int4: Int
}

/** [[SeqLike]] of [[PairElems]] where the first component of each of the pairs is an [[Int4Elem]]. */
trait SlPairInt4[A1 <: Int4Elem, A2, A <: PairInt4Elem[A1, A2]] extends SequPairIntN[A1, A2, A]
{ /** Constructs new pair element from 4 [[Int]]s and a third parameter of type A2. */
  def elemFromInts(int1: Int, int2: Int, int3: Int, Int4: Int, a2: A2): A

  final override def a1NumInt: Int = 4
}

/** An [[Arr]] of [[PairElem]]s where the first component is an [[Int4Elem]]. */
trait ArrPairInt4[A1 <: Int4Elem, ArrA1 <: ArrInt4[A1], A2, A <: PairInt4Elem[A1, A2]] extends ArrPairIntN[A1, ArrA1, A2, A], SlPairInt4[A1, A2, A]
{ type ThisT <: ArrPairInt4[A1, ArrA1, A2, A]

  def a1FromInts(int1: Int, int2: Int, int3: Int, int4: Int): A1
  final override def a1Index(index: Int): A1 = a1FromInts(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3))

  final override def apply(index: Int): A =
    elemFromInts(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3), a2Array(index))

  final override def elem(index: Int): A = elemFromInts(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3), a2Array(index))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = { setA1Unsafe(index, newElem.a1);  a2Array(index) = newElem.a2 }
  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayInt.setIndex4(index, value.int1, value.int2, value.int3, value.int4)

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Int](a1ArrayLength + 4)
    a1ArrayInt.copyToArray(newA1Array)
    newA1Array.setIndex4(length, a1.int1, a1.int2, a1.int3, a1.int4)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

/** Efficient buffer class for [[PairInt4Elem]]s. */
trait BuffPairInt4[B1 <: Int4Elem, B2, B <: PairInt4Elem[B1, B2]] extends BuffPairIntN[B1, B2, B]
{ /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def elemFromInts(int1: Int, int2: Int, int3: Int, int4: Int, a2: B2): B

  final override def apply(index: Int): B =
    elemFromInts(b1IntBuffer (index * 4), b1IntBuffer(index * 4 + 1), b1IntBuffer(index * 4 + 2), b1IntBuffer(index * 4 + 3), b2Buffer(index))

  final override def elem(index: Int): B = elemFromInts(b1IntBuffer (index * 4), b1IntBuffer(index * 4 + 1), b1IntBuffer(index * 4 + 2), b1IntBuffer(index * 4 + 3), b2Buffer(index))

  override final def grow(newElem: B): Unit =
  { b1IntBuffer.append4(newElem.a1Int1, newElem.a1Int2, newElem.a1Int3, newElem.a1Int4)
    b2Buffer.append(newElem.a2)
  }

  override final def setElemUnsafe(index: Int, newElem: B): Unit =
  { b1IntBuffer.setIndex4(index, newElem.a1Int1, newElem.a1Int2, newElem.a1Int3, newElem.a1Int4)
    b2Buffer(index) = newElem.a2
  }
}

/** [[BuilderBoth]] trait for constructing [[Arr]]s of [[PairElems]], where the first component of each of the pairs is an [[Int4Elem]], by both map and flatMap
 * methods. */
trait BuilderArrPairInt4[B1 <: Int4Elem, ArrB1 <: ArrInt4[B1], B2, ArrB <: ArrPairInt4[B1, ArrB1, B2, ?]] extends BuilderArrPairIntN[B1, ArrB1, B2, ArrB]
{ type BuffT <: BuffPairInt4[B1, B2, ?]
  type B1BuffT <: BuffInt4[B1]
}

/** [[BuilderMap]] trait for constructing [[Arr]] objects of [[PairElem]]s where the first component of each pair is an [[Int4Elem]] by the map method. */
trait BuilderMapArrPairInt4[B1 <: Int4Elem, ArrB1 <: ArrInt4[B1], B2, B <: PairInt4Elem[B1, B2], ArrB <: ArrPairInt4[B1, ArrB1, B2, B]] extends
  BuilderArrPairInt4[B1, ArrB1, B2, ArrB] with  BuilderArrPairIntNMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairInt4[B1, B2, B]
  final override def a1IntNum: Int = 4

  final override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
  { seqLike.a1ArrayInt.setIndex4(index, newElem.a1Int1, newElem.a1Int2, newElem.a1Int3, newElem.a1Int4)
    seqLike.a2Array(index) = newElem.a2
  }
}

/** Builders for [[ArrPairInt4]] objects by flatMap f: A => ArrPairB method. */
trait BuilderArrPairInt4Flat[B1 <: Int4Elem, ArrB1 <: ArrInt4[B1], B2, ArrB <: ArrPairInt4[B1, ArrB1, B2, ?]] extends BuilderArrPairInt4[B1, ArrB1, B2, ArrB],
  BuilderArrPairIntNFlat[B1, ArrB1, B2, ArrB]

trait Int4PairArrCompanion[A1 <: Int4Elem]
{ def seqToArrays[A2](pairs: Seq[PairInt4Elem[?, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 4)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray.setIndex4(i, p.a1Int1, p.a1Int2, p.a1Int3, p.a1Int4)
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}