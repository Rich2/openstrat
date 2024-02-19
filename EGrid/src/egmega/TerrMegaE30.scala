/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object TerrMegaE30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e30(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(119, BendOut(1536, HVUp), MouthOld(1538, HVUR)),
      TRow(118, tundra),
      TRow(116, taiga),
      VRow(115, MouthOld(1534, HVUR)),
      TRow(114, land),
      TRow(112, hilly, land),
      TRow(110, Cape(3, 1, hilly), hilly),
      VRow(109, BendOut(1532, HVUR), MouthOld(1538, HVDR)),
      TRow(108, Cape(0, 1), desert),
      VRow(107, MouthOld(1538, HVUL), BendAllOld(1540, HVDL)),
      TRow(106, desert * 3),
      VRow(105, BendAllOld(1540, HVUR), BendAllOld(1542, HVDL)),
      TRow(104, desert * 3),
      VRow(103, BendAllOld(1542, HVUR)),
      TRow(102, jungle * 2, hilly),
      TRow(100, jungle * 2, land),
      TRow(98, jungle * 2, sea),
      TRow(96, land * 2, Cape(1, 3)),
      VRow(95, MouthOld(1538, HVUL)),
      TRow(94, desert, Cape(1, 2), Cape(4, 2)),
      TRow(92, Cape(2, 2, hilly), sea),
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
