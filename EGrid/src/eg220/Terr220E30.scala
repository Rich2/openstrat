/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._
import phex._
import egrid._
import WTile._
import ostrat.egrid

object Terr220E30 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.e30(132)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(154, plain * 2, hills * 2, plain * 3, sea, plain * 2)
    wr(152, hills * 2, plain * 2, sea * 2, plain, hills, plain * 2)
    wr(150, hills * 4, sea * 5, mtain, hills)
    wr(148, hills * 4, plain, hills * 4, mtain, hills)
    wr(146, hills, hills * 2, hills * 2, desertHills * 3, mtain, desertHills * 2)
    wr(144, hills, sea, hills, sea, hills * 4, desertHills, plain, mtain)
    wr(142, sea * 7, Island(Hilly), hills, desert * 3)
    wr(140, sea * 3, hills, sea * 4, hills, desert * 3)
    wr(138, sea * 2, desert, sea * 5, hills, desert * 3)
    wr(136, desert, sea, desert * 4, plain, desertHills, desert * 5)
    wr(134, desert * 6, plain, desert, desertHills, desertHills, desert * 3)
    wr(132, desert * 6, plain, desert, sea, desert * 4)

    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)

    res.setSomeInts(WSideMid(), 144,1514,  146,1520,  147,1519,  148,1518,  149,1517)//Mediterranean
    res.setSomeInts(WSideMid(), 145,1523,  147,1525,  147,1527,  146,1528,  147,1529,  147,1531,  147,1533,  148,1534,  152,1546)//Greece / Turkey
    res.setSomeInts(WSideMid(), 134, 1540,  133,1541,  134,1544)
    res.setSomeInts(WSideRt(), 143, 1541)//Cyprus north west
    res.setSomeInts(WSideLt(), 143,1543,  142,1544)//Cyprus north east and east.
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setCorner(148, 1516, 0, HVDn)//Adriatic
    res.setCorner(150, 1518, 4, HVUR)//Adriatic
    res.setVert1In(148, 1516)//Adriatic
    res.setVert4In(148, 1520)//Adriatic
    res.setVert1In(146, 1518)//Adriatic
    res.setMouth0(144, 1520)//Adriatic south

    res.setCorner(144, 1516, 5, HVDR)//Italy - Sicily north
    res.setCorner(144, 1516, 4, HVUR)//Italy - Sicily
    res.setMouth0(142, 1514)

    res.setMouth1(144, 1520)//Peloponnese - Greece west
    res.setMouth4(146, 1526)//Peloponnese - Greece
    res.setMouth1(146, 1522)//Thessaloniki - Aegean
    res.setVert0In(146, 1526)//Thrace - Aegean
    res.setTJunction(147, 1528)//Aegean - Dardanelles
    res.setMouth0(144, 1528)//Thrace - Aegean
    res.setVert0In(146, 1530)//Dardanelles
    res.setVert3In(148, 1532)//Sea of Marmara
    res.setVert2In(148, 1532)//Constantinople
    res.setMouth3(150, 1534)//Sea of Marmara north

    res.setMouth3(154, 1546)//Kerch Straits north
    res.setMouth0(150, 1546)//Kerch Straits south

    res.setMouth3(136, 1540)//Suez Gulf of Suez north
    res.setVert4In(134, 1542)//Gulf of Suez
    res.setMouth5(132, 1544)//Gulf of Suez south
    res.setMouth0(132, 1544)//Gulf of Arabia south
    res.setMouth3(136, 1544)//Gulf of Arabia north

    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(182, Head2Land(5, Hilly, Tundra), Head2Land(0, Hilly, Tundra), sea * 3),
      TRow(180, tundraHills, taiga, tundra, Head2Land(0, Hilly, Tundra), sea),
      TRow(178, taiga * 4, Head3Land(5, Plains, Tundra)),
      VRow(177, MouthUp(1530), MouthDL(1538), VertInUL(1544)),
      TRow(176, Head1Land(2, Plains, Taiga), taiga * 2, Head3Land(0, Plains, Taiga), Head2Land(4, Plains, Taiga), taiga),
      VRow(175, MouthDL(1540), MouthDR(1544)),
      TRow(174, Head1Land(2, Plains, Taiga), Head1Land(5, Plains, Taiga), taiga * 4),
      VRow(173, MouthUL(1540, Lake), VertInDL(1542, Lake)),
      TRow(172, Head1Land(2, Plains, Taiga), Head1Land(5, Plains, Taiga), taiga * 5),
      VRow(171, MouthDL(1536, Lake), MouthUR(1538, Lake), MouthDn(1542, Lake)),
      TRow(170, sea, Head2Land(3, Plains, Taiga), Head1Land(3, Plains, Taiga), taiga * 4),
      VRow(169, MouthUR(1536)),
      TRow(168, Head2Land(1), Head2Land(5), Head1Land(0), forest * 2, taiga * 2),
      TRow(166, Head2Land(2), Head1Land(5), plain * 2, forest * 2, plain, forest),
      TRow(164, Head1Land(5), plain * 7),
      TRow(162, plain * 9),
      TRow(160, plain * 3, forest, plain * 5),
      TRow(158, hills * 2, plain * 7),
      TRow(156, plain * 2, mtain, plain * 6, desert),
    )
  }
  help.run
}