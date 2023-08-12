/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr13E60 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e60(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, desert),
      VRow(109, Mouth(2558, HVUp)),
      TRow(108, SideB(Lake), desert),
      VRow(107, Mouth(2556, HVDR)),
      TRow(106, desert, hills),
      VRow(105, VertIn(2558, HVUp), VertIn(2560, HVDn)),
      TRow(104, Hland(3, 1, Hilly(Desert)), Hland(1, 4)),
      TRow(102, Hland(3, 0, Level(Desert)), sea),
      TRow(100, SideB(), sea * 2),
      VRow(99, SetSide(2557)),
      TRow(98, SideB(), sea * 2),
      VRow(97, SetSide(2557)),
      TRow(96, SideB(), sea * 2),
      VRow(95, SetSide(2557)),
      TRow(86, Hland(1, 0, Level(IceCap)))
    )
  }
  help.run
}

/*
object BritReg
{ def britGrid: EGrid14Long = EGrid14Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = Terr14E0.terrs.spawn(Terr14E0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr14E0.sTerrs.spawn(Terr14E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr14E0.corners.spawn(Terr14E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid14Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}*/
