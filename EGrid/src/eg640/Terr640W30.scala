/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile area of 354724.005km².
 * [[Isle9]] 100112.536km² => 125054.068km². Iceland 103125km².
 * [[Isle3]] 8660.254km² => 16974.097km².
 * Below 8660.254km² Canaries 7,492 km². */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(131, MouthRt(11780, HVUp)),
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
    VRow(109, MouthLt(11782, HVUp, 7)),
    TRow(108, sea * 3, deshot),
    VRow(107, BendIn(11782, HVUR, 13), BendMin(11784, HVDL)),
    TRow(106, sea * 4, sahel),
    VRow(101, MouthLt(11766, HVDR, 7), MouthLt(11788, HVUL, 7)),
    VRow(99, MouthRt(11770, HVUL, 7), MouthLt(11772, HVDR, 7)),
    TRow(98, hillySahel),
    TRow(96, hillySavannah, hillySavannah),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(126, "", "Iceland")
  }
}