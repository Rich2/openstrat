/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile area 1.463582932 million km².
 * Isle6 172942.905km² => 243930.488km². (Cuba 105806km²) + (Hispaniola 76479km²) + (Jamaica	11188km²) = 193473km². */
object Terr13W90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Isle10(tundra)),
      TRow(112, taiga),
      VRow(111, MouthLt(9730, HVDn, 7)),
      TRow(110, tempForest),
      TRow(108, temperate),
      VRow(107),
      TRow(106, savannah, temperate),
      VRow(105, Mouth(9728, HVUL, 6, 7), ThreeDown(9730, 8, 10, 7), BendIn(9732, HVDn, 10), BendIn(9734, HVDL, 10)),
      TRow(104, hillyJungle, hillyJungle),
      VRow(103, MouthRt(9724, HVDR, 7), Bend(9730, HVUR, 10, 4), Bend(9732, HVUp, 10, 4), ThreeUp(9734, 0, 10, 8)),
      TRow(102, sea, hillyJungle),
      VRow(101, MouthLt(9728, HVUL, 7), BendOut(9730, HVDL, 7)),
      TRow(100, sea, hillyJungle),
      VRow(99, BendIn(9728, HVDR, 13), BendOut(9730, HVUL, 6)),
      TRow(98, sea, mtainOld),
      VRow(97, BendIn(9728, HVUR, 13), MouthRt(9730, HVDR, 7)),
      VRow(91, BendIn(9728, HVDR, 13), MouthRt(9730, HVUR)),
      TRow(90, mtainOld),
      VRow(89, BendIn(9728, HVUR, 13), MouthRt(9730, HVDR, 7)),
      VRow(87, MouthOld(9728, HVDR, 3, wice)),
      TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "US North West")
    str(106, "US South Central", "US Deep South")
    str(104, "Yucatan", "Cuba")
  }
}