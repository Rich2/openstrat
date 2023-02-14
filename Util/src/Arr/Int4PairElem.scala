/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

/** Pair where the first component is an [[Int4Elem]]. This allows these pair elements to be stored efficently in [[Int4PAirArr]]s, where the first
 * [[Int4Elem]] components are backed bya single [[Array]][Int]. */
trait Int4PairElem[A1 <: Int4Elem, A2] extends IntNPairElem[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
  def a1Int3: Int
  def a1Int4: Int
}

/** An [[Arr]] of [[PairNoA1ParamElem]]s where the first component is an [[Int4Elem]]. */
trait Int4PairArr[A1 <: Int4Elem, ArrA1 <: Int4Arr[A1], A2, A <: Int4PairElem[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int4PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, int3: Int, Int4: Int, a2: A2): A

  def newA1(int1: Int, int2: Int, int3: Int, int4: Int): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3))

  final override def apply(index: Int): A =
    newPair(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3), a2Array(index))

  override final def setElemUnsafe(i: Int, newElem: A): Unit = { setA1Unsafe(i, newElem.a1);  a2Array(i) = newElem.a2 }

  final override def setA1Unsafe(index: Int, value: A1): Unit = { a1ArrayInt(index * 4) = value.int1; a1ArrayInt(index * 4 + 1) = value.int2
    a1ArrayInt(index * 4 + 2) = value.int3; a1ArrayInt(index * 4 + 2) = value.int3 }

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Int](a1ArrayLength + 4)
    a1ArrayInt.copyToArray(newA1Array)
    newA1Array(a1ArrayLength) = a1.int1
    newA1Array(a1ArrayLength + 1) = a1.int2
    newA1Array(a1ArrayLength + 2) = a1.int3
    newA1Array(a1ArrayLength + 3) = a1.int3
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Int4PairBuff[B1 <: Int4Elem, B2, B <: Int4PairElem[B1, B2]] extends IntNPairBuff[B1, B2, B]
{ /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, int2: Int, int3: Int, int4: Int, a2: B2): B

  inline final override def apply(index: Int): B =
    newElem(b1IntBuffer (index * 4), b1IntBuffer(index * 4 + 1), b1IntBuffer(index * 4 + 2), b1IntBuffer(index * 4 + 3), b2Buffer(index))

  override final def grow(newElem: B): Unit =
  { b1IntBuffer.append(newElem.a1Int1)
    b1IntBuffer.append(newElem.a1Int2)
    b1IntBuffer.append(newElem.a1Int3)
    b1IntBuffer.append(newElem.a1Int4)
    b2Buffer.append(newElem.a2)
  }

  override final def setElemUnsafe(i: Int, newElem: B): Unit =
  { b1IntBuffer(i * 4) = newElem.a1Int1
    b1IntBuffer(i * 4 + 1) = newElem.a1Int2
    b1IntBuffer(i * 4 + 2) = newElem.a1Int3
    b1IntBuffer(i * 4 + 3) = newElem.a1Int4
    b2Buffer(i) = newElem.a2
  }
}

trait Int4PairArrCommonBuilder[B1 <: Int4Elem, ArrB1 <: Int4Arr[B1], B2, ArrB <: Int4PairArr[B1, ArrB1, B2, _]] extends
IntNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB]
{ type BuffT <: Int4PairBuff[B1, B2, _]
  type B1BuffT <: Int4Buff[B1]

}

trait Int4PairArrMapBuilder[B1 <: Int4Elem, ArrB1 <: Int4Arr[B1], B2, B <: Int4PairElem[B1, B2], ArrB <: Int4PairArr[B1, ArrB1, B2, B]] extends
Int4PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int4PairBuff[B1, B2, B]

  final override def a1IntNum: Int = 4

  final override def indexSet(seqLike: ArrB, index: Int, elem: B): Unit = {
    seqLike.a1ArrayInt(index * 4) = elem.a1Int1
    seqLike.a1ArrayInt(index * 4 + 1) = elem.a1Int2
    seqLike.a1ArrayInt(index * 4 + 2) = elem.a1Int3
    seqLike.a1ArrayInt(index * 4 + 3) = elem.a1Int4
    seqLike.a2Array(index) = elem.a2
  }
}

trait Int4PairArrFlatBuilder[B1 <: Int4Elem, ArrB1 <: Int4Arr[B1], B2, ArrB <: Int4PairArr[B1, ArrB1, B2, _]] extends
  Int4PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with  IntNPairArrFlatBuilder[B1, ArrB1, B2, ArrB]

trait Int4PairArrCompanion[A1 <: Int4Elem]
{
  def seqToArrays[A2](pairs: Seq[Int4PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 4)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray(i * 4) = p.a1Int1
      intsArray(i * 4 + 1) = p.a1Int2
      intsArray(i * 4 + 2) = p.a1Int3
      intsArray(i * 4 + 3) = p.a1Int4
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}