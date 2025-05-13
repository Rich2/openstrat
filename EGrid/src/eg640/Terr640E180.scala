/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * [[Isle10]] 125054.068km² => 152766.881km². South Island (NZ) 147583.72km².
 * [[Isle9]] 100112.536km² => 125054.068km². North Island (New Zealand) 111583km². */
object Terr640E180 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e180(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(130, siceWin),
    VertRow(129, BendOut(6654, HVUp, 7, siceWin), BendIn(6656, HVDn, 13, siceWin), ThreeDown(6658, 0, 8, 13, siceWin)),
    TileRow(128, tundra),
    VertRow(127, BendOut(6658, HVUR), BendIn(6660, HVDL, 8, siceWin)),
    TileRow(126, hillyTundra, tundra),
    VertRow(125, OrigLt(6656, HVDR, 7, SeaIceWinter), ThreeDown(6658, 13, 13, 0, SeaIceWinter), BendIn(6660, HVUL, 13, SeaIceWinter)),
    TileRow(124, siceWin, hillyTundra),

    VertRow(123, BendMin(6652, HVDR, 3, siceWin), OrigRt(6654, HVDL, 7, siceWin), BendIn(6658, HVUR, 13, siceWin), ThreeDown(6660, 13, 13, 0, siceWin),
      Orig(6662, HVDL, 4, 2, siceWin)),

    TileRow(122, SepB()),
    TileRow(96, SepB()),
    TileRow(92, SepB()),
    VertRow(91, SetSep(6649)),
    TileRow(84, Isle9(hillySubForest)),
    TileRow(82, Isle10(hillyOce)),
    TileRow(74, sea, SeaIcePerm),
    TileRow(72, SeaIcePerm),
    TileRow(70, SeaIcePerm)
    )
  }
  help.run
}