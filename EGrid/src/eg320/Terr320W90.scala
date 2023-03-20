/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** Terrain for 90 degrees west includes grid, tile terrain and straits [[Boolean]]s. */
object Terr320W90 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w90(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(160, tundra, sea)
    wr(158, tundra * 3)
    wr(156, sea, tundra * 2)
    wr(154, tundra * 3, sea)
    wr(152, tundra * 4)
    wr(150, taiga, tundra, sea * 2)
    wr(148, taiga, tundra, sea * 2, tundra)
    wr(146, taiga * 2, sea * 2, taiga)
    wr(144, taiga * 5)
    wr(142, taiga * 6)
    wr(140, plain * 2, taiga * 4)
    wr(138, plain * 3, lake, taiga * 3)
    wr(136, plain * 5, lake, taiga)
    wr(134, plain * 6, forestHills)
    wr(132, desert, plain * 4, forestHills, hills)
    wr(130, desert, plain * 5, hills, plain)
    wr(128, desert, plain * 3, hills * 2, plain, sea)
    res
  }
  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    res.setSomeInts(WSideMid(), 159,9729)
    res.setSomeInts(WSideMid(), 142,9736,  143,9735,  144,9734,  152,9730,  152,9734,  153,9731,  155, 9731,  156,9730,  157,9729,  158,9724,  158,9728,  159,9725,  159,9727)
    res.setSomeInts(WSideMid(Lake), 135,9731,  135,9741,  135, 9737,  136,9730,  137,9733)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setCornerIn(160, 9728, 4)//Devon and Cornwallis Islands
    res.setCornerIn(158, 9726, 0)//Somerset Island
    res.setCornerIn(158, 9726, 5)//Prince of Wales Island
    res.setCornerIn(158, 9726, 4)//Prince of Wales Island south
    res.setMouth0Corner(156, 9724)//Victoria Island - Prince of Wales Island south
    res.setTJunction(159, 9728)//Somerset Island - Devon Island - Baffin Island

    //res.setCorner(154, 9722, 0, HVDn, 1) //Victoria Island - Canada east
    //res.setCorner(154, 9722, 5, HVDL) //Victoria Island - Canada east
    res.setCornerPair(156, 9724, 4, HVDL, HVUL)

    res.setMouth4(160, 9732)//Bylot Island - Devon Island east
    res.setVert4In(158, 9730)//Baffin Island - Canada
    res.setVert1In(156, 9728)//Canada - Baffin Island
    res.setVert4In(156, 9732)//Baffin Island - Canada
    res.setMouth5(154, 9734)//Baffin Island - Canada
    res.setMouth3(154, 9734)//Southampton Island - Newfoundland north
    res.setMouth0(150, 9734)//Southampton Island - Newfoundland south

    res.setMouth4(154, 9734)//Southampton Island west
    res.setVert5In(152, 9732)//Southampton Island west
    res.setMouth0(150, 9730)//Southampton Island west

    res.setMouth3(146, 9734)//James Bay mouth
    res.setVert4In(144, 9736)//James Bay
    res.setVert1In(142, 9734)//James Bay
    res.setMouth0(140, 9736)//James Bay head

    res.setMouth3(138, 9730)//Lake Michigan north
    res.setVert4In(136, 9732)//Lake Michigan
    res.setMouth5(134, 9734)//Lake Michigan south

    res.setMouth2(138, 9730)//Lake Superior west
    res.setMouth5(136, 9736)//Lake Huron north west

    res.setMouth1(134, 9738)//Lake Ontario west
    res.setCornerIn(136, 9740, 2)//Lake Ontario east
    res.setCornerIn(134, 9742, 0)//Lake Ontario east

    res
  }
}