/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 east to 105 east, centred on 90° east. */
object Terr13E90 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e90(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, mtain),
      TRow(108, hillyDesert),
      TRow(106, mtain, mtain),
      TRow(104, Cape(3, 1, jungle), hillyJungle),
      VRow(103, BendAll(3580, HVDL), Mouth(3582, HVUL), Mouth(3586, HVUR)),
      TRow(102, Cape(1, 4, hillyJungle), Cape(4, 2, jungle)),
      VRow(101, BendIn(3586, HVUp), BendIn(3588, HVDn)),
      TRow(100, sea, Cape(1, 1, jungle)),
      VRow(99, BendIn(3588, HVDR), ThreeWay(3590)),
      VRow(95, BendOut(3590, HVUL)),
      VRow(87, Mouth(3584, HVDR, wice)),
      TRow(86, ice)
    )
  }
  help.run
}