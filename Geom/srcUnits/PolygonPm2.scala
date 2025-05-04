/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A polygon using vertices specified in [[PtPm2]] points rather than scalars. */
final class PolygonPm2(val arrayUnsafe: Array[Double]) extends AnyVal, PolygonLen2[PtPm2]
{ type ThisT = PolygonPm2
  type SideT = LineSegPm2
  override def typeStr: String = "PolygonPm2"
  def fromArray(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
  override def elemFromDbls(d1: Double, d2: Double): PtPm2 = PtPm2(d1, d2)
  override def fElemStr: PtPm2 => String = _.toString
  override def verts: PtPm2Arr = new PtPm2Arr(arrayUnsafe)

  /** The X component of vertex v0, will throw on a 0 vertices polygon. */
  override def v0x: Length = ???

  /** The Y component of vertex v1, will throw on a 0 vertices polygon. */
  override def v0y: Length = ???

  /** Vertex v0, will throw on a 0 vertices polygon. By convention the default position for this vertex is at the top or 12 o'clock position of the polygon or
   * the vertex immediately anti-clockwise if there is no vertex in this position. */
  override def v0: PtLen2 = ???

  override def slate(operand: VecPtLen2): PolygonPm2 = dblsMap(_ + operand.xPicometresNum, _ + operand.yPicometresNum)
  override def slate(xOperand: Length, yOperand: Length): PolygonPm2 = dblsMap(_ + xOperand.picometresNum, _ + yOperand.picometresNum)
  override def slateX(xOperand: Length): PolygonPm2 = dblsMap(_ + xOperand.picometresNum, y => y)
  override def slateY(yOperand: Length): PolygonPm2 = dblsMap(x => x, _ + yOperand.picometresNum)
  override def scale(operand: Double): PolygonPm2 = dblsMap(_ * operand, _ * operand)
  override def mapGeom2(operand: Length): Polygon = Polygon.fromArray(arrayUnsafe.map(_ / operand.picometresNum))

  /** Performs the side effecting function on the [[PtPm2]] value of each vertex. */
  override def vertsForeach[U](f: PtPm2 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtPm2 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  @inline override def side(index: Int): LineSegPm2 = LineSegPm2(vert(index), vert(index + 1))
  override def sides: LineSegPm2Arr = new LineSegPm2Arr(arrayForSides)

  override def sidesForeach[U](f: LineSegPm2 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  override def revY: PolygonPm2 = map(_.revY)
  override def revYIf(cond: Boolean): PolygonPm2 = ife(cond, revY, this)
  override def rotate180: PolygonPm2 = map(_.rotate180)
  override def rotate180If(cond: Boolean): PolygonPm2 = ife(cond, map(_.rotate180), this)
  override def rotate180IfNot(cond: Boolean): PolygonPm2 = ife(cond, this, map(_.rotate180))
}

/** The companion object for [[PolygonPm2]]. Provides an implicit builder. */
object PolygonPm2 extends CompanionSlDbl2[PtPm2, PolygonPm2]
{ override def fromArray(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonPm2, PolygonPm2Arr] = new BuilderArrMap[PolygonPm2, PolygonPm2Arr]
  { override type BuffT = PolygonPm2Buff
    override def newBuff(length: Int): PolygonPm2Buff = PolygonPm2Buff(length)
    override def uninitialised(length: Int): PolygonPm2Arr = new PolygonPm2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonPm2Arr, index: Int, newElem: PolygonPm2): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonPm2Buff, newElem: PolygonPm2): Unit = buff.bufferUnsafe.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonPm2Buff): PolygonPm2Arr = new PolygonPm2Arr(buff.bufferUnsafe.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonPm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtPm2, PolygonPm2] = PersistSeqSpecBoth[PtPm2, PolygonPm2]("PolygonPm2")
}

/** [[Arr]] of [[PolygonPm2]]s. */
class PolygonPm2Arr(val arrayOfArraysUnsafe:Array[Array[Double]]) extends ArrArrayDbl[PolygonPm2]
{ override type ThisT = PolygonPm2Arr
  override def typeStr: String = "PolygonPm2Arr"
  override def fElemStr: PolygonPm2 => String = _.toString

