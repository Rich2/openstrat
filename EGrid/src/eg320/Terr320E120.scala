/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale of 320km. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
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
      TRow(158, tundra, hillyTundra, SeaIceWinter),
      TRow(156, taiga * 2, mtainOld),
      TRow(154, taiga * 3, hilly),
      TRow(152, taiga * 2, mtainOld, hillyTaiga),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, hillyTaiga, mtainOld, taiga, hillyTaiga * 2),
      TRow(144, mtainOld, hillyTaiga * 4),
      TRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(140, hillyTaiga, level, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(138, desert * 2, level, hilly, level, taiga * 2),
      TRow(136, level * 7),
      TRow(134, level * 6, sea),
      VRow(133, MouthOld(4610, HVUp)),
      TRow(132, level * 3, CapeOld(2, 2, hilly), CapeOld(3, 1, hilly), hilly, sea),
      TRow(130, desert, level * 3, CapeOld(0, 3), CapeOld(4, 2, hilly), CapeOld(1, 2, hilly), sea),
      VRow(131, MouthOld(4606, HVUL), MouthOld(4614, HVUR)),
      TRow(130, desert, level * 3, CapeOld(0, 3), CapeOld(4, 2, hilly), CapeOld(1, 2, hilly), sea),
      TRow(128, level * 3, level/*2*/, sea, CapeOld(2, 3, hilly), sea, hilly),//check land and first hilly
      VRow(127, MouthOld(4608, HVDL)),
      TRow(126, mtainOld, hilly, level * 2, CapeOld(0, 2), sea * 2, hilly),
      VRow(125, MouthOld(4612, HVDn)),
      TRow(124, hilly, mtainOld, level, hilly * 2),
    )
  }
  help.run
}