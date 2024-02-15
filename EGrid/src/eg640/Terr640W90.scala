/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 640km.  */
object Terr640W90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w90(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, tundra, Isle(tundra)),
      TRow(124, Cape(1, 2, taiga), Cape(4, 3, tundra)),
      VRow(123, Mouth(9728, HVDL), Mouth(9732, HVDR)),
      TRow(122, taiga * 2),
      TRow(120, taiga * 3),
      VRow(119, Mouth(9728, HVDL, 3, Lake), Mouth(9730, HVUR, 3, Lake), Mouth(9732, HVUp, 3, Lake)),
      TRow(118, taiga, taiga, taiga),
      VRow(117, Mouth(9732, HVDn, 3, Lake), Mouth(9736, HVUL)),
      TRow(116, savannah, savannah, hilly),
      TRow(114, sahel, savannah, land, land),
      TRow(112, savannah, Cape(3), jungle, sea),
      VRow(111, BendAll(9724, HVDR), BendOut(9726, HVDn)),
      TRow(110, sahel, sea * 2, sea),
      VRow(109, BendOut(9724, HVUR), BendOut(9726, HVUp), Mouth(9720, HVUR)),
      TRow(108, jungle, Cape(0, 1, jungle), sea, Isle(hillyJungle)),
      TRow(106, sea, Land(Hilly, Savannah, Forest), Land(Hilly, Savannah, Forest)),
      VRow(105, MouthLt(9724, HVUL, 7), BendIn(9726, HVUp, 13), Mouth(9728, HVUR), BendOut(9738, HVUp)),
      TRow(104, sea * 3, Cape(4, 1, hillyJungle), Cape(0, 1, hillyJungle)),
      TRow(102, sea * 4, mtain),
      TRow(100, sea * 4, Cape(5, 1, mtain)),
      TRow(98, sea * 3, Cape(4, 2, mtain), jungle),
      TRow(96, sea * 4, Cape(4, 1, mtain)),
    )
  }
  help.run
}