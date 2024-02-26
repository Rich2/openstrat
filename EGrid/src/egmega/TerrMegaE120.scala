/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1 megametre or 1000km.
 * Isle8 274015.850km² <= 165762.674km². Most of Philippines excluding Luzon and Palawan.
 * Isle6 165762.674km² <= 102333.079km² Luzon. */
object TerrMegaE120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e120(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      VRow(115, MouthOld(4612, HVUp)),
      TRow(114, taiga),
      TRow(112, taiga, hillyTaiga),
      TRow(110, savannah, hillyTaiga),
      VRow(109, MouthOld(4608, HVUL), BendIn(4612, HVUL), BendOut(4614, HVDR)),
      TRow(108, CapeOld(1, 2), CapeOld(2, 4, hillyOce)),
      TRow(106, CapeOld(2, 2, hillyJungle), sea * 2),
      VRow(105, MouthOld(4604, HVUL)),
      TRow(104, CapeOld(1, 2, jungle), Isle6(hillyJungle)),

      TRow(102, CapeOld(5, 4, hillyJungle), Isle8(hillyJungle), sea),
      VRow(101, MouthOld(4614, HVDL)),
      TRow(100, hillyJungle, Isle10(hillyJungle), hillyJungle),
      TRow(98, Isle6(hillyJungle), sea, jungle),
      VRow(97, Bend(4616, HVDn, 6, 6)),
      TRow(96, sea, sea, savannah),
      VRow(95, MouthOld(4606, HVUR)),
      TRow(94, CapeOld(5, 1, desert), desert, sahel),
      VRow(93, BendIn(4604, HVUR), BendOut(4606, HVDL)),
      TRow(92, CapeOld(3, 2, savannah), desert),
      TRow(82, ice)
    )
  }
  help.run
}