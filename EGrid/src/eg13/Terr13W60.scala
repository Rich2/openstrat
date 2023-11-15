/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr13W60 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w60(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Hland(1, 4, ice)),
      TRow(112, Hland(3, 5, hillyTaiga)),
      TRow(110, Hland(3, 1, hillyForest)),
      VRow(109, Mouth(10752, HVUL)),
      TRow(108, Hland(3, 1, hillyForest)),
      TRow(106, SideB(), sea * 2),
      VRow(105, SetSide(10748)),
      TRow(104, SideB(), sea * 2),
      VRow(103, Mouth(10752, HVDR), BendOut(10748, HVUp)),
      TRow(102, Hland(1, 0, hills), sea),
      VRow(101, Mouth(10754, HVDL)),
      TRow(100, jungle, Hland(2, 0, jungle)),
      VRow(99, BendAll(10758, HVUR)),
      TRow(98, hills, plain),
      VRow(97, BendAll(10758, HVDR)),
      TRow(96, hills, Hland(1, 2)),
      VRow(95, BendAll(10756, HVDR)),
      TRow(94, Hland(1, 5, mtain), Hland(2, 2)),
      VRow(93, Mouth(10752, HVUL)),
      TRow(92, Hland(2, 1, savannah)),
      VRow(91, Mouth(10752, HVDL)),
      VRow(89, SetSide(10750)),
      VRow(87, SetSide(10750)),
      TRow(86, SideB(), sea),
    )
  }
  help.run
}