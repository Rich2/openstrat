/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale of 320km. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter * 2),
      TRow(158, tundra, hillyTundra, SeaIceWinter),
      TRow(156, taiga * 2, mtain),
      TRow(154, taiga * 3, hilly),
      TRow(152, taiga * 2, mtain, hillyTaiga),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, hillyTaiga, mtain, taiga, hillyTaiga * 2),
      TRow(144, mtain, hillyTaiga * 4),
      TRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(140, hillyTaiga, land, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(138, desert * 2, land, hilly, land, taiga * 2),
      TRow(136, land * 7),
      TRow(134, land * 6, sea),
      VRow(133, Mouth(4610, HVUp)),
      TRow(132, land * 3, Cape(2, 2, hilly), Cape(3, 1, hilly), hilly, sea),
      TRow(130, desert, land * 3, Cape(0, 3), Cape(4, 2, hilly), Cape(1, 2, hilly), sea),
      VRow(131, Mouth(4606, HVUL), Mouth(4614, HVUR)),
      TRow(130, desert, land * 3, Cape(0, 3), Cape(4, 2, hilly), Cape(1, 2, hilly), sea),
      TRow(128, land * 3, Cape(2), sea, Cape(2, 3, hilly), sea, hilly),
      VRow(127, Mouth(4608, HVDL)),
      TRow(126, mtain, hilly, land * 2, Cape(0, 2), sea * 2, hilly),
      VRow(125, Mouth(4612, HVDn)),
      TRow(124, hilly, mtain, land, hilly * 2),
    )
  }
  help.run
}