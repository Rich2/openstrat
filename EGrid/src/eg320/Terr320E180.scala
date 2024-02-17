/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° east to 165° west, centred on 180° east. Hex tile scale of 320km. */
object Terr320E180 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e180(120)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter * 2),
      TRow(158, SeaIceWinter * 3),
      TRow(156, Cape(5, 3, tundra, SeaIceWinter), SeaIceWinter * 2),
      TRow(154, tundra * 2, Cape(0, 2, tundra, SeaIceWinter), SeaIceWinter),
      TRow(152, tundra, sea, Cape(1, 4, tundra, SeaIceWinter), Cape(4, 2, taiga, SeaIceWinter)),
      TRow(150, tundra * 2, sea * 2),
      TRow(148, tundra, sea * 4),
      VRow(144, SetSep(6646)),
     // VRow(143, Mouth(6666, HVUL)),
    )
  }
  help.run
}