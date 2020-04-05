/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._, collection.mutable.ArrayBuffer

trait EGrid80Km extends EGrid
{

}

object EGrid80Km
{
  val scale = 20.km * math.sqrt(3)
  val yFarNorthTileMinMax = (446, 540)
  val yNearNorthTileMinMax = (340, 444)

  /** The key method to get the longitude delta for x based from 0 degs longitude. */
  def roordToLatLong0(inp: Roord): LatLong =
  { val adj: Vec2 = HexGrid.roordToVec2(inp.subYC(300, 0))
    val d2: Dist2 = adj * scale
    val lat: Double = d2.y / EarthPolarRadius
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong(lat, longDelta)
  }

  def irrGrid(yMin: Int, yMax: Int): Array[Int] =
  {
    val buff: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    iToForeach(yMin, yMax, 2) { y =>
      //    val tileMax: Int = rowTileXEnd(y)
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

trait EGrid extends HexGridIrr
{
  def roodToLL(roord: Roord): LatLong
}
