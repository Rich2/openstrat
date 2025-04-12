/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A polygon using vertices specified in [[PtFm2]] points rather than scalars. */
final class PolygonFm2(val arrayUnsafe: Array[Double]) extends AnyVal, PolygonLen2[PtFm2], PtFm2SeqLike
{ type ThisT = PolygonFm2
  type SideT = LineSegFm2
  override def typeStr: String = "PolygonFm2"
  def fromArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)  
  override def verts: PtFm2Arr = new PtFm2Arr(arrayUnsafe)
  override def mapGeom2(operand: Length): Polygon = Polygon.fromArray(arrayUnsafe.map(_ / operand.femtometresNum))
  override def slate(operand: VecPtLen2): PolygonFm2 = dblsMap(_ + operand.xFemtometresNum, _ + operand.yFemtometresNum)
  override def slate(xOperand: Length, yOperand: Length): PolygonFm2 = dblsMap(_ + xOperand.femtometresNum, _ + yOperand.femtometresNum)
  override def slateX(xOperand: Length): PolygonFm2 = dblsMap(_ + xOperand.femtometresNum, y => y)
  override def slateY(yOperand: Length): PolygonFm2 = dblsMap(x => x, _ + yOperand.femtometresNum)
  override def scale(operand: Double): PolygonFm2 = dblsMap(_ * operand, _ * operand)

  /** Performs the side effecting function on the [[PtFm2]] value of each vertex. */
  override def vertsForeach[U](f: PtFm2 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtFm2 => B)(implicit builder: BuilderMapArr[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  @inline override def side(index: Int): LineSegFm2 = LineSegFm2(vert(index), vert(index + 1))
  override def sides: LineSegFm2Arr = new LineSegFm2Arr(arrayForSides)

  override def sidesForeach[U](f: LineSegFm2 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  override def revY: PolygonFm2 = map(_.revY)
  override def revYIf(cond: Boolean): PolygonFm2 = ife(cond, revY, this)
  override def rotate180: PolygonFm2 = map(_.rotate180)
  override def rotate180If(cond: Boolean): PolygonFm2 = ife(cond, map(_.rotate180), this)
  override def rotate180IfNot(cond: Boolean): PolygonFm2 = ife(cond, this, map(_.rotate180))
}

/** The companion object for [[PolygonFm2]]. Provides an implicit builder. */
object PolygonFm2 extends CompanionSlDbl2[PtFm2, PolygonFm2]
{ override def fromArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)

  implicit val arrBuildImplicit: BuilderMapArr[PolygonFm2, PolygonFm2Arr] = new BuilderMapArr[PolygonFm2, PolygonFm2Arr]
  { override type BuffT = PolygonFm2Buff
    override def newBuff(length: Int): PolygonFm2Buff = PolygonFm2Buff(length)
    override def uninitialised(length: Int): PolygonFm2Arr = new PolygonFm2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonFm2Arr, index: Int, newElem: PolygonFm2): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonFm2Buff, newElem: PolygonFm2): Unit = buff.bufferUnsafe.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonFm2Buff): PolygonFm2Arr = new PolygonFm2Arr(buff.bufferUnsafe.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonFm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtFm2, PolygonFm2] = PersistSeqSpecBoth[PtFm2, PolygonFm2]("PolygonFm2")
}

/** [[Arr]] of [[PolygonFm2]]s. */
class PolygonFm2Arr(val arrayOfArraysUnsafe:Array[Array[Double]]) extends ArrArrayDbl[PolygonFm2]
{ override type ThisT = PolygonFm2Arr
  override def typeStr: String = "PolygonFm2Arr"
  override def fElemStr: PolygonFm2 => String = _.toString
  override def elemFromArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
  override def fromArrayArray(array: Array[Array[Double]]): PolygonFm2Arr = new PolygonFm2Arr(array)
}

/** Buff of [[PolygonFm2]]s. Not to be confused with [[PtFm2Buff]]. */
class PolygonFm2Buff(val bufferUnsafe: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonFm2]
{ override type ThisT = PolygonFm2Buff
  override def typeStr: String = "PolygonFm2Buff"
  override def fElemStr: PolygonFm2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
}

/** Companion object of the [[PolygonFm2Buff]] class, a Buff of [[PolygonFm2]]s, contains factory apply method. */
object PolygonFm2Buff
{ /** Factory apply method for creating an empty [[PolygonFm2Buff]]. Not to ne confused with a [[PtFm2Buff]]. */
  def apply(initLen: Int = 4): PolygonFm2Buff = new PolygonFm2Buff(new ArrayBuffer[Array[Double]](initLen))
}
class PolygonFm2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtFm2, PolygonFm2, A2]{
  override def a1: PolygonFm2 = new PolygonFm2(a1ArrayDbl)
}

object PolygonFm2Pair
{ implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderMapArr[PolygonFm2Pair[A2], PolygonFm2PairArr[A2]] = new PolygonFm2PairBuilder[A2]
}

final class PolygonFm2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtFm2, PolygonFm2, PolygonFm2Arr, A2, PolygonFm2Pair[A2]]
{ override type ThisT = PolygonFm2PairArr[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonFm2Pair[A2]): Unit = { a1ArrayDbls(index) = newElem.a1ArrayDbl; a2Array(index) = newElem.a2 }
  override def fElemStr: PolygonFm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonFm2Pair[A2] = new PolygonFm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def elem(index: Int): PolygonFm2Pair[A2] = new PolygonFm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonFm2Arr = new PolygonFm2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonFm2PairArr[A2] = new PolygonFm2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
}

final class PolygonFm2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtFm2, PolygonFm2, PolygonFm2Arr, A2, PolygonFm2Pair[A2], PolygonFm2PairArr[A2]]
{ override type BuffT = PolygonFm2PairBuff[A2]
  override type B1BuffT = PolygonFm2Buff
  override def uninitialised(length: Int): PolygonFm2PairArr[A2] = new PolygonFm2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonFm2PairArr[A2], index: Int, newElem: PolygonFm2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonFm2PairBuff[A2] = new PolygonFm2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonFm2PairBuff[A2]): PolygonFm2PairArr[A2] = new PolygonFm2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeBuilderMap[PtFm2, PolygonFm2] = PtFm2.polygonBuildImplicit
  override def b1ArrBuilder: BuilderMapArr[PolygonFm2, PolygonFm2Arr] = PolygonFm2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonFm2Arr, b2s: Array[A2]): PolygonFm2PairArr[A2] = new PolygonFm2PairArr[A2](b1Arr.arrayOfArraysUnsafe, b2s)
  override def newB1Buff(): PolygonFm2Buff = PolygonFm2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonFm2PairArr[A2] = new PolygonFm2PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonFm2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  BuffPairSlDblN[PtFm2, PolygonFm2, A2, PolygonFm2Pair[A2]]
{ override type ThisT = PolygonFm2PairBuff[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonFm2Pair[A2]): Unit = { b1Buffer(index) = newElem.a1ArrayDbl; b2Buffer(index) = newElem.a2 }
  override def fElemStr: PolygonFm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonFm2PairBuff"
  override def apply(index: Int): PolygonFm2Pair[A2] = new PolygonFm2Pair[A2](b1Buffer(index), b2Buffer(index))
  override def elem(index: Int): PolygonFm2Pair[A2] = new PolygonFm2Pair[A2](b1Buffer(index), b2Buffer(index))
}