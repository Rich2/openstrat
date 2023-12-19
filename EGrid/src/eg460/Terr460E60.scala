/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 460km.  */
object Terr460E60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e60(114)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(134, taiga * 3),
      TRow(132, taiga, hillyTaiga, taiga),
      TRow(130, land * 4),
      TRow(128, savannah, sahel * 2, savannah),
      TRow(126, sahel, sahel * 3),
      VRow(125, BendOut(2554, HVDR), Mouth(2556, HVUR)),
      TRow(124, hillySavannah, sahel * 3, hillySavannah),
      VRow(123, BendOut(2554, HVUR)),
      TRow(122, Cape(1, 2, savannah), desert, sahel, mtain * 2),
      VRow(121, Mouth(2554, HVDL)),
      TRow(120, hillySahel, hillySahel, hillyDesert, mtain * 2),
      TRow(118, hillySahel, hillyDesert * 4, savannah),
      VRow(117, Mouth(2550, HVUp)),
      TRow(116, Cape(3, 2, hillyDesert), mtain, hillyDesert, hillySahel, hillyDesert, desert),
      VRow(115, BendIn(2556, HVDL)),
      TRow(114, desert, Cape(0, 1, desert), Cape(3, 2, mtain), hillyDesert, sahel * 2),
      VRow(113, BendOut(2560, HVDn)),
//      TRow(112, hillyDesert, hillyDesert, Land(Mountains, Desert), savannah),
//      VRow(111, Mouth(2554, HVUL), BendOut(2556, HVUp)),
//      TRow(110, sahel, Cape(0, 2, sahel), Cape(3, 2, sahel), savannah),
//      VRow(109, Mouth(2564, HVUR)),
//      TRow(108, sahel, sea * 2, Cape(4, 1, savannah)),
//      VRow(107, BendOut(2568, HVDL)),
//      TRow(106, Cape(2, 2, desert), sea * 3, Cape(4, 1, savannah)),
//      TRow(104, Cape(0, 1, desert)),
//      VRow(101, Mouth(2550, HVUp)),
    )
  }
  help.run
}