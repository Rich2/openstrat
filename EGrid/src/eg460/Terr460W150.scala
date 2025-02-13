/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 460km.
 * [[Isle4]] 8768.845km² => 14495.438km². Kodiak archipelago 13890km². */
object Terr460W150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w150(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    TileRow(144, SeaIcePerm),
    VertRow(143, BendIn(7684, HVDR, 6, siceWin, SeaIcePerm)),
    TileRow(142, SeaIceWinter),
    TileRow(140, SeaIceWinter * 2),
    VertRow(139, BendIn(7676, HVDL, 13, siceWin)),
    TileRow(138, SepB(siceWin), hillyTundra, mtainTaiga),
    VertRow(137, SetSep(7675, siceWin)),
    TileRow(136, hillyTaiga * 2, mtainTaiga),
    VertRow(135, Bend(7674, HVUL, 13, 2, siceWin)),
    TileRow(134, hillyTaiga, mtainTundra, mtainTaiga),
    VertRow(133, BendOut(7674, HVDL, 6, siceWin), BendIn(7678, HVDR, 12), BendIn(7680, HVDn, 12), BendInLt(7682, HVDL, 12, 7)),
    TileRow(132, hillyTundra, mtainTundra),

    VertRow(131, BendIn(7672, HVDR, 13), BendOut(7674, HVUL, 7,siceWin, sea), BendOut(7676, HVDR, 7), ThreeUp(7678, 12, 0, 11), BendIn(7680, HVUp, 12),
      BendIn(7682, HVUL, 12)),

    TileRow(130, hillyTaiga),
    VertRow(129, BendIn(7672, HVUR, 13), BendIn(7674, HVUp, 13), BendIn(7676, HVUL, 13), OrigLt(7688, HVDR, 7), BendOut(7690, HVDL)),
    TileRow(112, sea, Isle6(mtainSavannah)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(132, "Aleut north", "Kodiak Archipelago")
    str(130, "Aleut south")
  }
}