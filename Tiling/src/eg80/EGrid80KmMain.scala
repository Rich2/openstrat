/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom.pglobe._

/** A main non-polar grid with a hex pan of 80Km */
class EGrid80KmMain(bottomTileRow: Int, numTileRows: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(bottomTileRow, numTileRows, cenLong, 20000.metres, 300, cOffset)

object EGrid80Km0
{
  def apply(): EGrid80KmMain = new EGrid80KmMain(446, ((540 - 446 + 2) / 2).max0, 0.east, 512)
}