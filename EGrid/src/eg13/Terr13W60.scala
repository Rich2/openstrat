/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr13W60 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w60(86)

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, ice),
      TRow(112, hillyTaiga),
      VRow(111, MouthLt(10750, HVDn, 7), BendOut(10754, HVUR, 7), ThreeDown(10756, 12, 12, 13)),
      TRow(110, hillyForest),
      VRow(109, MouthLt(10752, HVUL), ThreeDown(10754, 13, 0, 13), ThreeUp(10756, 12, 0, 13)),
      TRow(108, hilly),
      VRow(107, MouthLt(10750, HVUL), BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13)),
      TRow(106, sea * 2),
      VRow(105, SetSep(10748)),
      TRow(104, SepB(), sea * 2),
      VRow(103, MouthOld(10752, HVDR), BendOut(10748, HVUp)),
      TRow(102, Cape(0, 1, hilly), sea),
      VRow(101, MouthRt(10756, HVUL), BendIn(10758, HVDL, 13)),
      TRow(100, jungle, jungle),
      VRow(99, BendIn(10758, HVUR)),
      TRow(98, hilly, land),
      VRow(97, BendAllOld(10758, HVDR)),
      TRow(96, hilly, Cape(2, 1)),
      VRow(95, BendAllOld(10756, HVDR)),
      TRow(94, Cape(5, 1, mtain), Cape(2, 2)),
      VRow(93, MouthOld(10752, HVUL)),
      TRow(92, Cape(1, 2, savannah)),
      VRow(91, MouthOld(10752, HVDL)),
      VRow(89, SetSep(10750)),
      VRow(87, SetSep(10750), MouthOld(10756, HVDL, 3, wice)),
      TRow(86, wice),
    )
  }
  help.run
}