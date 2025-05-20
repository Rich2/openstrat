/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.*, reflect.ClassTag, collection.mutable.ArrayBuffer

trait PolygonM2 extends Any, PolygonLen2[PtM2]
{ def v0xMNum: Double
  def v0yMNum: Double
  final override def v0x: Metres = Metres(v0xMNum)
  final override def v0y: Metres = Metres(v0yMNum)
  final override def v0: PtM2 = PtM2(v0xMNum, v0yMNum)
}

/** A polygon where the vertices are specified in [[Metres]] rather than scalars. */
final class PolygonM2Gen(val arrayUnsafe: Array[Double]) extends AnyVal, PolygonM2
{ type ThisT = PolygonM2Gen
  type SideT = LSegM2
  override def typeStr: String = "PolygonM2"
  override def fromArray(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)
  override def elemFromDbls(d1: Double, d2: Double): PtM2 = PtM2.apply(d1, d2)
  override def fElemStr: PtM2 => String = _.toString
  override def verts: PtM2Arr = new PtM2Arr(arrayUnsafe)
  override def v0xMNum: Double = arrayUnsafe(0)
  override def v0yMNum: Double = arrayUnsafe(1)
  override def slate(operand: VecPtLen2): PolygonM2Gen = dblsMap(_ + operand.xMetresNum, _ + operand.yMetresNum)
  override def slate(xOperand: Length, yOperand: Length): PolygonM2Gen = dblsMap(_ + xOperand.metresNum, _ + yOperand.metresNum)
  override def slateX(xOperand: Length): PolygonM2Gen = dblsMap(_ + xOperand.metresNum, y => y)
  override def slateY(yOperand: Length): PolygonM2Gen = dblsMap(x => x, _ + yOperand.metresNum)
  override def scale(operand: Double): PolygonM2Gen = dblsMap(_ * operand, _ * operand)
  override def mapGeom2(operand: Length): Polygon = Polygon.fromArray(arrayUnsafe.map(_ / operand.metresNum))
  
  override def vertsForeach[U](f: PtM2 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtM2 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  @inline override def side(index: Int): LSegM2 = LSegM2(vert(index), vert(index + 1))
  override def sides: LineSegM2Arr = new LineSegM2Arr(arrayForSides)

  override def sidesForeach[U](f: LSegM2 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  override def revY: PolygonM2Gen = map(_.revY)
  override def revYIf(cond: Boolean): PolygonM2Gen = ife(cond, revY, this)
  override def rotate180: PolygonM2Gen = map(_.rotate180)
  override def rotate180If(cond: Boolean): PolygonM2Gen = ife(cond, map(_.rotate180), this)
  override def rotate180IfNot(cond: Boolean): PolygonM2Gen = ife(cond, this, map(_.rotate180))
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonM2Gen extends CompanionSlDbl2[PtM2, PolygonM2Gen]
{ override def fromArray(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)

  /** Implicit [[BuilderArrMap]] type class instance for [[PolygonM2Gen]]s. */
  implicit val arrBuildImplicit: BuilderArrMap[PolygonM2Gen, PolygonM2Arr] = new BuilderArrMap[PolygonM2Gen, PolygonM2Arr]
  { override type BuffT = PolygonM2Buff
    override def newBuff(length: Int): PolygonM2Buff = PolygonM2Buff(length)
    override def uninitialised(length: Int): PolygonM2Arr = new PolygonM2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonM2Arr, index: Int, newElem: PolygonM2Gen): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonM2Buff, newElem: PolygonM2Gen): Unit = buff.bufferUnsafe.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonM2Buff): PolygonM2Arr = new PolygonM2Arr(buff.bufferUnsafe.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonM2Gen]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtM2, PolygonM2Gen] = PersistSeqSpecBoth[PtM2, PolygonM2Gen]("PolygonM2")
}

/** Arr of [[PolygonM2Gen]]s. */
class PolygonM2Arr(val arrayOfArraysUnsafe:Array[Array[Double]]) extends ArrArrayDbl[PolygonM2Gen]
{ override type ThisT = PolygonM2Arr
  override def typeStr: String = "PolygonM2Arr"
  override def fElemStr: PolygonM2Gen => String = _.toString
  override def elemFromArray(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)
  override def fromArrayArray(array: Array[Array[Double]]): PolygonM2Arr = new PolygonM2Arr(array)
}

