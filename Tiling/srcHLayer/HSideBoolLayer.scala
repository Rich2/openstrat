/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*

/** Boolean data corresponding to the sides of a [[HGridSys]] hex grid system , stored using an underlying Array[Boolean]. These classes should be created,
 * initialised and using an [[HGrid]] class. For convenience the [[HGrid]] is passed as an implicit parameter. */
final class HSideBoolLayer(val arrayUnsafe: Array[Boolean]) extends AnyVal, BoolSeqSpec
{ override type ThisT = HSideBoolLayer
  override def typeStr: String = "HSideBoolDGrid"
  override def fromArray(array: Array[Boolean]): HSideBoolLayer = new HSideBoolLayer(array)

  def apply(hs: HSep)(using gridSys: HGridSys): Boolean = arrayUnsafe(gridSys.sepLayerArrayIndex(hs))
  def apply(gridSys: HGridSys, hs: HSep): Boolean = arrayUnsafe(gridSys.sepLayerArrayIndex(hs))

  override def setElemUnsafe(index: Int, newElem: Boolean): Unit = ???

  /** Foreach true value applies the side effecting function to the corresponding [[HSep]] value. */
  def truesHsForeach(f: HSep => Unit)(using gridSys: HGridSys): Unit =
  { var i = 0
    gridSys.sepsForeach{hs =>
      if (arrayUnsafe(i) == true) f(hs)
      i += 1
    }
  }

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](f: HSep => B)(using gridSys: HGridSys, build: BuilderArrMap[B, ArrB]): ArrB = truesHsMap(gridSys)(f)

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](gridSys: HGridSys)(f: HSep => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    gridSys.sepsForeach{hs =>
      if (arrayUnsafe(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Returns the [[HSep]]s that have a corresponding true value. */
  def trueHSides(using gridSys: HGridSys): HSepArr = truesHsMap(hs => hs)

  def projTruesMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: HSep => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    proj.gChild.sepsForeach { hs =>
      if (arrayUnsafe(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](f: LSeg2 => B)(using proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB = projTruesLineSegMap(proj)(f)

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LSeg2 => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
    proj.gChild.sepsOptMap { hs => if (apply(proj.parent, hs)) proj.transOptLineSeg(hs.lineSegHC).map(f) else None }

  /** Maps across all the link trues in this Side Layer that exist in the projection. */
  def projLinkTruesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LSeg2 => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
    projLinkTruesLineSegMap(f)(using proj, build)

  /** Maps across all the link trues in this Side Layer that exist in the projection. */
  def projLinkTruesLineSegMap[B, ArrB <: Arr[B]](f: LSeg2 => B)(using proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    proj.gChild.linksOptMap { hs => if (apply(proj.parent, hs)) proj.transOptLineSeg(hs.lineSegHC).map(f) else None }

  /** Maps across all the falses in this Side Layer that exist in the projection. */
  def projFalsesLineSegMap[B, ArrB <: Arr[B]](f: LSeg2 => B)(using proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projFalsesLineSegMap(proj)(f)

  /** Maps across all the falses in this Side Layer that exist in the projection. */
  def projFalsesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LSeg2 => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
    proj.gChild.sepsOptMap { hs =>
      if (!apply(proj.parent, hs)) proj.transOptLineSeg(hs.lineSegHC).map(f)
      else None
    }

  def projFalsesHsLineSegOptMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HSep, LSeg2) => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.sepsForeach { hs =>
      if (!apply(proj.parent, hs))
        proj.transOptLineSeg(hs.lineSegHC).foreach{ls => f(hs, ls).foreach(build.buffGrow(buff, _) )}
    }
    build.buffToSeqLike(buff)
  }

  /** Projection OptMaps over the [[HSep]] and [[LSeg2]] of eahc false [[HSep]] link. */
  def projFalseLinksHsLineSegOptMap[B, ArrB <: Arr[B]](f: (HSep, LSeg2) => Option[B])(using proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projFalseLinksHsLineSegOptMap(proj)(f)

  /** Projection OptMaps over the [[HSep]] and [[LSeg2]] of eahc false [[HSep]] link. */
  def projFalseLinksHsLineSegOptMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HSep, LSeg2) => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.linksForeach { hs =>
      if (!apply(proj.parent, hs))
        proj.transOptLineSeg(hs.lineSegHC).foreach { ls => f(hs, ls).foreach(build.buffGrow(buff, _)) }
    }
    build.buffToSeqLike(buff)
  }

  def set(hs: HSep, value: Boolean)(using gSys: HGridSys): Unit = set(gSys, hs, value)
  
  def set(gSys: HGridSys, hs: HSep, value: Boolean): Unit =
  { val i = gSys.sepLayerArrayIndex(hs)
    if (i >= arrayUnsafe.length) deb(s"$hs")
    arrayUnsafe(i) = value
  }

  def set(r: Int, c: Int, value: Boolean)(using grid: HGridSys): Unit = { arrayUnsafe(grid.sepLayerArrayIndex(r, c)) = value }
  def setTrues(hSides: HSepArr)(using grid: HGridSys): Unit = hSides.foreach(r => arrayUnsafe(grid.sepLayerArrayIndex(r)) = true)
  def setTrues(hSides: HSep*)(using grid: HGridSys): Unit = hSides.foreach(r => arrayUnsafe(grid.sepLayerArrayIndex(r)) = true)
  def setTruesPairs(hSidePairs: (Int, Int)*)(using grid: HGridSys): Unit = hSidePairs.foreach(p => arrayUnsafe(grid.sepLayerArrayIndex(p._1, p._2)) = true)

  def setTruesInts(hSideInts: Int*)(using grid: HGridSys): Unit =
  { val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2){ i =>
      val index = grid.sepLayerArrayIndex(hSideInts(i), hSideInts(i + 1))
      arrayUnsafe(index) = true
    }
  }

  /** Spawns a new [[HSideBoolLayer]] data layer for the child [[HGridSys]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys): HSideBoolLayer =
  { val array: Array[Boolean] = new Array[Boolean](childGridSys.numSeps)
    childGridSys.sepsForeach { hs => array(childGridSys.sepLayerArrayIndex(hs)) = apply(parentGridSys, hs) }
    new HSideBoolLayer(array)
  }
}

object HSideBoolLayer
{ /** Factory apply method for [[HSideBoolean]] takes an [[HGridSys]] as an implicit parameter. */
  def apply()(using gridSys: HGridSys): HSideBoolLayer = new HSideBoolLayer(new Array[Boolean](gridSys.numSeps))
}