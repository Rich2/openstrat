/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTile._

object Terr320E30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e30(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }


    gs(142, 1526, plain * 6)
    gs(140, 1528, plain * 6)
    gs(138, 1526, mtain * 2, hills, plain * 3, desert)
    gs(136, 1524, hills, plain * 2, sea, plain * 3)
    gs(134, 1526, hills * 2, Head3Land(1, Hilly), sea * 3, mtain)
    gs(132, 1524, Head2Land(1, Hilly), hills, Head3Land(4, Hilly), hills * 4)
    wr(130, Head2Land(2, Hilly), Head4Land(3, Hilly), Head3Land(1, Hilly), Head2Land(3, Hilly), hills * 4)
    wr(128, sea * 2, Island(Hilly), sea, Island(Hilly), hills, desert * 2)
    wr(126, sea, desert, sea * 3, hills, desert * 2)
    wr(124, desert * 4, plain, desert * 4)
    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    
    res.setSomeInts(WSideMid(), 131, 1527, 133,1525,  134,1524,  135,1523,  136,1522,  136,1542,  137,1541,  137,1543)
    res.setSomeInts(WSideMid(), 130,1524,  131,1525,  132,1526,  132,1530)
    res.setSomeInts(WSideMid(), 133,1531,  133,1533,  133,1535)//Bosphorus
    res.setSomeInts(WSideLt(), 130,1520, 131,1521)//Sicily - Italy
    res.setSomeInts(WSideRt(), 127,1531,  128,1530,  129,1531,  129,1539,  128,1538, 127,1539)//West Crete and Cyprus
    res.setSomeInts(WSideLt(), 127,1533,  128,1534,  129,1533,  129,1541,  128,1542, 127,1541)//East Crete and Cyprus

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth0(134, 1542)//Kerch straits

    res.setTJunction(137, 1542)//Azov Sea
    res.setMouth4(138, 1546)//Rostov north west Azov sea
    res.setMouth2(138, 1538)//Azov Sea north east

    res.setCornerIn(136, 1524, 5)//Adriatic head
    res.setCornerIn(136, 1524, 4)//Adriatic San Marino
    res.setCorner(136, 1524, 3, HVUR)//Adriatic
    res.setCorner(134, 1526, 5, HVUR)//Adriatic
    res.setCornerIn(134, 1526, 4)//Adriatic
    res.setCorner(132, 1524, 0, HVDL)//Adriatic
    res.set2CornersIn(132, 1524, 1, 7)//Italy Heel
    res.set2CornersIn(130, 1522, 2, 7)//Italy Toe

    res.set6CornersIn(128, 1532, 7)//Crete
    res.set6CornersIn(128, 1540, 7)//Cyprus
    res.set3CornersIn(134, 1534, 1, 7)//Thrace
    res.set4CornersIn(130, 1526, 3, 7)//Peloponnesian Peninsular
    res.set3CornersIn(130, 1530, 1, 7)//Athens
    res.setMouth5(130, 1530)
    res.set3CornersIn(132, 1532, 4, 7)//North east Anatolia
    res.set2CornersIn(130, 1534, 3, 7)//South east Anatolia

    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(156, Head2Land(5, Hilly, Tundra), Head2Land(0, Hilly, Tundra), sea),
      TRow(154, taigaHills, taiga * 2, Head4Land(0, Plains, Tundra)),
      VRow(153, MouthUp(1534)),
      TRow(152, Head1Land(2, Plains, Taiga), taiga, sea, taiga),
      VRow(151, VertInDR(1532)),
      TRow(150, Head1Land(2, Plains, Taiga), taiga * 3),
      VRow(149, MouthUL(1536, Lake), MouthDR(1538, Lake)),
      VRow(147, MouthUR(1534)),
      TRow(148, Head1Land(2, Plains, Taiga), Head3Land(3, Plains, Taiga), taiga * 3),
      TRow(146, Head2Land(5), taiga * 4),
      TRow(144, plain * 5),

    )
  }

  help.run
}