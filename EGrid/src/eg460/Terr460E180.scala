/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E180 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e180(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIcePerm),
      TRow(142, SeaIceWinter),
      TRow(140, SeaIceWinter * 2),
      VRow(139, Source(6656, HVUR, 2, 4, siceWin), BendIn(6658, HVDn, 13, siceWin), BendIn(6660, HVDL, 13, siceWin)),
      TRow(138, mtainTundra * 2),
      VRow(137),
      TRow(136, mtainTundra, hillyTundra, siceWin),
      VRow(135, BendOut(6656, HVDR)),
      TRow(134, mtainTundra, sea, hillyTundra),
      VRow(133, BendOut(6650, HVDR), BendOut(6662, HVDL)),
      VRow(131, SetSep(6649)),
      TRow(130, SepB()),
    )
  }
  help.run
}