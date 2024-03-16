/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 640km.
 * [[Isle8]] 77942.286km² => 100112.536km². (Hispaniola 76192km²) + (Puerto Rico 8897km²) = 85089km². */
object Terr640W60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w60(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
    TRow(130, tundra),
    TRow(128, tundra),
    TRow(126, tundra, hillyTundra),
    VRow(125, BendOut(10758, HVDR)),
    TRow(124, Mountains(Tundra), mtainOld),
    VRow(123, MouthOld(10748, HVDR), ThreeDown(10756, 13, 0, 13), BendIn(10758, HVUL, 9)),
    TRow(122, taiga, hillyLakesTaiga),
    VRow(121, Bend(10754, HVDR, 4, 2), ThreeUp(10756, 0, 13, 13), BendIn(10758, HVDL, 13)),
    TRow(120, taiga * 2, hillyLakesTaiga),
    VRow(119, MouthLt(10750, HVUL, 7), BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13), MouthLt(10756, HVDL), BendIn(10758, HVUL, 13)),
    TRow(118, taiga, sea),
    VRow(117, BendOut(10750, HVDR, 7), MouthRt(10752, HVUR, 7)),
    TRow(116, hillyCont),
    VRow(115, MouthLt(10746, HVUL), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
    VRow(109, BendIn(10746, HVDL, 6), ThreeUp(10744, 0, 13, 13)),
    TRow(108, Isle8(hillyJungle)),
    VRow(107, BendIn(10746, HVUL, 7)),
    VRow(105, BendOut(10742, HVUp, 7), BendIn(10744, HVDn), BendOut(10746, HVUp), BendIn(10748, HVDn, 11), BendMin(10750, HVUp), BendIn(10752, HVDn, 12), BendIn(10754, HVDL, 13)),
    TRow(104, hillyJungle, jungle, jungle),
    VRow(103, BendOut(10754, HVUR), BendOut(10756, HVUp), BendIn(10758, HVDn, 13), BendIn(10760, HVDL, 13)),
    TRow(102, savannah, hillyJungle, jungle, hillyJungle),
    VRow(101, BendOut(10760, HVUR, 7), MouthLt(10762, HVDR, 7)),
    TRow(100, jungle * 3, hillyJungle, jungle),
    TRow(98, jungle * 4, hillyJungle),
    TRow(96, jungle * 4, hillyJungle),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(120, "" * 2, "Newfoundland")
    str(116, "New York")
    str(108, "Hispaniola")
  }
}