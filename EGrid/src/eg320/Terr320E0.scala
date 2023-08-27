/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale of 320km. So one of the principles of these terrain grids is that
 *  tiles and tile sides should be specified according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should
 *  not be a sea hex as the majority of the hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex.
 *  Given that it is a land hex by geographical area it must be assigned to France. */
object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(118)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(156, sea * 3),
      TRow(154, sea * 4),
      TRow(152, sea * 3, hillyTaiga),
      TRow(150, sea * 3, hillyTaiga),
      VRow(149, Mouth(514, HVUp)),
      TRow(148, sea * 3, Hland(1, 4, hillyTaiga), taiga),
      VRow(147, ThreeWay(516)),
      TRow(146, sea, hills, sea, Hland(4, 4), Hland(3, 2, forest)),
      VRow(145, Mouth(508, HVUL), Mouth(518, HVDR)),
      TRow(144, sea, Hland(2, 1), plain, sea, plain),
      TRow(142, sea, Hland(2, 2), Hland(1, 5), Hland(3, 1), plain * 2),
      VRow(141, Mouth(504, HVUL)),
      TRow(140, sea, Hland(4, 2, hills), Hland(2, 5), plain * 3),

      TRow(138, sea * 2, plain * 2, hills, mtain * 2),
      VRow(137, Mouth(526, HVUp)),
      TRow(136, sea * 3, plain, hills, mtain, plain),
      VRow(135, BendAll(526, HVUR)),
      TRow(134, sea, hills * 3, sea, Isle(hills), Hland(1, 1, hills)),
      TRow(132, sea, hills, plain * 2, sea, Isle(hills), sea),
      VRow(131, MouthSpec(526, HVUL, HVRt, HVLt)),
      TRow(130, sea, Hland(3, 3), Hland(1, 3, hills), Hland(2, 2, hills), sea * 2, Hland(3, 5, hills), Hland(2, 3, hills)),
      TRow(128, sea, Hland(2, 5, hills), Hland(1, 0, hills), hills * 3, Hland(1, 1, hills), sea),
      TRow(126, sea, Hland(1, 5), mtain, hillyDesert, desert * 4),
      TRow(124, sea, hillyDesert, hills, desert * 6),
      VRow(123, SetSide(495)),
      TRow(122, SideB(), desert * 9),
      VRow(121, SetSide(495)),
      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
      TRow(118, desert * 9),
    )
  }
  help.run
}

object BritReg
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = Terr320E0.terrs.spawn(Terr320E0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr320E0.sTerrs.spawn(Terr320E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr320E0.corners.spawn(Terr320E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}