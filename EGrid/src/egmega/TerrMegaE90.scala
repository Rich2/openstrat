/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1 megametre or 1000km.
 * Isle4 41440.668km² => 68503.962km². SriLanka 65610 km² */
object TerrMegaE90 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e90(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      TRow(114, taiga),
      TRow(112, hillyDeshot, deshot),
      TRow(110, deshot, hillyDeshot),
      TRow(108, hillyDescold, hillySteppe),
      TRow(106, jungle * 3),
      VRow(105, BendMin(3582, HVDR), Mouth(3584, HVUR, 2, 4), MouthRt(3592, HVUL, 7)),
      TRow(104, jungle, sea, jungle),
      VRow(103, MouthRt(3578, HVUR), BendIn(3580, HVDR, 12), ThreeUp(3582, 0, 12, 13), BendIn(3584, HVDL, 12), Bend(3592, HVDR, 13, 5)),
      TRow(102, hillyJungle, sea, hillyJungle),

      VRow(101, BendIn(3578, HVUp, 13), ThreeUp(3580, 12, 0, 8), BendIn(3582, HVUp, 12), BendIn(3584, HVUL, 12), OrigLt(3586, HVDn), OrigMin(3588, HVDR),
        ThreeDown(3590, 0, 6, 6), Bend(3592, HVUL, 13, 5)),

      TRow(100, sea * 2, jungle),
      VRow(99, BendIn(3586, HVUR, 13), BendOut(3588, HVDL, 7), BendIn(3590, HVUR), ThreeDown(3592, 6, 10, 8)),
      VRow(97, BendIn(3588, HVUR, 13), BendIn(3590, HVUp, 13), ThreeUp(3592, 10, 0, 13)),
      TRow(98, sea * 2, hillyJungle),
      VRow(95, BendIn(3592, HVDR, 12)),
      VRow(93, BendOut(3592, HVUR)),
      VRow(91, MouthRt(3590, HVDn, 7)),
      TRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "India south", "", "Burma")
    str(102, "Sri Lanka", "", "Thailand")
  }
}