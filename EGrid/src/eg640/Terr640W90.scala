/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 640km.  */
object Terr640W90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w90(96)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, ice),
      TRow(128, tundra),
      VRow(127, BendIn(9728, HVDR), BendIn(9730, HVUL)),
      TRow(126, tundra, tundra),
      VRow(125, BendIn(9728, HVUR, 6), ThreeDown(9730, 3, 3, 3)),
      TRow(124, taiga, tundra),
      VRow(123, MouthOld(9730, HVDn, 7)),
      TRow(122, taiga * 2),
      TRow(120, taiga * 3),
      VRow(119, MouthOld(9728, HVDL, 3, Lake), MouthOld(9730, HVUR, 3, Lake), MouthOld(9732, HVUp, 3, Lake)),
      TRow(118, taiga, taiga, taiga),
      VRow(117, MouthOld(9732, HVDn, 3, Lake), MouthOld(9736, HVUL)),
      TRow(116, savannah, savannah, hilly),
      TRow(114, sahel, savannah, land, land),
      TRow(112, savannah, land, jungle, sea),
      VRow(111, BendAllOld(9724, HVDR), BendOut(9726, HVDn), BendIn(9728, HVUp, 13), BendOut(9730, HVDn, 7), BendOut(9732, HVDL, 7), BendIn(9736, HVDL, 13)),
      TRow(110, sahel, sea * 2, jungle),

      VRow(109, MouthOld(9720, HVUR), BendOut(9724, HVUR), BendOut(9726, HVUp, 7), MouthLt(9728, HVUR), BendIn(9730, HVDR, 13), ThreeUp(9732, 13, 13, 0),
        Bend(9734, HVUp, 13, 3), ThreeUp(9736, 0, 13, 13), BendIn(9738, HVDL, 6)),

      TRow(108, jungle * 2, hillyJungle * 2),
      VRow(107, Bend(9730, HVUR, 13, 3), BendIn(9732, HVUp, 13), BendOut(9734, HVDn), BendIn(9736, HVUp, 13), BendIn(9738, HVUL, 7)),
      TRow(106, sea, Land(Hilly, Savannah, Forest), Land(Hilly, Savannah, Forest)),
      VRow(105, MouthLt(9724, HVUL, 7), BendIn(9726, HVUp, 13), MouthOld(9728, HVUR), BendOut(9738, HVUp)),
      TRow(104, sea * 3, Cape(4, 1, hillyJungle), Cape(0, 1, hillyJungle)),
      VRow(103, MouthRt(9732, HVDR, 7)),
      TRow(102, sea * 4, mtain),
      TRow(100, sea * 4, Cape(5, 1, mtain)),
      TRow(98, sea * 3, Cape(4, 2, mtain), jungle),
      TRow(96, sea * 4, Cape(4, 1, mtain)),
    )
  }
  help.run
}