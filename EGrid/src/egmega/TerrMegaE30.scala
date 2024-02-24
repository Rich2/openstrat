/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object TerrMegaE30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e30(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(119, BendOut(1536, HVUp), MouthOld(1538, HVUR)),
      TRow(118, tundra),
      TRow(116, taiga),
      VRow(115, MouthOld(1534, HVUR)),
      TRow(114, level),
      TRow(112, hilly, level),
      VRow(111, BendIn(1532, HVDL, 13)),
      TRow(110, hilly, hilly),
      VRow(109, BendOut(1532, HVUR), Bend(1534, HVUp, 8, 3), BendIn(1536, HVDn, 13), MouthOld(1538, HVDR)),
      TRow(108, savannah, desert),
      VRow(107, MouthOld(1538, HVUL), BendAllOld(1540, HVDL)),
      TRow(106, desert * 3),
      VRow(105, BendAllOld(1540, HVUR), BendAllOld(1542, HVDL)),
      TRow(104, desert * 3),
      VRow(103, BendAllOld(1542, HVUR)),
      TRow(102, jungle * 2, hilly),
      TRow(100, jungle * 2, level),
      TRow(98, jungle * 2, sea),
      VRow(97, BendIn(1542, HVDR)),
      TRow(96, level * 2, hillySavannah),
      VRow(95, Bend(1540, HVDR, 7, 7), BendIn(1542, HVUL, 13)),
      TRow(94, sahel, hillySavannah, hillySavannah),
      VRow(93, BendOut(1538, HVDR, 7), ThreeUp(1540, 11, 0, 13)),
      TRow(92, hillySavannah, sea),
      VRow(91, MouthRt(1532, HVDR, 7), MouthLt(1536, HVDL, 7), BendIn(1538, HVUL, 13)),
      TRow(82, ice),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(110, "Greece", "Turkey")
    str(108, "Cairo")
    str(92, "Durban")
  }
}