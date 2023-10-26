/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

trait Int3PairElem[A1 <: Int3Elem, A2] extends IntNPairElem[A1, A2]
{ def a1Int1: Int
  def a1Int2: Int
  def a1Int3: Int
}

trait Int3PairArr[A1 <: Int3Elem, ArrA1 <: ArrInt3[A1], A2, A <: Int3PairElem[A1, A2]] extends IntNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Int3PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newPair(int1: Int, int2: Int, int3: Int, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayInt(index * 3), a1ArrayInt(index * 3 + 1), a1ArrayInt(index * 3 + 2), a2Array(index))

  override final def setElemUnsafe(i: Int, newElem: A): Unit = { a1ArrayInt.setIndex3(i, newElem.a1Int1, newElem.a1Int2, newElem.a1Int3)
    a2Array(i) = newElem.a2 }

  def newA1(int1: Int, int2: Int, int3: Int): A1

  override def a1Index(index: Int): A1 = newA1(a1ArrayInt(index * 3), a1ArrayInt(index * 3 + 1), a1ArrayInt(index * 3 + 2))
  override def a1NumInt: Int = 3

  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayInt.setIndex3(index, value.int1, value.int2, value.int3)

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Int](a1ArrayLength + 3)
    a1ArrayInt.copyToArray(newA1Array)
    newA1Array.setIndex3(length, a1.int1, a1.int2, a1.int3)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Int3PairBuff[A1 <: Int3Elem, A2, A <: Int3PairElem[A1, A2]] extends IntNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 3 [[Int]]s and a third parameter of type A2. */
  def newElem(int1: Int, int2: Int, int3: Int, a2: A2): A

  inline final override def apply(index: Int): A = newElem(b1IntBuffer (index * 3), b1IntBuffer(index * 3 + 1), b1IntBuffer(index * 3 + 2), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1IntBuffer.append3(newElem.a1Int1, newElem.a1Int2, newElem.a1Int3)
    b2Buffer.append(newElem.a2)
  }

  override final def setElemUnsafe(i: Int, newElem: A): Unit =
  { b1IntBuffer.setIndex3(i, newElem.a1Int1, newElem.a1Int2, newElem.a1Int3)
    b2Buffer(i) = newElem.a2
  }
}

trait Int3PairArrMapBuilder[B1 <: Int3Elem, ArrB1 <: ArrInt3[B1], B2, B <: Int3PairElem[B1, B2], ArrB <: Int3PairArr[B1, ArrB1, B2, B]] extends
  IntNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Int3PairBuff[B1, B2, B]

  final override def a1IntNum: Int = 3

  final override def indexSet(seqLike: ArrB, index: Int, elem: B): Unit = { seqLike.a1ArrayInt.setIndex3(index, elem.a1Int1, elem.a1Int2, elem.a1Int3)
    seqLike.a2Array(index) = elem.a2 }
}

trait Int3PairArrCompanion[A1 <: Int3Elem]
{
  def pairsToArrays[A2](pairs: Seq[Int3PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  {  val intsArray = new Array[Int](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      intsArray.setIndex3(i, p.a1Int1, p.a1Int2, p.a1Int3)
      a2Array(i) = p.a2
      i += 1
    }
    (intsArray, a2Array)
  }

  def tuplesToArrays[A2](pairs: Seq[(Int3Elem, A2)])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) = {
    val intsArray = new Array[Int](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach { p =>
      intsArray.setIndex3(i, p._1.int1, p._1.int2, p._1.int3)
      a2Array(i) = p._2
      i += 1
    }
    (intsArray, a2Array)
  }

  /** Not sure about the implementation of this method. */
  def reverseTuplesToArrays[A2](pairs: Seq[(A2, Int3Elem)])(implicit ct: ClassTag[A2]): (Array[Int], Array[A2]) =
  { deb("Not sure about the imlementation of reverseTuplesToArrays method.")
    val intsArray = new Array[Int](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach { p =>
      intsArray.setIndex3(i, p._2.int1, p._2.int2, p._2.int3)
      a2Array(i) = p._1
      i += 1
    }
    (intsArray, a2Array)
  }
}