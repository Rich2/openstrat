/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 640km. A hex tile area of 354724.005km².
 *  A minimum island area of 59120.667km², which
 *  [[Isle5]] 28059.223km² => 41915.629km². (Sardinia 24090km²) + (Corsica.8722km²) = 32812. */
object Terr640E0 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e0(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(131, MouthRt(512, HVUp)),
      VRow(129, BendOut(510, HVDR), BendIn(512, HVUL, 13)),
      VRow(127, SetSep(509), MouthLt(512, HVUp, 7)),
      TRow(126, sea, hillyTaiga),
      VRow(125, SourceRt(508, HVDL), BendIn(510, HVDR, 13), ThreeUp(512, 13, 13, 0), BendIn(514, HVDL, 13), MouthRt(518, HVUp)),
      TRow(124, mtainOld, taiga),
      VRow(123, BendIn(508, HVDR, 10), BendOut(510, HVUL), BendIn(512, HVDR, 13), ThreeUp(514, 0, 6, 13), BendOut(516, HVUp), Bend(518, HVUL, 2, 4)),
      TRow(122, oceanic * 2),
      VRow(121, MouthRt(508, HVDn), MouthRt(510, HVDL, 7), BendOut(512, HVUL, 7)),
      TRow(120, sea, oceanic * 2),
      VRow(119, MouthLt(508, HVUp, 7)),
      TRow(118, hillyOce, oceanic, hillyOce),
      VRow(117, BendIn(506, HVDR, 13), BendOut(508, HVUL), Bend(514, HVDR, 11, 3), Bend(516, HVDn, 11, 2), BendIn(518, HVDL, 11)),
      TRow(116, hillyOce, hillyOce, mtainSubForest),

      VRow(115, MouthRt(506, HVDn, 7), MouthRt(508, HVDL), BendIn(510, HVDn, 10), BendMin(512, HVUp), ThreeUp(514, 11, 3, 10), BendIn(516, HVUp, 11),
        ThreeUp(518, 0, 6, 11), BendIn(520, HVDL, 13)),

      TRow(114, sea, hillyOce, hillyOce, hillyOce),
      VRow(113, MouthLt(520, HVDn, 6)),
      TRow(112, hillyDeshot, deshot * 3),
      TRow(110, deshot * 2, hillyDeshot, deshot),
      TRow(108, deshot * 4),
      TRow(106, sahel * 5),
      TRow(104, Land(Hilly, Savannah, Forest), Land(Plain, Savannah, Forest) * 3, Land(Hilly, Savannah, Forest)),
      TRow(102, jungle, jungle * 2, jungle, jungle),
      VRow(101, MouthLt(504, HVUL, 7), BendIn(506, HVUp, 11), BendOut(508, HVDn), BendIn(510, HVUp, 13), BendOut(512, HVDn, 7), BendIn(514, HVUp, 13),
        BendOut(516, HVDn), MouthRt(518, HVDR)),
      TRow(100, sea * 4, hillyJungle),
      TRow(98, sea * 4, hillyJungle),
      TRow(96, sea * 4, hillySavannah)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(124, "Scotland", "Scandanavia south")
    str(122, "British Isles", "Denmark")
    str(116, "Portugal", "Spain east", "Sardinia-Corsica")
  }
}