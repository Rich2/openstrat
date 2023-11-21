/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1300km. Baffin Island appears at this scale by Cuba is too
 *  small. */
object Terr13W90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w90(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Isle(tundra)),
      TRow(112, Cape(1, 2, taiga)),
      VRow(111, Mouth(9728, HVDL)),
      TRow(110, forest),
      TRow(108, land),
      TRow(106, savannah, Cape(2, 1)),
      VRow(105, Mouth(9726, HVDL)),
      TRow(104, Cape(0, 2, hillyJungle), sea),
      VRow(103, SetSide(9725), BendOut(9730, HVUR), BendOut(9732, HVUp)),
      TRow(102, sea, Cape(3, 2, hillyJungle)),
      VRow(101, Mouth(9732, HVUR)),
      TRow(100, sea, Cape(5, 1, hillyJungle)),
      VRow(99, Mouth(9730, HVDn)),
      VRow(91, Mouth(9730, HVUR)),
      TRow(90, Cape(3, 3, mtain)),
      TRow(86, Cape(5, 4, ice))
    )
  }
  help.run
}