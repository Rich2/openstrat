/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 460km.
 * [[Isle4]] 8768.845km² => 14495.438km². Kodiak archipelago 13890km². */
object Terr460W150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w150(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    TRow(144, SeaIcePerm),
    VRow(143, BendIn(7684, HVDR, 6, siceWin, SeaIcePerm)),
    TRow(142, SeaIceWinter),
    TRow(140, SeaIceWinter * 2),
    VRow(139, BendIn(7676, HVDL, 13, siceWin)),
    TRow(138, SepB(siceWin), hillyTundra, mtainOld),
    VRow(137, SetSep(7675, siceWin)),
    TRow(136, hillyTaiga * 2, mtainOld),
    VRow(135, Bend(7674, HVUL, 13, 2, siceWin)),
    TRow(134, hillyTaiga, mtainTundra, mtainTaiga),
    VRow(133, BendOut(7674, HVDL, 6, siceWin), BendIn(7678, HVDR, 12), BendIn(7680, HVDn, 12), BendInLt(7682, HVDL, 12, 7)),
    TRow(132, hillyTundra, mtainTundra),

    VRow(131, BendIn(7672, HVDR, 13), BendOut(7674, HVUL, 7,siceWin, sea), BendOut(7676, HVDR, 7), ThreeUp(7678, 12, 0, 11), BendIn(7680, HVUp, 12),
      BendIn(7682, HVUL, 12)),

    TRow(130, hillyTaiga),
    VRow(129, BendIn(7672, HVUR, 13), BendIn(7674, HVUp, 13), BendIn(7676, HVUL, 13), MouthLt(7688, HVUL, 7), BendOut(7690, HVDL)),
    TRow(112, sea, Isle6(mtainOld)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(132, "Aleut north", "Kodiak Archipelago")
    str(130, "Aleut south")
  }
}