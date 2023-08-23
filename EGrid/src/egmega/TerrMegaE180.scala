/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1 megametre or 1000km. */
object TerrMegaE180 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e180(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(118, hillyTundra),
      TRow(116, Hland(2, 1, hillyTundra)),
//      TRow(114, plain),
//      TRow(112, desert * 2),
//      TRow(110, desert * 2),
//      TRow(108, hillyDesert * 2),
//      TRow(106, desert, sea, plain),
//      TRow(104, SideB(), Hland(2, 2, Hilly(Desert)), sea * 2),
//      TRow(96, SideB(), Hland(2, 1, Hilly()), sea * 2),
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
