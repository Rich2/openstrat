/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 640km [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile area of 354724.005km².
 * [[Isle9]] 100112.536km² => 125054.068km². Iceland 103125km².
 * [[Isle3]] 8660.254km² => 16974.097km².
 * Below 8660.254km² Canaries 7,492 km². */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(131, OrigRt(11780, HVDn)),
    TileRow(130, ice),
    VertRow(129, BendOut(11778, HVDR), BendIn(11780, HVUL, 13)),
    TileRow(128, ice),
    VertRow(127, Bend(11776, HVDR, 10, 4), ThreeUp(11778, 0, 6, 9)),
    TileRow(126, ice, hillyTundra),
    VertRow(125, BendOut(11774, HVDR), ThreeUp(11776, 11, 0, 13), BendIn(11778, HVUp, 6), OrigRt(11780, HVDL)),
    VertRow(123, BendIn(11772, HVUp, 13)),
    TileRow(122, SepB(siceWin)),
    VertRow(121, SetSep(11771)),
    VertRow(119, BendIn(11770, HVUL, 13)),
    VertRow(109, OrigLt(11782, HVDn, 7)),
    TileRow(108, sea * 3, deshot),
    VertRow(107, BendIn(11782, HVUR, 13), BendMin(11784, HVDL)),
    TileRow(106, sea * 4, sahel),
    VertRow(105, OrigRt(11784, HVUp)),
    VertRow(101, OrigLt(11766, HVUL, 7), OrigLt(11788, HVDR, 7)),
    VertRow(99, OrigRt(11770, HVDR, 7), OrigLt(11772, HVUL, 7)),
    TileRow(98, hillySahel),
    VertRow(97, OrigRt(11774, HVDn, 7)),
    TileRow(96, hillySavannah, hillySavannah),
    VertRow(95, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13)),
    TileRow(94, hillySavannah),
    VertRow(93, BendOut(11772, HVUR, 7), BendIn(11774, HVDL, 13)),
    TileRow(92, hillyJungle),
    VertRow(91, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13)),
    TileRow(90, hillySavannah),
    VertRow(89, OrigLt(11768, HVDR, 7), BendIn(11770, HVUp, 13), BendIn(11772, HVUL, 13)),
    TileRow(74, SeaIcePerm * 2),
    TileRow(72, SeaIcePerm),
    TileRow(70, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(126, "", "Iceland")
  }
}