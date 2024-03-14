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
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      TRow(140, SeaIceWinter * 2),
      VRow(139, BendIn(7676, HVDL, 13, siceWin)),
      TRow(138, SepB(), hillyTundra, mtainOld),
      TRow(136, hillyTaiga * 2, mtainOld),
      TRow(134, hillyTaiga, CapeOld(3, 1, mtainOld), mtainOld),
      VRow(133, BendOut(7674, HVDL), BendAllOld(7678, HVDR), BendOut(7680, HVDn), MouthOld(7684, HVUR)),
      TRow(132, hillyTundra),
      VRow(129, MouthLt(7688, HVUL, 7), BendOut(7690, HVDL)),
      TRow(112, sea, Isle6(mtainOld)),
    )
  }
  help.run
}