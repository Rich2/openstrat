/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/* A polygon using distances measured in [[Length]] or metres rather than scalars. */
final class PolygonM2(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLikeDbl2[PtM2]
{ type ThisT = PolygonM2
  type SideT = LineSegM2
  override def typeStr: String = "PolygonM2"
  def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
  override def ssElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
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

  def revY: PolygonM2 = map(_.revY)
  def revYIf(cond: Boolean): PolygonM2 = ife(cond, revY, this)
  def rotate180: PolygonM2 = map(_.rotate180)
  def rotate180If(cond: Boolean): PolygonM2 = ife(cond, map(_.rotate180), this)
  def rotate180IfNot(cond: Boolean): PolygonM2 = ife(cond, this, map(_.rotate180))
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