/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A polygon using vertices specified in [[PtKm2]] points rather than scalars. */
final class PolygonKm2(val arrayUnsafe: Array[Double]) extends AnyVal, PolygonLen2[PtKm2]
{ type ThisT = PolygonKm2
  type SideT = LineSegKm2
  override def typeStr: String = "PolygonKm2"
  def fromArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
  override def elemFromDbls(d1: Double, d2: Double): PtKm2 = PtKm2.apply(d1, d2)
  override def fElemStr: PtKm2 => String = _.toString
  override def verts: PtKm2Arr = new PtKm2Arr(arrayUnsafe)
  override def slate(operand: VecPtLen2): PolygonKm2 = dblsMap(_ + operand.xKilometresNum, _ + operand.yKilometresNum)
  override def slate(xOperand: Length, yOperand: Length): PolygonKm2 = dblsMap(_ + xOperand.kilometresNum, _ + yOperand.kilometresNum)
  override def slateX(xOperand: Length): PolygonKm2 = dblsMap(_ + xOperand.kilometresNum, y => y)
  override def slateY(yOperand: Length): PolygonKm2 = dblsMap(x => x, _ + yOperand.kilometresNum)
  override def scale(operand: Double): PolygonKm2 = dblsMap(_ * operand, _ + operand)
  override def mapGeom2(operand: Length): Polygon = Polygon.fromArray(arrayUnsafe.map(_ / operand.kilometresNum))

  /** Performs the side effecting function on the [[PtKm2]] value of each vertex. */
  override def vertsForeach[U](f: PtKm2 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtKm2 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  @inline override def side(index: Int): LineSegKm2 = LineSegKm2(vert(index), vert(index + 1))
  override def sides: LineSegKm2Arr = new LineSegKm2Arr(arrayForSides)

  override def sidesForeach[U](f: LineSegKm2 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  override def revY: PolygonKm2 = map(_.revY)
  override def revYIf(cond: Boolean): PolygonKm2 = ife(cond, revY, this)
  override def rotate180: PolygonKm2 = map(_.rotate180)
  override def rotate180If(cond: Boolean): PolygonKm2 = ife(cond, map(_.rotate180), this)
  override def rotate180IfNot(cond: Boolean): PolygonKm2 = ife(cond, this, map(_.rotate180))
}

/** The companion object for [[PolygonKm2]]. Provides an implicit builder. */
object PolygonKm2 extends CompanionSeqLikeDbl2[PtKm2, PolygonKm2]
{ override def fromArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonKm2, PolygonKm2Arr] = new BuilderArrMap[PolygonKm2, PolygonKm2Arr]
  { override type BuffT = PolygonKm2Buff
    override def newBuff(length: Int): PolygonKm2Buff = PolygonKm2Buff(length)
    override def uninitialised(length: Int): PolygonKm2Arr = new PolygonKm2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonKm2Arr, index: Int, newElem: PolygonKm2): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonKm2Buff, newElem: PolygonKm2): Unit = buff.bufferUnsafe.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonKm2Buff): PolygonKm2Arr = new PolygonKm2Arr(buff.bufferUnsafe.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonKm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtKm2, PolygonKm2] = PersistSeqSpecBoth[PtKm2, PolygonKm2]("PolygonKm2")
}

/** Arr of [[PolygonKm2]]s. */
class PolygonKm2Arr(val arrayOfArraysUnsafe:Array[Array[Double]]) extends ArrArrayDbl[PolygonKm2]
{ override type ThisT = PolygonKm2Arr
  override def typeStr: String = "PolygonKm2Arr"
  override def fElemStr: PolygonKm2 => String = _.toString
  override def elemFromArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
  override def fromArrayArray(array: Array[Array[Double]]): PolygonKm2Arr = new PolygonKm2Arr(array)
}

/** Buff of [[PolygonKm2]]s. Not to be confused with [[Pt2Km2Buff]]. */
class PolygonKm2Buff(val bufferUnsafe: ArrayBuffer[Array[Double]]) extends AnyVal, BuffArrayDbl[PolygonKm2]
{ override type ThisT = PolygonKm2Buff
  override def typeStr: String = "PolygonKm2Buff"
  override def fromArrayDbl(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
}

/** Companion object of the [[PolygonKm2Buff]] class, a Buff of [[PolygonKm2]]s, contains factory apply method. */
object PolygonKm2Buff
{ /** Factory apply method for empty [[PolygonKm2Buff]]. Not to be confused with [[PtKn2Buff]]. */
  def apply(initLen: Int = 4): PolygonKm2Buff = new PolygonKm2Buff(new ArrayBuffer[Array[Double]](initLen))
}
class PolygonKm2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtKm2, PolygonKm2, A2]{
  override def a1: PolygonKm2 = new PolygonKm2(a1ArrayDbl)
}

object PolygonKm2Pair
{ implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonKm2Pair[A2], PolygonKm2PairArr[A2]] = new PolygonKm2PairBuilder[A2]
}

final class PolygonKm2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtKm2, PolygonKm2, PolygonKm2Arr, A2, PolygonKm2Pair[A2]]
{ override type ThisT = PolygonKm2PairArr[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonKm2Pair[A2]): Unit = { a1ArrayDbls(index) = newElem.a1ArrayDbl; a2Array(index) = newElem.a2 }
  override def fElemStr: PolygonKm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def elem(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonKm2Arr = new PolygonKm2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
}

final class PolygonKm2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtKm2, PolygonKm2, PolygonKm2Arr, A2, PolygonKm2Pair[A2], PolygonKm2PairArr[A2]]
{ override type BuffT = PolygonKm2PairBuff[A2]
  override type B1BuffT = PolygonKm2Buff
  override def uninitialised(length: Int): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonKm2PairArr[A2], index: Int, newElem: PolygonKm2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonKm2PairBuff[A2] = new PolygonKm2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonKm2PairBuff[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeBuilderMap[PtKm2, PolygonKm2] = PtKm2.polygonBuildEv
  override def b1ArrBuilder: BuilderArrMap[PolygonKm2, PolygonKm2Arr] = PolygonKm2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonKm2Arr, b2s: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](b1Arr.arrayOfArraysUnsafe, b2s)
  override def newB1Buff(): PolygonKm2Buff = PolygonKm2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonKm2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtKm2, PolygonKm2, A2, PolygonKm2Pair[A2]]
{ override type ThisT = PolygonKm2PairBuff[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonKm2Pair[A2]): Unit = { b1Buffer(index) = newElem.a1ArrayDbl; b2Buffer(index) = newElem.a2 }
  override def fElemStr: PolygonKm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](b1Buffer(index), b2Buffer(index))
  override def elem(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](b1Buffer(index), b2Buffer(index))
}