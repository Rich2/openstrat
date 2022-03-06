/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pGrid._, collection.mutable.ArrayBuffer

trait EGrid80KmOld extends EGridOld

/** Under new numbering system Greenwich longitude will be row 512 0G0 in Base32, Longitude 30 East passing through St Petersburg Russia will be 1536
 * Base32 1G0. Longitude 90 West passing through Memphis and New Orleans will be 9728 Base32 9G0. 30 West passing through Greenland will be 11776
 * Base32 BG0. */
object EGrid80KmOld
{
  val scale = 20.km * math.sqrt(3)
  val yFarNorthTileMinMax = (446, 540)
  val yNearNorthTileMinMax = (340, 444)

  /** The key method to get the longitude delta for x based from 0 degs longitude. */
  def roordToLatLong0(inp: Roord): LatLong =
  { val adj: Pt2 = HexGridOld.roordToVec2(inp.subYC(300, 0))
    val d2: PtM2 = adj.toMetres(scale)
    val lat: Double = d2.y / EarthPolarRadius
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(y: Int, c: Int): Double = roordToLatLong0(Roord(y, c)).longDegs

  /** Returns the min and max columns of a tile row in an EGrid80Km grid for a given y (latitude) with a given c offset. */
  def tileRowMaxC(y: Int, cOffset: Int): (Int, Int) =
  {
    val startC: Int = ife(y %% 4 == 0, 0, 2)
    val hexDelta: Double = cDelta(y, 4)
    val margin = 15 - hexDelta

    def loop(cAcc: Int): (Int, Int) =
    {
      val newPt = cDelta(y, cAcc)
      val overlapRatio = (newPt - margin) / hexDelta
      newPt match
      { case r if r < margin => loop(cAcc + 4)
      //case r if overlapRatio < 0.2 => (-xAcc, xAcc + 4)
      case r if overlapRatio < 0.5 => (-cAcc, cAcc)
      case r => (4 - cAcc, cAcc)
      }
    }
    val (neg, pos) = loop(startC)
    (neg + cOffset , pos + cOffset)
  }

  /** This would seem to return the Array that has the irregular HexGrid row specifications. */
  def getBounds(xOffset: Int, yTileMin: Int, yTileMax: Int): Array[Int] =
  { val bounds: Array[Int] = new Array[Int]((yTileMax - yTileMin + 2).max0)
    (yTileMin to yTileMax by 2).foreach{ y =>
      val p = (y - yTileMin)// * 2
      val pair = EGrid80KmOld.tileRowMaxC(y, xOffset)
      bounds(p) = pair._1
      bounds(p + 1) = pair._2
    }
    bounds
  }

  def irrGrid(yMin: Int, yMax: Int): Array[Int] =
  {
    val buff: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    iToForeach(yMin, yMax, 2) { y =>
      //val tileMax: Int = rowTileXEnd(y)
      //    val tileMin: Int = rowTileXStart(y)
      //    val xSideMax = tileMax + 2
      //    val xSideMin = tileMin - 2
      //
      //    val rt1: Double = coodToLL(xSideMax, y + 1).long
      //    val lt1: Double = coodToLL(xSideMin, y + 1).long
      //    val rt1Adj: Double = lt1 + 30.degreesToRadians
      //    val rt1New = (rt1 + rt1Adj) / 2
      //    val lt1Adj = rt1 - 30.degreesToRadians
      //    val lt1New = (lt1 + lt1Adj) / 2
      //    setLongitude(xSideMax, y + 1, rt1New)
      //    setLongitude(xSideMin, y + 1, lt1New)
      //
      //    val rts: Double = coodToLL(xSideMax, y).long
      //    val lts: Double = coodToLL(xSideMin, y).long
      //    val rtsAdj: Double = lts + 30.degreesToRadians
      //    val rtsNew = (rts + rtsAdj) / 2
      //    val ltsAdj = rts - 30.degreesToRadians
      //    val ltsNew = (lts + ltsAdj) / 2
      //    setLongitude(xSideMax, y, rtsNew)
      //    setLongitude(xSideMin, y, ltsNew)
      //
      //    val rt2: Double = coodToLL(xSideMax, y - 1).long
      //    val lt2: Double = coodToLL(xSideMin, y - 1).long
      //    val rt2Adj: Double = lt2 + 30.degreesToRadians
      //    val rt2New = (rt2 + rt2Adj) / 2
      //    val lt2Adj = rt2 - 30.degreesToRadians
      //    val lt2New = (lt2 + lt2Adj) / 2
      //    setLongitude(xSideMax, y - 1, rt2New)
      //    setLongitude(xSideMin, y - 1, lt2New)
      //  }
      //
      //  override def optTile(x: Int, y: Int): Option[TileT] = ife5(
      //    y < yTileMin, None,
      //    y > yTileMax, None,
      //    x < rowTileXStart(y), None,
      //
      //    x == rowTileXEnd(y) + 4,
      //    rightGrid.map { grid =>
      //      val rs: Int = grid.rowTileXStart(y)
      //      grid.getTile(rs, y)
      //    },
      //
      //    x > rowTileXEnd(y), None,
      //    Some(getTile(x, y))
      //  )
    }
    buff.toArray
  }
}