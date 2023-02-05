/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

/** An earth grid covering a full 30 degree longitude range for the non polar regions. */
abstract class EGridLongFull(rBottomCen: Int, rTopCen: Int, longGridIndex: Int, cScale: Length, rOffset: Int) extends EGridLong(rBottomCen,
  longGridIndex, cScale, rOffset, EGridLongFull.getBounds(rBottomCen, rTopCen, rOffset, (longGridIndex %% 12) * 1024 + 512, cScale))
{
  override def hCoordLL(hc: HCoord): LatLong = hc match
  {
    case hc: HCen => hCoordMiddleLL(hc)
    case HVertHigh(r, _) if r == topSideR => hCoordMiddleLL(hc)
    case HVertLow(r, _) if r == bottomSideR => hCoordMiddleLL(hc)
    //case c if hc.r.div4Rem3 & c.div4Rem2 => hCoordMiddleLL(hc)

    case cx if hc.c == rowRightCoordC(hc.r, hc.c) =>
    { val rt = hCoordMiddleLL(hc)
      val lt = hCoordMiddleLL(HCoord(hc.r, rowLeftCoordC(hc.r, hc.c)))
      val rtLong = rt.longMilliSecs
      val ltLong = (lt.long + 30.east).milliSecs
      val longMilliSecs = rtLong aver ltLong
      LatLong.milliSecs(rt.latMilliSecs, longMilliSecs)
    }

    case cx if hc.c == rowLeftCoordC(hc.r, hc.c) =>
    { val lt = hCoordMiddleLL(hc)
      val rt = hCoordMiddleLL(HCoord(hc.r, rowRightCoordC(hc.r, hc.c)))
      val ltLong = lt.longMilliSecs
      val rtLong = (rt.long - 30.east).milliSecs
      val longMilliSecs = ltLong aver rtLong
      LatLong.milliSecs(lt.latMilliSecs, longMilliSecs)
    }

    case _ => hCoordMiddleLL(hc)
  }
}

/** Functions for Earth tile grids. */
object EGridLongFull
{
  /** Returns the min and max columns of a tile row in an EGrid80Km grid for a given y (latitude) with a given c offset. */
  def tileRowMinMaxC(r: Int, rOffset: Int, cOffset: Int, cScale: Length): (Int, Int) =
  {
    val startC: Int = ife(r %% 4 == 0, 0, 2)
    val hexDelta: Double = EGridLong.cDelta(r - rOffset, 4, cScale)
    val margin = 15 - hexDelta

    def loop(cAcc: Int): (Int, Int) =
    { val longDegsAcc: Double = EGridLong.cDelta(r - rOffset, cAcc, cScale)
      val overlapRatio = (longDegsAcc - margin) / hexDelta
      val res: (Int, Int) = longDegsAcc match {
        case lds if (lds < margin) => loop(cAcc + 4)
        case _ if overlapRatio < 0.5 => (-cAcc, cAcc)
        case _ => (4 - cAcc, cAcc)
      }
      res
    }
    val (neg, pos) = loop(startC)
    (neg + cOffset , pos + cOffset)
  }

  /** Copied from Old. This would seem to return the Array that has the irregular HexGrid row specifications. */
  def getBounds(rTileMin: Int, rTileMax: Int, rOffset: Int, c0Offset: Int, cScale: Length): Array[Int] =
  { val bounds: Array[Int] = new Array[Int]((rTileMax - rTileMin + 2).max0)
    iToForeach(rTileMin, rTileMax, 2){ r =>
      val p = (r - rTileMin)
      val pair = tileRowMinMaxC(r, rOffset, c0Offset, cScale)
      bounds(p) = ((pair._2 - pair._1 + 4)/ 4).max0
      bounds(p + 1) = pair._1
    }
    bounds
  }
}