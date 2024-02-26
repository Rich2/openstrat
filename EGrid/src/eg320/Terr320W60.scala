/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° west to 45° wast, centred on 60° west. Hex tile scale of 320km. */
object Terr320W60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w60(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, ice),
      TRow(164, CapeOld(5, 1, ice, SeaIceWinter)),
      TRow(162, CapeOld(4, 1, ice, SeaIceWinter), ice),
      TRow(160, SeaIceWinter, ice),
      TRow(158, sea, ice * 2),
      TRow(156, tundra, sea, ice),
      TRow(154, tundra * 2, sea, ice),
      TRow(152, tundra, sea * 2, ice),
      TRow(150, sea * 3, CapeOld(4, 2, tundra)),
      TRow(148, taiga, CapeOld(5, 3, mtainOld), sea * 2, CapeOld(3, 2, tundra)),
      VRow(147, MouthOld(10746, HVDn)),
      TRow(146, taiga, hillyTaiga, sea * 3),
      TRow(144, taiga * 3, sea * 2),
      TRow(142, taiga * 3, CapeOld(1, 3, taiga), sea * 2),
      TRow(140, taiga, CapeOld(2, 3, taiga), sea, CapeOld(1, 1, taiga), sea * 2),
      VRow(139, MouthOld(10744, HVDL), BendAllOld(10746, HVDn)),
      TRow(138, taiga * 2, sea, CapeOld(3, 3, taiga), CapeOld(1, 3, taiga), sea * 2),
      TRow(136, hillyTaiga, taiga, taiga, CapeOld(0, 4, taiga), sea * 3),
      VRow(135, MouthOld(10738, HVUR, 3, Lake)),
      TRow(134, CapeOld(2, 2, hillyTempForest), sea * 6),
      TRow(132, sea * 7),
    )
  }
  help.run
}