/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import geom._, egrid._, pglobe._, prid.phex._

trait EGrid80Sys extends EGridSys
{ override val cScale: LengthMetric = 20.kiloMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid80LongFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid80Sys

class EGrid80LongPart(rBottomCen: Int, cenLongInt: Int, rArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 20.kiloMetres, 300, rArray)
{
  /** The latitude and longitude [[LatLong]] of an [[HCoord]] within the grid. */
  override def hCoordLL(hc: HCoord): LatLong = hCoordMiddleLL(hc)
}

object EGrid80LongFull {
  def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid80LongFull(rBottomCen, rTopCen, cenLongInt)
}

trait EGrid80LongMulti extends EGridLongMulti with EGrid80Sys

/** Terrain data grid for [[EGrid80LongFull]]s. */
trait Long80Terrs extends LongTerrs
{ override implicit val grid: EGrid80LongFull
}