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
      TRow(114, oceanic),
      TRow(112, hillyOce, oceanic),
      VRow(111, BendIn(1532, HVDL, 13)),
      TRow(110, hillyOce, hillyOce),
      VRow(109, BendOut(1532, HVUR), Bend(1534, HVUp, 8, 3), BendIn(1536, HVDn, 13), MouthOld(1538, HVDR)),
      TRow(108, savannah, desert),
      VRow(107, Mouth(1538, HVUL, 1, 5), BendIn(1540, HVDL, 13)),
      TRow(106, desert * 3),
      VRow(105, BendOut(1540, HVUR, 7), BendIn(1542, HVDL, 13)),
      TRow(104, desert * 3),
      VRow(103, BendOut(1542, HVUR), BendIn(1544, HVUp, 13)),
      TRow(102, jungle * 2, hillyOce),
      TRow(100, jungle * 2, savannah),
      TRow(98, jungle * 2, sea),
      VRow(97, BendInLt(1542, HVDR, 6, 7)),
      TRow(96, savannah, hillySavannah * 2),
      VRow(95, Bend(1540, HVDR, 13, 7), BendIn(1542, HVUL, 13)),
      TRow(94, sahel, hillySavannah, hillySavannah),
      VRow(93, BendOut(1538, HVDR, 7), ThreeUp(1540, 13, 0, 13), BendIn(1542, HVUp, 10), BendIn(1544, HVUL, 7)),
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