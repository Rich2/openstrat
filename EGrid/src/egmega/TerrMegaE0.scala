/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° West to 15° East, centred on 0° East. Hex tile scale 1 Megametre or 1000km. The Mediterranean has not been given a hex
 * tile. Making 110, 514 an island causes problems. */
object TerrMegaE0 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e0(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(119, BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp)),
      TRow(118, hillyTundra),
      VRow(117, BendIn(510, HVDR), BendOut(512, HVUL, 7)),
      TRow(116, hillyTaiga),
      VRow(115, MouthRt(510, HVDn, 7)),
      TRow(114, land),
      TRow(112, land, hilly),
      VRow(111, MouthLt(508, HVUp, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendIn(516, HVDL, 13)),
      TRow(110, hilly, savannah),
      VRow(109, BendIn(508, HVUR, 10), BendIn(510, HVUp), BendMin(512, HVUL), BendOut(516, HVUR), BendOut(518, HVUp, 3)),
      TRow(108, hillySavannah, sahel),
      TRow(106, desert * 3),
      TRow(104, sahel, desert * 2),
      TRow(102, jungle * 3),
      VRow(101, BendIn(506, HVUp, 13), MouthMin(508, HVUR), MouthRt(512, HVUL), BendOut(514, HVDL, 7)),
      TRow(100, sea * 2, jungle),
      VRow(99, BendIn(514, HVUR, 13), BendOut(516, HVDL)),
      TRow(98, sea * 2, SepB(), jungle),
      VRow(97, BendOut(516, HVUL)),
      TRow(96, sea * 2, hillySavannah),
      TRow(94, sea * 2, hillyDesert),
      VRow(93, BendIn(514, HVDR, 7)),
      TRow(92, sea, hillySavannah),
      VRow(91, BendIn(514, HVUR, 13), MouthRt(516, HVDR, 7)),
      TRow(82, ice),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(116, "Scandanavia west")
    str(114, "Europe north west")
    str(112, "France", "Alps")
    str(110, "Iberia", "Algeria")
    str(92, "", "The Cape")
  }
}