/** Buff of [[PolygonM2Gen]]s. Not to be confused with [[PtM2Buff]]. */
class PolygonM2Buff(val bufferUnsafe: ArrayBuffer[Array[Double]]) extends AnyVal, BuffArrayDbl[PolygonM2Gen]
{ override type ThisT = PolygonM2Buff
  override def typeStr: String = "PolygonM2Buff"
  override def fElemStr: PolygonM2Gen => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)
}

/** Companion object of the [[PolygonM2Buff]] class, a Buff of [[PolygonM2Gen]]s, contains factory apply method. */
object PolygonM2Buff
{ /** Factory apply method to create mepty [[PolygonM2Buff]]. */
  def apply(initLen: Int = 4): PolygonM2Buff = new PolygonM2Buff(new ArrayBuffer[Array[Double]](initLen))
}

class PolygonM2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtM2, PolygonM2Gen, A2]
{ override def a1: PolygonM2Gen = new PolygonM2Gen(a1ArrayDbl)
}

object PolygonM2Pair
{ /** Implicit [[BuilderArrMap]] instances / evidence for [[PolygonM2PairArr]]s. */
  given buildMapEv[A2](using ctA: ClassTag[A2]): BuilderArrMap[PolygonM2Pair[A2], PolygonM2PairArr[A2]] = new PolygonM2PairBuilder[A2]
}

/** Specialist [[ArrPair]] class for [[PolygonM2Gen]]s. */
final class PolygonM2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends PolygonLikeDbl2PairArr[PtM2, PolygonM2Gen, PolygonM2Arr,
  A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairArr[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonM2Pair[A2]): Unit = { a1ArrayDbls(index) = newElem.a1ArrayDbl; a2Array(index) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def elem(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonM2Arr = new PolygonM2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)
}

/** [[BuilderMapArrPair]] class for [[PolygonM2Gen]]s. */
final class PolygonM2PairBuilder[B2](using val b2ClassTag: ClassTag[B2], @unused notB: Not[SpecialT]#L[B2]) extends PolygonLikeDblNPairArrBuilder[PtM2,
  PolygonM2Gen, PolygonM2Arr, B2, PolygonM2Pair[B2], PolygonM2PairArr[B2]]
{ override type BuffT = PolygonM2PairBuff[B2]
  override type B1BuffT = PolygonM2Buff
  override def uninitialised(length: Int): PolygonM2PairArr[B2] = new PolygonM2PairArr[B2](new Array[Array[Double]](length), new Array[B2](length))

  override def indexSet(seqLike: PolygonM2PairArr[B2], index: Int, newElem: PolygonM2Pair[B2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonM2PairBuff[B2] = new PolygonM2PairBuff[B2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[B2](4))
  override def buffToSeqLike(buff: PolygonM2PairBuff[B2]): PolygonM2PairArr[B2] = new PolygonM2PairArr[B2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)
  override def b1Builder: PolygonLikeBuilderMap[PtM2, PolygonM2Gen] = PtM2.polygonBuildMapEv
  override def b1ArrBuilder: BuilderArrMap[PolygonM2Gen, PolygonM2Arr] = PolygonM2Gen.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonM2Arr, b2s: Array[B2]): PolygonM2PairArr[B2] = new PolygonM2PairArr[B2](b1Arr.arrayOfArraysUnsafe, b2s)
  override def newB1Buff(): PolygonM2Buff = PolygonM2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[B2]): PolygonM2PairArr[B2] = new PolygonM2PairArr[B2](arrayArrayDbl, a2Array)
}

/** [[BuffPair]] class for [[PolygonM2Gen]]s. */
class PolygonM2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends BuffPairSeqLikeDblN[PtM2, PolygonM2Gen, A2,
  PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairBuff[A2]
  override def setElemUnsafe(index: Int, newElem: PolygonM2Pair[A2]): Unit = { b1Buffer(index) = newElem.a1ArrayDbl; b2Buffer(index) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](b1Buffer(index), b2Buffer(index))
  override def elem(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](b1Buffer(index), b2Buffer(index))
}