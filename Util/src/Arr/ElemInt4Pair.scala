/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** Pair where the first component is an [[ElemInt4]]. This allows these pair elements to be stored efficently in [[Int4PAirArr]]s, where the first
 * [[ElemInt4]] components are backed bya single [[Array]][Int]. */
trait ElemInt4Pair[A1 <: ElemInt4, A2] extends ElemIntNPair[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
  def a1Int3: Int
  def a1Int4: Int
}

trait Int4PairArr[A1 <: ElemInt4, ArrA1 <: Int4Arr[A1], A2, A <: ElemInt4Pair[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int4PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, int3: Int, Int4: Int, a2: A2): A

  def newA1(int1: Int, int2: Int, int3: Int, int4: Int): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3))

  final override def apply(index: Int): A =
    newPair(a1ArrayInt(index * 4), a1ArrayInt(index * 4 + 1), a1ArrayInt(index * 4 + 2), a1ArrayInt(index * 4 + 3), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayInt(i * 4) = value.a1Int1;
    a1ArrayInt(i * 4 + 1) = value.a1Int2
    a1ArrayInt(i * 4 + 2) = value.a1Int3
    a1ArrayInt(i * 4 + 3) = value.a1Int4
    a2Array(i) = value.a2
  }
}

trait Int4PairBuff[B1 <: ElemInt4, B2, B <: ElemInt4Pair[B1, B2]] extends IntNPairBuff[B1, B2, B]
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

  override final def unsafeSetElem(i: Int, value: B): Unit =
  { b1IntBuffer(i * 4) = value.a1Int1
    b1IntBuffer(i * 4 + 1) = value.a1Int2
    b1IntBuffer(i * 4 + 2) = value.a1Int3
    b1IntBuffer(i * 4 + 3) = value.a1Int4
    b2Buffer(i) = value.a2
  }
}

trait Int4PairArrMapBuilder[B1 <: ElemInt4, ArrB1 <: Int4Arr[B1], B2, B <: ElemInt4Pair[B1, B2], ArrB <: Int4PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int4PairBuff[B1, B2, B]
  override type B1BuffT <: Int4Buff[B1]
  final override def a1IntNum: Int = 4

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit = {
    arr.a1ArrayInt(index * 4) = value.a1Int1
    arr.a1ArrayInt(index * 4 + 1) = value.a1Int2
    arr.a1ArrayInt(index * 4 + 2) = value.a1Int3
    arr.a1ArrayInt(index * 4 + 3) = value.a1Int4
    arr.a2Array(index) = value.a2
  }
}

trait Int4PairArrCompanion[A1 <: ElemInt4, ArrA1 <: Int4Arr[A1]] extends IntNPairArrCompanion[A1, ArrA1]
{
  override def elemNumInts: Int = 4

  def seqToArrays[A2](pairs: Seq[ElemInt4Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
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