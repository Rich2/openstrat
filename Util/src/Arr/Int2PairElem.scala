/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

trait Int2PairElem[A1 <: Int2Elem, A2] extends IntNPairElem[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
}

trait Int2PairArr[A1 <: Int2Elem, ArrA1 <: Int2Arr[A1], A2, A <: Int2PairElem[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int2PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayInt(index * 2), a1ArrayInt(index * 2 + 1), a2Array(index))

  override final def setElemUnsafe(i: Int, newElem: A): Unit = { a1ArrayInt.setIndex2(i, newElem.a1Int1, newElem.a1Int2); a2Array(i) = newElem.a2 }

  def newA1(int1: Int, int2: Int): A1

  final override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 2), a1ArrayInt(index * 2 + 1))
  final override def a1NumInt: Int = 2

  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayInt.setIndex2(index, value.int1, value.int2)

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Int](a1ArrayLength + 2)
    a1ArrayInt.copyToArray(newA1Array)
    newA1Array.setIndex2(length, a1.int1, a1.int2)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Int2PairBuff[A1 <: Int2Elem, A2, A <: Int2PairElem[A1, A2]] extends IntNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, int2: Int, a2: A2): A
  inline final override def apply(index: Int): A = newElem(b1IntBuffer (index * 2), b1IntBuffer(index * 2 + 1), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1IntBuffer.append(newElem.a1Int1)
    b1IntBuffer.append(newElem.a1Int2)
    b2Buffer.append(newElem.a2)
  }

  def grow(newA1: A1, newA2: A2): Unit =
  { b1IntBuffer.append(newA1.int1)
    b1IntBuffer.append(newA1.int2)
    b2Buffer.append(newA2)
  }

  override final def setElemUnsafe(i: Int, newElem: A): Unit =
  { b1IntBuffer(i * 2) = newElem.a1Int1
    b1IntBuffer(i * 2 + 1) = newElem.a1Int2
    b2Buffer(i) = newElem.a2
  }
}

trait Int2PairArrMapBuilder[B1 <: Int2Elem, ArrB1 <: Int2Arr[B1], B2, B <: Int2PairElem[B1, B2], ArrB <: Int2PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int2PairBuff[B1, B2, B]
  final override def a1IntNum: Int = 2

  final override def indexSet(seqLike: ArrB, index: Int, elem: B): Unit = { seqLike.a1ArrayInt.setIndex2(index, elem.a1Int1, elem.a1Int2)
    seqLike.a2Array(index) = elem.a2 }
}

trait Int2PairArrCompanion[A1 <: Int2Elem]
{
  def seqToArrays[A2](pairs: Seq[Int2PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 2)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray.setIndex2(i, p.a1Int1, p.a1Int2)
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}