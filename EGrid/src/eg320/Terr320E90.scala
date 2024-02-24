/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale of 320km. */
object Terr320E90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e90(124)
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
      TRow(160, hillyTundra * 2),
      TRow(158, tundra * 3),
      TRow(156, taiga, hillyTaiga * 2),
      TRow(154, taiga * 2, hillyTaiga, taiga),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, taiga * 5),
      TRow(144, taiga, level * 4),
      TRow(142, desert, level, hilly * 3, mtain),
      TRow(140, level, mtain, hillyDesert, desert, mtain, hilly),
      TRow(138, desert * 3, hillyDesert, desert, hillyDesert, desert),
      TRow(136, mtain * 2, desert * 5),
      TRow(134, mtain * 3, desert * 4),
      TRow(132, desert * 7),
      TRow(130, desert * 3, hillyDesert * 5),
      TRow(128, hillyDesert * 6, hilly, mtain),
      TRow(126, mtain, hillyDesert * 4, mtain * 3),
      TRow(124, level, mtain, hillyDesert * 2, mtain * 4, hilly),
    )
  }
  help.run
}