  override def elemFromArray(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
  override def fromArrayArray(array: Array[Array[Double]]): PolygonPm2Arr = new PolygonPm2Arr(array)
}

/** Buff of [[PolygonPm2]]s. Not to be confused with a [[PtPm2Buff]], that you would use to build an individual [[PolygonPm2]]. */
class PolygonPm2Buff(val bufferUnsafe: ArrayBuffer[Array[Double]]) extends AnyVal, BuffArrayDbl[PolygonPm2]
{ override type ThisT = PolygonPm2Buff
  override def typeStr: String = "PolygonPm2Buff"
  override def fElemStr: PolygonPm2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
}

/** Companion object of the [[PolygonPm2Buff]] class, a Buff of [[PolygonPm2]]s, contains factory apply method. */
object PolygonPm2Buff
{ /** Factory apply method for Buff class for [[PolygonPm2]]s. Not to be confused with a [[PtPm2Buff]]. */
  def apply(initLen: Int = 4): PolygonPm2Buff = new PolygonPm2Buff(new ArrayBuffer[Array[Double]](initLen))
}

class PolygonPm2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtPm2, PolygonPm2, A2]{
  override def a1: PolygonPm2 = new PolygonPm2(a1ArrayDbl)
}

object PolygonPm2Pair
{ /** Implicit [[BuilderArrMap]] type class instances / evidence for [[PolygonPm2Pair]]. */
  implicit def buildArrMapEv[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonPm2Pair[A2], PolygonPm2PairArr[A2]] = new PolygonPm2PairBuilder[A2]
}

final class PolygonPm2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtPm2, PolygonPm2, PolygonPm2Arr, A2, PolygonPm2Pair[A2]]
{ override type ThisT = PolygonPm2PairArr[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonPm2Pair[A2]): Unit = { a1ArrayDbls(index) = newElem.a1ArrayDbl; a2Array(index) = newElem.a2 }
  override def fElemStr: PolygonPm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonPm2Pair[A2] = new PolygonPm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def elem(index: Int): PolygonPm2Pair[A2] = new PolygonPm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonPm2Arr = new PolygonPm2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonPm2PairArr[A2] = new PolygonPm2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
}

final class PolygonPm2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtPm2, PolygonPm2, PolygonPm2Arr, A2, PolygonPm2Pair[A2], PolygonPm2PairArr[A2]]
{ override type BuffT = PolygonPm2PairBuff[A2]
  override type B1BuffT = PolygonPm2Buff
  override def uninitialised(length: Int): PolygonPm2PairArr[A2] = new PolygonPm2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonPm2PairArr[A2], index: Int, newElem: PolygonPm2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonPm2PairBuff[A2] = new PolygonPm2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonPm2PairBuff[A2]): PolygonPm2PairArr[A2] = new PolygonPm2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeBuilderMap[PtPm2, PolygonPm2] = PtPm2.polygonBuilderEv
  override def b1ArrBuilder: BuilderArrMap[PolygonPm2, PolygonPm2Arr] = PolygonPm2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonPm2Arr, b2s: Array[A2]): PolygonPm2PairArr[A2] = new PolygonPm2PairArr[A2](b1Arr.arrayOfArraysUnsafe, b2s)
  override def newB1Buff(): PolygonPm2Buff = PolygonPm2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonPm2PairArr[A2] = new PolygonPm2PairArr[A2](arrayArrayDbl, a2Array)
}

/** Not sure if this class needs to exist. */
class PolygonPm2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  BuffPairSeqLikeDblN[PtPm2, PolygonPm2, A2, PolygonPm2Pair[A2]]
{ override type ThisT = PolygonPm2PairBuff[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonPm2Pair[A2]): Unit = { b1Buffer(index) = newElem.a1ArrayDbl; b2Buffer(index) = newElem.a2 }
  override def fElemStr: PolygonPm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonPm2Pair[A2] = new PolygonPm2Pair[A2](b1Buffer(index), b2Buffer(index))
  override def elem(index: Int): PolygonPm2Pair[A2] = new PolygonPm2Pair[A2](b1Buffer(index), b2Buffer(index))
}