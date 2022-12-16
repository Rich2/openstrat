/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Boolean data corresponding to the sides of a [[HGridSys]] hex grid system , stored using an underlying Array[Boolean]. Thhese classes should be
 *  created, initalised and used using an [HGrid]] class. For convenience the [[HGrid]] is passed as an implicit parameter. */
final class HSideBoolLayer(val unsafeArray: Array[Boolean]) extends AnyVal with BoolSeqSpec
{ override type ThisT = HSideBoolLayer
  override def typeStr: String = "HSideBoolDGrid"
  override def fromArray(array: Array[Boolean]): HSideBoolLayer = new HSideBoolLayer(array)

  def apply(hs: HSide)(implicit gridSys: HGridSys): Boolean = unsafeArray(gridSys.sideArrIndex(hs))

  /** Foreach true value applies the side effecting function to the corresponding [[HSide]]
   *  value.  */
  def truesHsForeach(f: HSide => Unit)(implicit gridSys: HGridSys): Unit =
  { var i = 0
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i) == true) f(hs)
      i += 1
    }
  }

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](f: HSide => B)(implicit gridSys: HGridSys, build: ArrMapBuilder[B, ArrB]): ArrB = truesHsMap(gridSys)(f)(build)

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](gridSys: HGridSys)(f: HSide => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Returns the [[HSide]]s that have a corresponding true value. */
  def trueHSides(implicit gridSys: HGridSys): HSideArr = truesHsMap(hs => hs)

  def projTruesMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: HSide => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    proj.gChild.sidesForeach { hs =>
      if (unsafeArray(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](f: LineSeg => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projTruesLineSegMap(proj)(f)(build)

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.sidesForeach { hs =>
      if (apply(hs)(proj.parent))
        proj.transOptLineSeg(hs.lineSegHC).foreach(ls => build.buffGrow(buff, f(ls)))
    }
    build.buffToSeqLike(buff)
  }

  def projFalsesScLineSegOptMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HSide, LineSeg) => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.sidesForeach { hs =>
      if (!apply(hs)(proj.parent))
        proj.transOptLineSeg(hs.lineSegHC).foreach{ls => f(hs, ls).foreach(build.buffGrow(buff, _) )}
    }
    build.buffToSeqLike(buff)
  }

  /*def projFalsesLineSegCensMap[A <: AnyRef, B, ArrB <: Arr[B]](layer: HCenLayer[A], proj: HSysProjection)(f: (LineSeg, A, A) => B)(
    implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  {
    var i = 0
    val buff = build.newBuff()
    proj.gChild.linksForeach { hs =>
      if (unsafeArray(i))
        proj.transOptLineSeg(hs.lineSegHC).foreach(ls =>
          val t1 = layer()
          build.buffGrow(buff, f(ls, )))
    }
    build.buffToSeqLike(buff)
  }*/

  def set(hs: HSide, value: Boolean)(implicit grid: HGridSys): Unit = {
    val i = grid.sideArrIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(r: Int, c: Int, value: Boolean)(implicit grid: HGridSys): Unit = { unsafeArray(grid.sideArrIndex(r, c)) = value }
  def setTrues(hSides: HSideArr)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideArrIndex(r)) = true)
  def setTrues(hSides: HSide*)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideArrIndex(r)) = true)
  def setTruesInts(hSides: (Int, Int)*)(implicit grid: HGridSys): Unit = hSides.foreach(p => unsafeArray(grid.sideArrIndex(p._1, p._2)) = true)
}

object HSideBoolLayer
{ def uniinitialised(length: Int) = new HSideBoolLayer(new Array[Boolean](length))
}