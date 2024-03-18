/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 460km.  */
object Terr460W150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w150(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      TRow(140, SeaIceWinter * 2),
      VRow(139, BendIn(7676, HVDL, 13, siceWin)),
      TRow(138, SepB(siceWin), hillyTundra, mtainOld),
      VRow(137, SetSep(7675, siceWin)),
      TRow(136, hillyTaiga * 2, mtainOld),
      VRow(135, Bend(7674, HVUL, 13, 2, siceWin)),
      TRow(134, hillyTaiga, mtainTundra, mtainTaiga),
      VRow(133, BendOut(7674, HVDL, 6, siceWin), SourceLt(7680, HVDR, 7), BendIn(7682, HVUp, 13), SourceLt(7684, HVDL)),
      TRow(132, hillyTundra),
      VRow(129, MouthLt(7688, HVUL, 7), BendOut(7690, HVDL)),
      TRow(112, sea, Isle6(mtainOld)),
    )
  }
  help.run
}