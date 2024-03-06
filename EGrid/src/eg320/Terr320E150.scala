/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter * 2),
      TRow(158, CapeOld(5, 3, tundra, SeaIceWinter), SeaIceWinter * 2),
      TRow(156, tundra * 2, CapeOld(0, 2, tundra, SeaIceWinter)),
      TRow(154, tundra * 4),
      TRow(152, tundra, taiga * 3),
      TRow(150, taiga * 4),
      TRow(148, taiga * 3, sea, taiga),
      TRow(146, sea * 3, taiga, sea),
      TRow(144, sea * 3, CapeOld(4, 2, taiga), CapeOld(1, 2, taiga)),
      TRow(142, taiga * 2, sea * 2, CapeOld(2, 3, taiga), sea),
      TRow(140, taiga, sea * 5),
      TRow(138, CapeOld(2, 2, taiga), sea * 6),
      TRow(136, mtainContForest, hillyContForest),
      TRow(134, CapeOld(3, 3, hillyOceForest), CapeOld(2, 2, hillyOceForest), sea * 5),
      TRow(132, mtainContForest * 2),
      VRow(131, MouthOld(5626, HVDn)),
      TRow(130, hillyOce, CapeOld(2, 1, hillyOce), sea * 6),
      TRow(128, CapeOld(2, 2, hillyOce), sea * 7),
    )
  }
  help.run
}
