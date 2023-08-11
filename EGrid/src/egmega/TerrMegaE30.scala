/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object TerrMegaE30 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e30(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, sea),
      TRow(116, taiga),
      TRow(114, plain),
      TRow(112, hills, plain),
      TRow(110, hills, hills),
      TRow(108, plain, desert),
      VRow(107, Mouth(1538, HVUL), VertIn(1540, HVDL)),
      TRow(106, desert * 3),
      VRow(105, VertIn(1540, HVUR), VertIn(1542, HVDL)),
      TRow(104, desert * 3),
      VRow(103, VertIn(1542, HVUR)),
      TRow(102, jungle * 2, hills),
      TRow(100, jungle * 2, plain),
      TRow(98, jungle * 2, sea),
      TRow(96, plain * 2, Hland(3, 1)),
      VRow(95, Mouth(1538, HVUL)),
      TRow(94, desert, Hland(2, 1), Hland(2, 4)),
      TRow(92, Hland(2, 2, Hilly()), sea),
      TRow(90, sea * 2),
      TRow(88, sea * 2),
      TRow(86, sea),
      TRow(84, sea),
      TRow(82, ice),
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
