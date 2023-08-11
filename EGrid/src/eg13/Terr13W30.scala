/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr13W30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w30(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(118, sea),
//      TRow(116, taiga),
//      TRow(114, plain),
//      TRow(112, hills, plain),
//      TRow(110, hills, hills),
//      TRow(108, plain, desert),
//      VRow(107, Mouth(1538, HVUL), VertIn(1540, HVDL)),
    //  TRow(106, sea * 2, Hland(2, 4, Level(Desert))),
//      VRow(105, VertIn(1540, HVUR), VertIn(1542, HVDL)),
      TRow(104, sea, Hland(2, 4, Level(Desert))),
//      VRow(103, VertIn(1542, HVUR)),
//      TRow(102, jungle * 2, hills),
//      TRow(100, jungle * 2, plain),
      TRow(98, Hland(2, 1, Level(Jungle)), sea),
//      TRow(96, plain * 2, Hland(3, 1)),
//      VRow(95, Mouth(1538, HVUL)),
//      TRow(94, desert, Hland(2, 1), Hland(2, 4)),
//      TRow(92, Hland(2, 2, Hilly()), sea),
//      TRow(90, sea * 2),
//      TRow(88, sea * 2),
//      TRow(86, sea),
//      TRow(84, sea),
//      TRow(82, ice),
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
