/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/* A polygon using distances measured in [[Length]] or metres rather than scalars. */
final class PolygonM2(val unsafeArray: Array[Double]) extends AnyVal with PolygonLikeDbl2[PtM2]
{ type ThisT = PolygonM2
  type SideT = LineSegM2
  def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
  override def typeStr: String = "PolygonMs"
  override def ssElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def fElemStr: PtM2 => String = _.str

  /** Performs the side effecting function on the [[PtM2]] value of each vertex. */
  override def vertsForeach[U](f: PtM2 => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, PtM2) => U): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtM2 => B)(implicit builder: MapBuilderArr[B, ArrB]): ArrB =
  { val res = builder.uninitialised(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, PtM2) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  override def sidesForeach[U](f: LineSegM2 => U): Unit = ???

  def revY: PolygonM2 = map(_.revY)
  def revYIf(cond: Boolean): PolygonM2 = ife(cond, revY, this)
  def rotate180: PolygonM2 = map(_.rotate180)
  def rotate180If(cond: Boolean): PolygonM2 = ife(cond, map(_.rotate180), this)
  def rotate180IfNot(cond: Boolean): PolygonM2 = ife(cond, this, map(_.rotate180))
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonM2 extends CompanionSeqLikeDbl2[PtM2, PolygonM2]
{ override def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)

  /** [[Show]] type class instance / evidence for [[PolygonM2]]. */
  implicit val showEv: ShowSeqSpec[PtM2, PolygonM2] = ShowSeqSpec[PtM2, PolygonM2]("PolygonM2")

  /** [[Unshow]] type class instance / evidence for [[PolygonM2]]. */
  implicit val unshowEv: UnshowSeqSpecDblN[PtM2, PolygonM2] = UnshowSeqSpecDblN[PtM2, PolygonM2]("PolygonM2", fromArray)

  implicit val arrBuildImplicit: MapBuilderArr[PolygonM2, PolygonM2Arr] = new MapBuilderArr[PolygonM2, PolygonM2Arr]
  { override type BuffT = PolygonM2Buff
    override def newBuff(length: Int): PolygonM2Buff = PolygonM2Buff(length)
    override def uninitialised(length: Int): PolygonM2Arr = new PolygonM2Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonM2Arr, index: Int, elem: PolygonM2): Unit = seqLike.unsafeArrayOfArrays(index) = elem.unsafeArray
    override def buffGrow(buff: PolygonM2Buff, newElem: PolygonM2): Unit = buff.unsafeBuffer.append(newElem.unsafeArray)
    override def buffToSeqLike(buff: PolygonM2Buff): PolygonM2Arr = new PolygonM2Arr(buff.unsafeBuffer.toArray)
  }
}

/** Arr of [[PolygonM2]]s. */
class PolygonM2Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Arr
  override def typeStr: String = "PolygonMArr"
  override def fElemStr: PolygonM2 => String = _.toString
  override def apply(index: Int): PolygonM2 = new PolygonM2(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM2Arr = new PolygonM2Arr(array)
}

/** Buff of [[PolygonM2]]s. */
class PolygonM2Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Buff
  override def typeStr: String = "PolygonMBuff"
  override def fElemStr: PolygonM2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

/** Companion object of the [[PolygonM2Buff]] class, a Buff of [[PolygonM2]]s, contains factory apply method. */
object PolygonM2Buff
{ def apply(initLen: Int = 4): PolygonM2Buff = new PolygonM2Buff(new ArrayBuffer[Array[Double]](initLen))
}