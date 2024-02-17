/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 east to 105 east, centred on 90° east. */
object Terr13E90 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e90(86)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, mtain),
      TRow(108, hillyDesert),
      TRow(106, mtain, mtain),
      TRow(104, jungle, hillyJungle),
      VRow(103, MouthLt(3580, HVDR), MouthMax(3584, HVUp)),
      TRow(102, hillyJungle, jungle),
      VRow(101, MouthLt(3580, HVUL), BendIn(3582, HVUp), ThreeUp(3584, 13, 0, 13), BendIn(3586, HVUp), BendIn(3588, HVDn)),
      TRow(100, sea, jungle),
      VRow(99, BendIn(3588, HVDR), ThreeUp(3590, 1, 7, 6)),
      VRow(95, BendOut(3590, HVUL)),
      VRow(87, MouthOld(3584, HVDR, 3, wice)),
      TRow(86, ice)
    )
  }
  help.run
}