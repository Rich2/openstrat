/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

trait Int1PairElem[A1 <: Int1Elem, A2] extends IntNPairElem[A1, A2]
{ def a1Int1: Int
}

trait Int1PairArr[A1 <: Int1Elem, ArrA1 <: Int1Arr[A1], A2, A <: Int1PairElem[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int1PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayInt(index), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit ={ a1ArrayInt(i) = value.a1Int1; a2Array(i) = value.a2 }

  def newA1(int1: Int): A1

  final override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index))
  final override def a1NumInt: Int = 1
  final override def unsafeSetA1(index: Int, value: A1): Unit = { a1ArrayInt(index) = value.int1 }

  @targetName("append") final override def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final override def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Int](length + 1)
    a1ArrayInt.copyToArray(newA1Array)
    newA1Array(length) = a1.int1
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Int1PairBuff[A1 <: Int1Elem, A2, A <: Int1PairElem[A1, A2]] extends IntNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 2 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, a2: A2): A
  inline final override def apply(index: Int): A = newElem(b1IntBuffer (index), b2Buffer(index))

  override final def grow(newElem: A): Unit = { b1IntBuffer.append(newElem.a1Int1); b2Buffer.append(newElem.a2) }

  def grow(newA1: A1, newA2: A2): Unit = { b1IntBuffer.append(newA1.int1); b2Buffer.append(newA2) }

  override final def unsafeSetElem(i: Int, value: A): Unit = { b1IntBuffer(i) = value.a1Int1; b2Buffer(i) = value.a2 }
}

trait Int1PairArrMapBuilder[B1 <: Int1Elem, ArrB1 <: Int1Arr[B1], B2, B <: Int1PairElem[B1, B2], ArrB <: Int1PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int1PairBuff[B1, B2, B]
  final override def a1IntNum: Int = 1

  final override def indexSet(seqLike: ArrB, index: Int, value: B): Unit = { seqLike.a1ArrayInt(index) = value.a1Int1
    seqLike.a2Array(index) = value.a2 }
}

trait Int1PairArrCompanion[A1 <: Int1Elem]
{
  def seqToArrays[A2](pairs: Seq[Int1PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  { val intsArray = new Array[Int](pairs.length)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray(i) = p.a1Int1
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }
}