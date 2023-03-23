/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France. Majorca is still just too small in area at this scale.  */
object Terr220E0 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.e0(132)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }


    wr(168, sea * 2, hills, sea * 2, taigaHills, taiga)
    wr(166, sea * 2, hills, sea * 3, plain, plain)
    wr(164, sea, plain, hills, sea * 3, plain, sea)
    wr(162, sea, plain, sea, plain, sea, plain * 4)
    wr(160, sea, plain, sea, plain * 2, plain * 2, hills, plain)
    wr(158, sea * 2, hills, sea, plain, hills * 4)
    wr(156, sea * 3, plain * 3, hills * 2, plain, hills)
    wr(154, sea * 4, plain * 2, hills, mtain * 3)
    wr(152, sea * 4, plain, hills, mtain, hills, plain, hills)
    wr(150, sea, hills * 5, sea * 2, Island(Hilly), hills, sea)
    wr(148, sea * 2, plain * 4, sea * 4, hills)
    wr(146, sea, plain * 4, sea * 3, hills, sea * 2)
    wr(144, sea * 2, plain * 3, sea * 5, hills)


    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)

    res.setSomeInts(WSideMid(),  157,507,  159,513,  160,514,  164,506,  165,505)//British Isles
    res.setSomeInts(WSideMid(), 167,521,  167,523,  166,524,  165,525)//Denmark

    res.setSomeInts(WSideMid(), 144,534,  151,531,  152,530)//Mediterranean
    res.setSomeInts(WSideRt(), 151,525)//Corsica north west
    res.setSomeInts(WSideLt(), 151,527,  150,528)

    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.setMouth2(166, 502)//Ireland - Scotland north west
    res.setVert1In(164, 504)//Ireland - Scotland
    res.setMouth0(162, 506)//Ireland - Scotland south east
    res.setMouth1(156, 504)//English Channel west
    res.setMouth4(158, 510)//Devon - Brittany
    res.setMouth1(158, 510)//Portsmouth Le Havre
    res.setVert2In(160, 512)//Dover Calais
    res.setMouth3(162, 514)//English Channel north east

    res.setMouth1(166, 518)//Denmark - Norway west
    res.setVert0In(166, 522)//Denmark Oslo
    res.setVert1In(166, 522)//Greaa - Halmstad
    res.setVert4In(166, 526)//Zealand - Sweden north
    res.setMouth5(164, 528)//Zealand - Sweden south east

    res.setMouth3(154, 530)//Adriatic north
    res.setVert4In(152, 532)//Adriatic
    res.setMouth5(150, 534)//Adriatic
    res.setMouth2Corner(150, 534)

    res.setMouth3Corner(146, 534)//Sicily - Italy north
    res.setCorner(144, 532, 2, HVUL)//Sicily - Italy south
    res.setMouth0Corner(142, 534)//Sicily - Italy south

    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[TRow] = RArr(
      TRow(178, sea * 4, Head2Land(4, Hilly, Taiga)),
      TRow(176, sea * 5, taigaHills),
      TRow(174, sea * 5, taigaHills),
      TRow(172, sea * 5, taigaHills, taiga),
      TRow(170, sea * 4, taigaHills, taiga * 2),

      TRow(142, sea * 3, Head3Land(2, Hilly), sea * 2, hills * 4, Head3Land(0, Hilly), sea),
      TRow(140, sea * 2, Head2Land(5, Hilly), hills, desertHills * 2, desert * 2, desertHills * 2, sea * 2),
      TRow(138, sea * 2, plain, desert, desertHills, desert * 7),
      TRow(136, sea * 2, mtain * 3, desert * 8),
      TRow(134, sea, desertHills * 2, desert * 10),
      TRow(132, sea, desert * 12)
    )
  }
  help.run
}

object BritReg220
{ def britGrid: EGrid220Long = EGrid220Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr220E0.grid, Terr220E0.terrs)
  def britSTerrs: HSideLayer[WSide] = britGrid.sideLayerSpawn(Terr220E0.grid, Terr220E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr220E0.grid, Terr220E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}