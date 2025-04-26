/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, reflect.ClassTag

class LineSegPair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a1Dbl4: Double, val a2: A2) extends LineSegLikeDbl4Pair[Pt2, LSeg, A2]
{ inline def startX: Double = a1Dbl1
  inline def startY: Double = a1Dbl2
  inline def endX: Double = a1Dbl3
  inline def endY: Double = a1Dbl4
  override def a1: LSeg = new LSeg(startX, startY, endX, endY)
}

object LineSegPair
{ def apply[A2](ls: LSeg, a2: A2): LineSegPair[A2] = new LineSegPair[A2](ls.dbl1, ls.dbl2, ls.dbl3, ls.dbl4, a2)
}

final class LineSegPairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends LineSegLikeDbl4PairArr[Pt2, LSeg, LineSegArr, A2, LineSegPair[A2]]
{ override type ThisT = LineSegPairArr[A2]
  override def typeStr: String = "LineSeqArrPair"
  override def a1Arr: LineSegArr = new LineSegArr(a1ArrayDbl)
  override def elemFromDbls(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: A2): LineSegPair[A2] = new LineSegPair[A2](dbl1, dbl2, dbl3, dbl4, a2)
  override def a1FromDbls(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double): LSeg = new LSeg(dbl1, dbl2, dbl3, dbl4)
  override def fElemStr: LineSegPair[A2] => String = _.toString
  override def fromArrays(a1Array: Array[Double], a2Array: Array[A2]): LineSegPairArr[A2] = new LineSegPairArr[A2](a1Array, a2Array)
}

object LineSegPairArr{
  implicit def pairArrFlatBuilder[B2](implicit ct: ClassTag[B2]): BuilderFlatArrPair[LSeg, LineSegArr,B2, LineSegPairArr[B2]] = new LineSegPairArrFlatBuilder[B2]
}

class LineSegPairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl4[LSeg, B2, LineSegPair[B2]]
{ override type ThisT = LineSegPairBuff[B2]
  override def typeStr: String = "LineSegPairBuff"

  override def elemFromDbls(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: B2): LineSegPair[B2] =
    new LineSegPair[B2](dbl1, dbl2, dbl3, dbl4, a2)
}

trait LineSegPairArrCommonBuilder[B2] extends BuilderArrPairDbl4[LSeg, LineSegArr, B2, LineSegPairArr[B2]]
{ override type BuffT = LineSegPairBuff[B2]
  override type B1BuffT = LineSegBuff
  override def newB1Buff(): LineSegBuff = LineSegBuff()
  override def arrFromArrays(b1ArrayDbl: Array[Double], b2Array: Array[B2]): LineSegPairArr[B2] = new LineSegPairArr[B2](b1ArrayDbl, b2Array)

  override def buffFromBuffers(b1Buffer: ArrayBuffer[Double], b2Buffer: ArrayBuffer[B2]): LineSegPairBuff[B2] =
    new LineSegPairBuff[B2](b1Buffer, b2Buffer)
}

class LineSegPairArrMapBuilder[B2](implicit ct: ClassTag[B2]) extends LineSegPairArrCommonBuilder[B2] with
BuilderArrPairDbl4Map[LSeg, LineSegArr, B2, LineSegPair[B2], LineSegPairArr[B2]]
{
  override def b1ArrBuilder: BuilderMapArr[LSeg, LineSegArr] = LSeg.arrMapbuilderEv

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  override def arrFromArrAndArray(b1Arr: LineSegArr, b2s: Array[B2]): LineSegPairArr[B2] = ???

  override implicit def b2ClassTag: ClassTag[B2] = ct
}

class LineSegPairArrFlatBuilder[B2](implicit ct: ClassTag[B2]) extends LineSegPairArrCommonBuilder[B2] with
BuilderFlatArrPairDbl4[LSeg, LineSegArr, B2, LineSegPairArr[B2]]
{ override implicit def b2ClassTag: ClassTag[B2] = ct
}