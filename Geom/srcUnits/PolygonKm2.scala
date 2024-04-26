/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A polygon using verticies specified in [[Kilometres]] rather than scalars. */
final class PolygonKm2(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLength2[PtKm2]
{ type ThisT = PolygonKm2
  type SideT = LineSegKm2
  override def typeStr: String = "PolygonKm2"
  def fromArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
  override def ssElem(d1: Double, d2: Double): PtKm2 = PtKm2.kilometresNum(d1, d2)
  override def fElemStr: PtKm2 => String = _.toString
  override def verts: PtKm2Arr = new PtKm2Arr(arrayUnsafe)

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
    override def indexSet(seqLike: PolygonKm2Arr, index: Int, newElem: PolygonKm2): Unit = seqLike.unsafeArrayOfArrays(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonKm2Buff, newElem: PolygonKm2): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonKm2Buff): PolygonKm2Arr = new PolygonKm2Arr(buff.unsafeBuffer.toArray)
  }

  /** Both [[Show]] amd [[Unshow]] type class instances / evidence for [[PolygonKm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtKm2, PolygonKm2] = PersistSeqSpecBoth[PtKm2, PolygonKm2]("PolygonKm2")
}

/** Arr of [[PolygonKm2]]s. */
class PolygonKm2Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonKm2]
{ override type ThisT = PolygonKm2Arr
  override def typeStr: String = "PolygonKm2Arr"
  override def fElemStr: PolygonKm2 => String = _.toString
  override def apply(index: Int): PolygonKm2 = new PolygonKm2(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonKm2Arr = new PolygonKm2Arr(array)
}

/** Buff of [[PolygonKm2]]s. */
class PolygonKm2Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonKm2]
{ override type ThisT = PolygonKm2Buff
  override def typeStr: String = "PolygonKm2Buff"
  override def fElemStr: PolygonKm2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
}

/** Companion object of the [[PolygonKm2Buff]] class, a Buff of [[PolygonKm2]]s, contains factory apply method. */
object PolygonKm2Buff
{ def apply(initLen: Int = 4): PolygonKm2Buff = new PolygonKm2Buff(new ArrayBuffer[Array[Double]](initLen))
}