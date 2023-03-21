/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(156, sea * 3)
    wr(154, sea * 4)
    wr(152, sea * 3, taigaHills)
    wr(150, sea * 3, taigaHills)
    wr(148, sea * 3, taigaHills, taiga)
    wr(146, sea, hills, sea, Head4Land(4), forest)
    wr(144, sea, Head4Land(5), plain, sea, plain)
    wr(142, sea, Head4Land(2), Head1Land(5), Head3Land(1), plain * 2)
    wr(140, sea, Head4Land(2, Hilly), Head2Land(5), plain * 3)
    wr(138, sea * 2, plain * 2, hills, mtain * 2)
    wr(136, sea * 3, plain, hills, mtain, plain)
    wr(134, sea, hills * 3, sea, Island(Hilly), hills)
    wr(132, sea, hills, plain * 2, sea, Island(Hilly), sea)
    wr(130, sea, Head4Land(3), Head1Land(3, Hilly), Head2Land(2, Hilly), sea * 2, Head3Land(5, Hilly), Head4Land(3, Hilly))
    wr(128, sea, Head2Land(5, Hilly), Head1Land(0, Hilly), hills * 3, Head1Land(1, Hilly), sea)

    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)

    res.setSomeInts(WSideMid(), 146,520,  145,521,  145,523,  146,524)//Scandinavia
    res.setSomeInts(WSideRt(), 145,517,  146,516,  147,517)//Denmark west
    res.setSomeInts(WSideLt(), 147,519)//Denmark

    res.setSomeInts(WSideRt(), 139,507,  140,506,  141,505,  142,504,  143,505,  144,506,  145,507)//British Isles
    res.setSomeInts(WSideLt(), 145,509,  144,510)//British Isles

    res.setSomeInts(WSideBoth(), 129,505, 129,507,  129,509,  129,511)//Alboran Sea

    res.setSomeInts(WSideBoth(), 131,521,  133,521,  130,524,  129,525)//Tunis - Sardinia - Corsica
    res.setSomeInts(WSideLt(), 132,522, 133,523,  134,524,  135,523)//Sardinia, Corsica
    res.setSomeInts(WSideRt(), 131,519,  132,518,  133,519,  134,520,  134,524,  135,521,  131,525)//Sardiaia, Corsica
    res.setSomeInts(WSideMid(),  134,528,  135,527,  136,526)//Mediterranean
    res.setSomeInts(WSideRt(), 130,520,  129,525)//Sicily - Tunis
    res.setSomeInts(WSideLt(), 130,528,  131,523,  129,527,  131,527)//North Africa, Sicily - Italy
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.set4CornersIn(146, 518, 4, 7)

    res.setVert4In(146, 522)//Copenhagen
    res.setCorner(146, 522, 3, HVUp)//Stralsund - Ystad
    res.setCorner(144, 520, 1, HVDn)//Stralsund - Ystad
    res.setCorner(146, 522, 2, HVUL)//Sweden
    res.setCorner(146, 522, 1, HVUL)//Sweden

    res.set4CornersIn(144, 508, 5, 7)//Northern Ireland
    res.set4CornersIn(142, 506, 2, 7)//Southern Ireland
    res.setCornerIn(142, 510, 5, 7)//Wales

    res.set4CornersIn(140, 508, 2, 7)//Devon and Cornwall
    res.set3CornersIn(142, 514, 1, 7)//South east England
    res.set2CornersIn(140, 512, 5, 7)//Normandy

    res.set3CornersIn(130, 502, 3, 7)//South west Spain
    res.setCornerIn(130, 506, 3, 7)//Gibraltar
    res.set2CornersIn(130, 510, 2, 7)//South west Spain
    res.set2CornersIn(128, 504, 5, 7)//Tangiers
    res.setCornerIn(128, 508, 0, 7)//East Morocco
    res.set3CornersIn(130, 522, 5, 7)//Tunisia
    res.setCornerIn(128, 524, 1, 7)//Tunisia east
    res.set4CornersIn(130, 526, 3, 7)//Sicilly

    res.set6CornersIn(132, 520, 7)//Sardinia
    res.set6CornersIn(134, 522, 7)//Corsica

    res.setMouth3Corner(138, 526)//Adriatic head
    res.setCornerIn(136, 524, 1)//Adriatic head
    res.setCorner(136, 524, 2, HVDL)//Adriatic San Marino
    res.setCorner(134, 526, 0, HVDL)//Adriatic San Marino
    res.setCornerIn(134, 526, 1)//Adriatic
    res.setCorner(134, 526, 2, HVDL)//Adriatic

    res
  }
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[TRow] = RArr(
      //TRow
      TRow(126, sea, Head1Land(5), mtain, desertHills, desert * 4),
      TRow(124, sea, desertHills, hills, desert * 6),
    )
  }
  help.run

}

object BritReg
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr320E0.grid, Terr320E0.terrs)
  def britSTerrs: HSideLayer[WSide] = britGrid.sideLayerSpawn(Terr320E0.grid, Terr320E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr320E0.grid, Terr320E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}