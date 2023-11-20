/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1300km or 1.3Megametre. A hex tile area of 1.463582932 million
 *  km² A minimum Island area of 243930.488km². Japan has an area of 377,973 km², entitling it to 1 Island hex not 2. */
object Terr13E150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(112, hillyTundra),
      TRow(108, Isle(hillyForest)),
      TRow(106, sea, sea),
      VRow(101, SetSide(5628)),
      TRow(100, SideB(), sea * 2),
      VRow(99, BendOut(5628, HVDL)),
      TRow(98, Cape(3, 2, hillyJungle), sea),
      TRow(96, Cape(3, 0, hilly), sea),
      TRow(94, savannah, sea),
    )
  }
  help.run
}