/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, mtainOld),
      TRow(110, hillyTaiga),
      VRow(109, MouthLt(8702, HVUp, 7)),
      TRow(108, hillyOceForest),
      VRow(107, MouthRt(8702, HVDn, 7)),
      TRow(106, sea, hillyDesert),
      VRow(105, MouthLt(8704, HVUL, 7), BendOut(8706, HVDL)),
      TRow(104, sea, CapeOld(3, 2, hillySahel)),
      VRow(103, MouthRt(8708, HVDR, 7)),
      VRow(91, BendIn(8708, HVDR, 13)),
      VRow(89, BendIn(8708, HVUR, 13)),
      VRow(87, MouthOld(8704, HVDL, 3, wice), MouthOld(8708, HVDR, 3, wice)),
      TRow(86, CapeOld(0, 1, ice, wice))
    )
  }
  help.run
}