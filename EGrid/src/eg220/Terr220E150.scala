/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 135° east to 165° east, centred on 150° east. */
object Terr220E150 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e150(138, 152)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(166, SeaIcePerm),
//      TRow(164, SeaIceWinter),
//      TRow(162, SeaIceWinter * 2),
//      TRow(160, SeaIceWinter * 2),
//      TRow(158, Cape(5, 3, tundra, SeaIceWinter), SeaIceWinter * 2),
//      TRow(156, tundra * 2, Cape(0, 2, tundra, SeaIceWinter)),
//      TRow(154, tundra * 4),
      TRow(152, sea),
      TRow(150, sea, mtain, hillyForest),
      VRow(149, MouthRt(5616, HVDn, 7), MouthRt(5618, HVDL), BendIn(5620, HVDn, 13), BendIn(5622, HVDL, 13)),
      TRow(148, sea * 2, mtain),
      VRow(147, MouthLt(5622, HVDn, 7)),
      TRow(146, sea, mtain),
      TRow(144, sea, mtain),
      TRow(142, mtain, hilly),
      TRow(140, hilly),
//      TRow(138, Cape(2, 2, taiga), sea * 6),
//      TRow(136, sea, Cape(5, 3, hillyForest), sea * 5),
//      TRow(134, Cape(3, 3, hillyForest), Cape(2, 2, hillyForest), sea * 5),
//      TRow(132, sea, Cape(0, 2, hilly), sea * 5),
//      VRow(131, Mouth(5626, HVDn)),
//      TRow(130, hilly, Cape(2, 1, hilly), sea * 6),
//      TRow(128, Cape(2, 2, hilly), sea * 7),
    )
  }
  help.run
}
