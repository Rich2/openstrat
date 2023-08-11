/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object TerrMegaE0 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e0(102)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(124, sea, taiga),
//      TRow(122, hills, plain),
//      TRow(120, sea, plain * 2),
//      TRow(118, sea, plain, hills),
//      TRow(116, hills * 2, sea),
      TRow(114, plain),
      TRow(112, plain, hills),
      TRow(110, hills, sea),
      TRow(108, hills, desert),
      TRow(106, desert * 3),
      TRow(104, desert * 3),
      TRow(102, jungle * 3),
//      VRow(141, Mouth(504, HVUL)),
//      TRow(140, sea, Hland(4, 2, Hilly()), Hland(2, 5), plain * 3),
//      TRow(138, sea * 2, plain * 2, hills, mtain * 2),
//      VRow(137, Mouth(526, HVUp)),
//      TRow(136, sea * 3, plain, hills, mtain, plain),
//      VRow(135, VertIn(526, HVUR)),
//      TRow(134, sea, hills * 3, sea, Isle(Hilly()), Hland(1, 1, Hilly())),
//      TRow(132, sea, hills, plain * 2, sea, Isle(Hilly()), sea),
//      VRow(131, MouthSpec(526, HVUL, HVRt, HVLt)),
//      TRow(130, sea, Hland(3, 3), Hland(1, 3, Hilly()), Hland(2, 2, Hilly()), sea * 2, Hland(3, 5, Hilly()), Hland(2, 3, Hilly())),
//      TRow(128, sea, Hland(2, 5, Hilly()), Hland(1, 0, Hilly()), hills * 3, Hland(1, 1, Hilly()), sea),
//      TRow(126, sea, Hland(1, 5), mtain, hillyDesert, desert * 4),
//      TRow(124, sea, hillyDesert, hills, desert * 6),
//      VRow(123, SetSide(495)),
//      TRow(122, SideB(), desert * 9),
//      VRow(121, SetSide(495)),
//      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
//      TRow(118, desert * 9),
    )
  }
  help.run
}

/*
object BritReg
{ def britGrid: EGridMegaLong = EGridMegaLong.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = TerrMegaE0.terrs.spawn(TerrMegaE0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =TerrMegaE0.sTerrs.spawn(TerrMegaE0.grid, britGrid)
  def britCorners: HCornerLayer =TerrMegaE0.corners.spawn(TerrMegaE0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGridMegaLong = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}*/
