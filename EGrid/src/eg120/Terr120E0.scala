/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._
import phex._
import egrid._
import WTile._
import ostrat.egrid

/** [[WTile]] terrain for 15 West to 15 East. The Faroe Islands and the Shetland Islands are large enough to qualify as an island. The Orkney's are
 *  probably not, even with the mainland that comes into the hex, but for the sake of Scapa FLow they will be an [[Island]].  */
object Terr120E0 extends Long120Terrs
{
  override implicit val grid: EGrid120LongFull = EGrid120.e0(300)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }



    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)

   // res.setSomeInts(WSideMid(), 307,509,  307,511,  307,513,  308,514,  309,515)//English Channel
    //res.setSomeInts(WSideMid(), 323,529,  317,533,  318,532,  318,536)//,  319,533,  319,539)//Denmark
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

   // res.setMouth1(306, 506)//English Channel Cherbourg
   // res.setVert0In(306, 510)//English Channel
   // res.setVert3In(308, 512)//English Channel
    //res.setVert2In(308, 512)//English Channel
   // res.setVert5In(308, 516)//English Channel
    //res.setMouth4(310, 518)//English Channel Dover - Calais

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
      TRow(328, sea * 6, sea * 3, mtain, taigaHills * 3),
      TRow(326, sea * 5, Island(Hilly), sea * 3, Head2Land(4, Hilly, Taiga), taigaHills, taiga * 3),
      TRow(324, sea * 3, plain, hills, sea * 4, Head2Land(3, Hilly, Taiga), Head2Land(2, Hilly, Taiga), sea, plain * 2),
      TRow(322, sea * 3, hills, hills * 2, sea * 5, plain, sea, plain * 2),
      TRow(320, sea * 4, plain * 2, sea * 5, plain * 2, sea, plain),
      VRow(319, MouthUp(536), MouthUL(538), MouthDR(540)),
      TRow(318, sea * 3, Head3Land(5), Head1Land(4, Hilly), hills, sea * 5, plain, plain, plain, sea),
      VRow(317, MouthDn(536)),
      TRow(316, sea * 2, Head2Land(5), plain, Head3Land(1), Head2Land(3, Hilly), plain, sea * 5, plain, sea * 3),
      VRow(315, MouthUL(498), MouthUR(506)),
      TRow(314, sea * 2, Head2Land(4), plain, Head2Land(1), Head3Land(4, Hilly), plain * 2, sea * 3, Head2Land(5), plain * 4),
      TRow(312, sea * 2, plain * 2, sea, hills, plain * 2, Head3Land(0), sea, plain * 6),
      TRow(310, sea * 2, Headland(4, 2), sea * 2, Head2Land(3, Hilly), plain * 2, Head1Land(2), sea, plain * 3, hills * 2, plain * 2),
      VRow(309, MouthUR(504)),
      TRow(308, sea * 5, Head3Land(4, Hilly), Head1Land(3, Hilly), Head1Land(3), Head2Land(2), Head2Land(5), plain * 2, hills * 5),
      TRow(306, sea * 8, Head3Land(5), Head1Land(5), plain * 2, hills * 6),
      VRow(305, MouthDn(512)),
      TRow(304, sea * 6, plain * 5, hills * 4, plain, hills * 2),
      TRow(302, sea * 6, Head3Land(3), plain * 4, hills * 2, forestHills, hills * 2, mtain * 2),
      TRow(300, sea * 8, plain * 4, hills * 2, mtain * 4, hills),
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