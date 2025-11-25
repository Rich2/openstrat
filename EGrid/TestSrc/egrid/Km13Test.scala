/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => *, *}, prid.phex.*, eg13.*

object Km13Test extends TestSuite
{
  val tests = Tests{
    val scen = Scen13All
    val gs = scen.gridSys
    val corners = scen.corners

    val sPoly: PolygonHvOffset = corners.sepPoly(HSep(92, 6654))(using gs)
    test("SidePoly")
    { sPoly.numVerts ==> 6
    }
  }
}