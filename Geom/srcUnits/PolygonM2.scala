/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A polygon where the vertices are specified in [[Metres]] rather than scalars. */
final class PolygonM2(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLength2[PtM2]
{ type ThisT = PolygonM2
  type SideT = LineSegM2
  override def typeStr: String = "PolygonM2"
  def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
  override def ssElem(d1: Double, d2: Double): PtM2 = PtM2.metresNum(d1, d2)
  override def fElemStr: PtM2 => String = _.toString
  override def verts: PtM2Arr = new PtM2Arr(arrayUnsafe)

  /** Performs the side effecting function on the [[PtM2]] value of each vertex. */
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

  @inline override def side(index: Int): LineSegM2 = LineSegM2(vert(index), vert(index + 1))
  override def sides: LineSegM2Arr = new LineSegM2Arr(arrayForSides)

  override def sidesForeach[U](f: LineSegM2 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  override def revY: PolygonM2 = map(_.revY)
  override def revYIf(cond: Boolean): PolygonM2 = ife(cond, revY, this)
  override def rotate180: PolygonM2 = map(_.rotate180)
  override def rotate180If(cond: Boolean): PolygonM2 = ife(cond, map(_.rotate180), this)
  override def rotate180IfNot(cond: Boolean): PolygonM2 = ife(cond, this, map(_.rotate180))
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonM2 extends CompanionSeqLikeDbl2[PtM2, PolygonM2]
{ override def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonM2, PolygonM2Arr] = new BuilderArrMap[PolygonM2, PolygonM2Arr]
  { override type BuffT = PolygonM2Buff
    override def newBuff(length: Int): PolygonM2Buff = PolygonM2Buff(length)
    override def uninitialised(length: Int): PolygonM2Arr = new PolygonM2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonM2Arr, index: Int, newElem: PolygonM2): Unit = seqLike.unsafeArrayOfArrays(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonM2Buff, newElem: PolygonM2): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonM2Buff): PolygonM2Arr = new PolygonM2Arr(buff.unsafeBuffer.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonM2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtM2, PolygonM2] = PersistSeqSpecBoth[PtM2, PolygonM2]("PolygonM2")
}

/** Arr of [[PolygonM2]]s. */
class PolygonM2Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Arr
  override def typeStr: String = "PolygonM2Arr"
  override def fElemStr: PolygonM2 => String = _.toString
  override def apply(index: Int): PolygonM2 = new PolygonM2(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM2Arr = new PolygonM2Arr(array)
}

/** Buff of [[PolygonM2]]s. */
class PolygonM2Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Buff
  override def typeStr: String = "PolygonM2Buff"
  override def fElemStr: PolygonM2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

/** Companion object of the [[PolygonM2Buff]] class, a Buff of [[PolygonM2]]s, contains factory apply method. */
object PolygonM2Buff
{ def apply(initLen: Int = 4): PolygonM2Buff = new PolygonM2Buff(new ArrayBuffer[Array[Double]](initLen))
}

class PolygonM2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtM2, PolygonM2, A2]{
  override def a1: PolygonM2 = new PolygonM2(a1ArrayDbl)
}

object PolygonM2Pair
{ implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonM2Pair[A2], PolygonM2PairArr[A2]] = new PolygonM2PairBuilder[A2]
}

final class PolygonM2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM2Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonM2Arr = new PolygonM2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

final class PolygonM2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2], PolygonM2PairArr[A2]]
{ override type BuffT = PolygonM2PairBuff[A2]
  override type B1BuffT = PolygonM2Buff
  override def uninitialised(length: Int): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonM2PairArr[A2], index: Int, newElem: PolygonM2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonM2PairBuff[A2] = new PolygonM2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonM2PairBuff[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtM2, PolygonM2] = PtM2.polygonBuildImplicit
  override def b1ArrBuilder: BuilderArrMap[PolygonM2, PolygonM2Arr] = PolygonM2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonM2Arr, b2s: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonM2Buff = PolygonM2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonM2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtM2, PolygonM2, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM2Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](b1Buffer(index), b2Buffer(index))
}