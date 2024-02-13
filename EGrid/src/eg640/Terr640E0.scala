/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 640km. A hex tile area of 354724.005km².
 *  A minimum island area of 59120.667km², which includes Britain but excludes Ireland and Sicily-Corsica. */
object Terr640E0 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e0(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(127, MouthLt(512, HVUp, 7)),
      TRow(126, sea, hillyTaiga),
      VRow(125, MouthRt(512, HVDn, 7)),
      TRow(124, sea, taiga),
      VRow(123, BendIn(512, HVDR, 13), BendIn(514, HVDn), BendOut(516, HVUp), BendAll(518, HVUL)),
      TRow(122, land * 2),
      VRow(121, MouthRt(510, HVDL, 7), BendOut(512, HVUL, 7)),
      TRow(120, sea, land * 2),
      VRow(119),
      TRow(118, hilly, land, hilly),
      VRow(117, BendInOut(514, HVDR, 13, 3), BendAll(516, HVDn)),
      TRow(116, hilly, hilly, mtain),
      VRow(115, MouthRt(508, HVDL), BendIn(510, HVDn, 10), BendAll(512, HVUp), ThreeUp(514, 13, 3, 10), BendIn(516, HVUp, 11)),
      TRow(114, sea, hilly, hilly, Cape(0, 2, hilly)),
      VRow(113, Mouth(520, HVDn)),
      TRow(112, hillyDesert, desert * 3),
      TRow(110, desert * 2, hillyDesert, desert),
      VRow(109, Mouth(504, HVUR)),
      TRow(108, desert * 4),
      TRow(106, sahel * 5),
      TRow(104, Land(Hilly, Savannah, Forest), Land(Level, Savannah, Forest) * 3, Land(Hilly, Savannah, Forest)),
      TRow(102, Cape(3, 2, jungle), Cape(3, 1, jungle) * 2, jungle, jungle),
      VRow(101, BendOut(508, HVDn), BendOut(512, HVDn)),
      TRow(100, sea * 4, hillyJungle),
      TRow(98, sea * 4, hillyJungle),
      TRow(96, sea * 4, Cape(4, 2, hillySavannah))
    )
  }
  help.run
}