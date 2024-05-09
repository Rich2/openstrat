/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile area of 354724.005km².
 * [[Isle9]] 100112.536km² => 125054.068km². Iceland 103125km².
 * [[Isle3]] 8660.254km² => 16974.097km².
 * Below 8660.254km² Canaries 7,492 km². */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(131, OrigRt(11780, HVDn)),
    TRow(130, ice),
    VRow(129, BendOut(11778, HVDR), BendIn(11780, HVUL, 13)),
    TRow(128, ice),
    VRow(127, Bend(11776, HVDR, 10, 4), ThreeUp(11778, 0, 6, 9)),
    TRow(126, ice, hillyTundra),
    VRow(125, BendOut(11774, HVDR), ThreeUp(11776, 11, 0, 13), BendIn(11778, HVUp, 6), OrigRt(11780, HVDL)),
    VRow(123, BendIn(11772, HVUp, 13), ThreeDown(11774, 13, 0, 13)),
    TRow(122, SepB(siceWin)),
    VRow(121, SetSep(11771)),
    VRow(119, BendIn(11770, HVUL, 13)),
    VRow(109, OrigLt(11782, HVDn, 7)),
    TRow(108, sea * 3, deshot),
    VRow(107, BendIn(11782, HVUR, 13), BendMin(11784, HVDL)),
    TRow(106, sea * 4, sahel),
    VRow(105, OrigRt(11784, HVUp)),
    VRow(101, OrigLt(11766, HVUL, 7), OrigLt(11788, HVDR, 7)),
    VRow(99, OrigRt(11770, HVDR, 7), OrigLt(11772, HVUL, 7)),
    TRow(98, hillySahel),
    VRow(97, OrigRt(11774, HVDn, 7)),
    TRow(96, hillySavannah, hillySavannah),
    VRow(95, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13)),
    TRow(94, hillySavannah),
    VRow(93, BendOut(11772, HVUR, 7), BendIn(11774, HVDL, 13)),
    TRow(92, hillyJungle),
    VRow(91, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13)),
    TRow(90, hillySavannah),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(126, "", "Iceland")
  }
}