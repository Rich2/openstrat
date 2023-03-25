/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France. The Faroe Islands and the Shetland Islands are large enough as an island.  */
object Terr120E0 extends Long120Terrs
{
  override implicit val grid: EGrid120LongFull = EGrid120.e0(300)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(328, sea * 6, sea * 3, mtain, taigaHills * 3)
    wr(326, sea * 5, hills, sea * 3, taigaHills * 2, taiga * 3)
    wr(324, sea * 3, plain, hills, sea * 4, taigaHills * 2, sea, plain * 2)
    wr(322, sea * 3, hills, hills * 2, sea * 5, plain, sea, plain * 2)
    wr(320, sea * 4, plain * 2, sea * 5, plain * 2, sea, plain)
    wr(318, sea * 3, plain, hills * 2, sea * 5, plain, plain, plain, sea)
    wr(316, sea * 2, plain * 3, hills, plain, sea * 5, plain, sea * 3)
    wr(314, sea * 2, plain * 3, hills, plain * 2, sea * 3, plain * 5)
    wr(312, sea * 2, plain * 2, sea, hills, plain * 3, sea, plain * 6)
    wr(310, sea * 2, plain, sea * 2, hills, plain * 3, sea, plain * 3, hills * 2, plain * 2)
    wr(308, sea * 5, hills * 2, plain * 2, plain * 3, hills * 5)
    wr(306, sea * 8, plain * 4, hills * 6)
    wr(304, sea * 6, plain * 5, hills * 4, plain, hills * 2)
    wr(302, sea * 6, plain * 5, hills * 2, forestHills, hills * 2, mtain * 2)
    wr(300, sea * 8, plain * 4, hills * 2, mtain * 4, hills)

    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    res.setSomeInts(WSideMid(), 319,499,  318,500,  317,501,  316,502,  315,501,  314,500,  321,499,  322,500,  323,501,  324,502,  325,505)//Britain
    res.setSomeInts(WSideMid(), 307,509,  307,511,  307,513,  308,514,  309,515)//English Channel
    res.setSomeInts(WSideMid(), 323,529,  317,533,  318,532,  318,536,  319,533,  319,539)//Denmark
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.setMouth2(320, 496)//Scotland - Ireland north west
    res.setVert1In(318, 498)//Ireland - Scotland
    res.setVert4In(318, 502)//Scotland - Ireland
    res.setVert1In(316, 500)//Irish Sea
    res.setVert2In(316, 500)//Irish Sea
    res.setVert5In(314, 502)//Irish Sea
    res.setMouth0(312, 500)//Irish Sea

    res.setMouth1(306, 506)//English Channel Cherbourg
    res.setVert0In(306, 510)//English Channel
    res.setVert3In(308, 512)//English Channel
    res.setVert2In(308, 512)//English Channel
    res.setVert5In(308, 516)//English Channel
    res.setMouth4(310, 518)//English Channel Dover - Calais

    res.setMouth4(320, 536)
    res.setVert5In(318, 534)
    res.setVert4In(318, 534)
    res.setMouth5(316, 536)
    res.setMouth3(320, 536)
    res.setMouth0(316, 536)
    res.setMouth2(320, 536)
    res.setMouth5(318, 542)
    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(344, sea * 9, Head2Land(4, Mountains)),
      TRow(342, sea * 10, mtain) ,
      TRow(340, sea * 10, mtain),
      TRow(338, sea * 9, tundraHills * 2),
      TRow(336, sea * 9, taigaHills * 2, taiga),
      TRow(334, sea * 8, Head2Land(5, Mountains), taigaHills * 3),
      TRow(332, sea * 3, Island(Hilly), sea * 4, Head2Land(4, Mountains), mtain, taigaHills, taiga * 2),
      TRow(330, sea * 5, Island(Hilly), sea * 2, mtain * 4, taigaHills),
    )
  }
  help.run
}

object BritReg120
{ def britGrid: EGrid120Long = EGrid120Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr120E0.grid, Terr120E0.terrs)
  def britSTerrs: HSideLayer[WSide] = britGrid.sideLayerSpawn(Terr120E0.grid, Terr120E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr120E0.grid, Terr120E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid120Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}