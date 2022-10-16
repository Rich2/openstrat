/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._, pglobe._, pEarth._, reflect.ClassTag

/** 80km hexs. deltaX in HexCood 1 = 20km */   
class EGrid80KmAncient[TileT <: TileAncient, SideT <: TileSideAncient](bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int,
  xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int)(implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends
  EGridAncient[TileT, SideT](bounds, name, cenLong, EGrid80KmAncient.scale, xOffset, EGrid80KmAncient.yOffset, xTileMin, xTileMax, yTileMin, yTileMax,
  turnNum)
{
   foreachTileRowAll{y =>
      val pair = EGrid80KmAncient.tileRowMaxX(y, xOffset)
      setRowStart(y, pair._1)
      setRowEnd(y, pair._2)
   }
   (yTileMin to yTileMax by 2).foreach{ y =>
      val tileMax: Int = rowTileXEnd(y)
      val tileMin: Int = rowTileXStart(y)
      val xSideMax = tileMax + 2
      val xSideMin = tileMin - 2

      val rt1: Double = coodToLL(xSideMax, y + 1).longRadians
      val lt1: Double = coodToLL(xSideMin, y + 1).longRadians
      val rt1Adj: Double = lt1 + 30.degsToRadians
      val rt1New = (rt1 + rt1Adj) / 2
      val lt1Adj = rt1 - 30.degsToRadians
      val lt1New = (lt1 + lt1Adj) / 2
      setLongitude(xSideMax, y + 1, rt1New)
      setLongitude(xSideMin, y + 1, lt1New)

      val rts: Double = coodToLL(xSideMax, y).longRadians
      val lts: Double = coodToLL(xSideMin, y).longRadians
      val rtsAdj: Double = lts + 30.degsToRadians
      val rtsNew = (rts + rtsAdj) / 2
      val ltsAdj = rts - 30.degsToRadians
      val ltsNew = (lts + ltsAdj) / 2
      setLongitude(xSideMax, y, rtsNew)
      setLongitude(xSideMin, y, ltsNew)

      val rt2: Double = coodToLL(xSideMax, y - 1).longRadians
      val lt2: Double = coodToLL(xSideMin, y - 1).longRadians
      val rt2Adj: Double = lt2 + 30.degsToRadians
      val rt2New = (rt2 + rt2Adj) / 2
      val lt2Adj = rt2 - 30.degsToRadians
      val lt2New = (lt2 + lt2Adj) / 2
      setLongitude(xSideMax, y - 1, rt2New)
      setLongitude(xSideMin, y - 1, lt2New)
   }

   override def optTile(x: Int, y: Int): Option[TileT] = ife5(
     y < yTileMin, None,
     y > yTileMax, None,
     x < rowTileXStart(y), None,

     x == rowTileXEnd(y) + 4,
     rightGrid.map { grid =>
        val rs: Int = grid.rowTileXStart(y)
        grid.getTile(rs, y)
     },

     x > rowTileXEnd(y), None,
     Some(getTile(x, y))
   )
}

object EGrid80KmAncient
{ val scale: Length = 20.km * math.sqrt(3)
  val yOffset = 300

  def coodToLatLong0Off200(inp: Cood): LatLong = coodToLatLong0(inp.subX(200))

  /** The key method to get the longitude delta for x based from 0 degs longitude. */
  def coodToLatLong0(inp: Cood): LatLong =
  { val adj: Pt2 = HexGridAncient.coodToVec2(inp.subXY(0, 300))
     val d2: PtM2 = adj.toMetres(scale)
     val lat: Double = d2.y / EarthPolarRadius
     val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
     LatLong.radians(lat, longDelta)
  }

  /** Returns the longitudinal delta for a given x at a given y (latitude) for an EGrid80Km Hex Grid. */
  def yToLatDegs(y: Int): Double = ((y - yOffset) * scale / EarthPolarRadius).radiansToDegs

  /** Returns the longitudinal delta for a given x. */
  def xDelta(y: Int, x: Int): Double = coodToLatLong0(Cood(x, y)).longDegs

  /** Returns the min and max x of a tile row in an EGrid80Km grid for a given y (latitude) with a given x offset. */
  def tileRowMaxX(y: Int, xOffset: Int = 0): (Int, Int) =
  {
    val startX: Int = ife(y %% 4 == 0, 0, 2)
    val hexDelta: Double = xDelta(y, 4)
    val margin = 15 - hexDelta

    def loop(xAcc: Int): (Int, Int) =
    {
      val newPt = xDelta(y, xAcc)
      val overlapRatio = (newPt - margin) / hexDelta
      newPt match
      { case r if r < margin => loop(xAcc + 4)
        //case r if overlapRatio < 0.2 => (-xAcc, xAcc + 4)
        case r if overlapRatio < 0.5 => (-xAcc, xAcc)
        case r => (4 - xAcc, xAcc)
      }
    }
    val (neg, pos) = loop(startX)
    (neg + xOffset , pos + xOffset)
  }

  /** This would seem to return the Arrray that has the irregular HexGrid row specifications. */
  def getBounds(xOffset: Int, yTileMin: Int, yTileMax: Int): Array[Int] =
  {
      val bounds = new Array[Int]((yTileMax - yTileMin + 1) * 2)
      (yTileMin to yTileMax by 2).foreach{ y =>
         val p = (y - 446) * 2
         val pair = EGrid80KmAncient.tileRowMaxX(y, xOffset)
         bounds(p) = pair._1
         bounds(p + 1) = pair._2
      }
      bounds
   }
}

object E80Empty extends EGridMaker
{
  def apply[TileT <: TileAncient, SideT <: TileSideAncient](implicit fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT,
                                                            evTile: ClassTag[TileT], evSide: ClassTag[SideT]):
  EGrid80KmAncient[TileT, SideT] =
      new EGrid80KmAncient[TileT, SideT](new Array[Int](0), "Empty", 0.east, xOffset = 0, xTileMin = 4, xTileMax = 0, yTileMin = 4, yTileMax = 0, turnNum = 0)

  //def rowDelta(y: Int): Double = ???
}

class EGFarNorth[TileT <: TileAncient, SideT <: TileSideAncient](name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
                                                                (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends
  EGrid80KmAncient[TileT, SideT](EGFarNorth.getBounds(xOffset), name, cenLong, xOffset: Int, xTileMin: Int, xTileMax: Int,
    yTileMin = 446, yTileMax = 540, turnNum = 0)
{
}

object EGFarNorth
{ def getBounds(xOffset: Int): Array[Int] = EGrid80KmAncient.getBounds(xOffset, 446, 540)
}

class EGNorth[TileT <: TileAncient, SideT <: TileSideAncient]
  (bounds: Array[Int], name: String, cenLong: Longitude, xOffset: Int, xTileMin: Int, xTileMax: Int)
  (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends
  EGrid80KmAncient[TileT, SideT](bounds, name, cenLong, xOffset: Int, xTileMin: Int, xTileMax: Int, yTileMin = 340, yTileMax = 444, turnNum = 0)

object EGNearNorth
{
  def getBounds(xOffset: Int): Array[Int] = EGrid80KmAncient.getBounds(xOffset, 300, 444)
}