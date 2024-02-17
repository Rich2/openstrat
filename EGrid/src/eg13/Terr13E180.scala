/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1300km or 1.3 Megametre. A hex tile area of 1.463582932 million
 *  km² A minimum Island area of 243930.488km². New Zealand has a land area of 268021km2 so qualifies as an islamd. */
object Terr13E180 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e180(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(113, MouthOld(6658, HVUp)),
      TRow(112, Cape(2, 2, hillyTundra)),
      VRow(111, BendAll(6658, HVUR)),
      TRow(108, SepB(), sea),
      TRow(92, Isle10(hilly)),
      VRow(87, MouthOld(6656, HVDR, 3, wice)),
      TRow(86, wice)
    )
  }
  help.run
}