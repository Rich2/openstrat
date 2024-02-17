/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1300km or 1.3Megametre. A hex tile area of 1.463582932 million
 *  km² A minimum Island area of 243930.488km². Japan has an area of 377,973 km², entitling it to 1 Island hex not 2. */
object Terr13E150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(112, hillyTundra),
      TRow(108, Isle10(hillyForest)),
      TRow(106, sea, sea),
      VRow(103, BendIn(5628, HVDL, 13)),
      TRow(102, SepB()),
      VRow(101, SetSep(5628)),
      TRow(100, SepB(), sea * 2),
      VRow(99, BendOut(5628, HVDL), MouthOld(5630, HVDn)),
      TRow(98, Cape(3, 2, hillyJungle), sea),
      TRow(96, Cape(0, 2, hilly), sea),
      VRow(95, MouthOld(5634, HVDn)),
      TRow(94, savannah, sea),
      VRow(87, MouthOld(5632, HVDL, 3, wice), MouthOld(5636, HVDR, 3, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}