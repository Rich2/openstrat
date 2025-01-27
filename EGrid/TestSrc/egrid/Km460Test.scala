/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => *, *}, prid.phex.*, eg460.*

object Km460Test
  extends TestSuite
  {
  val tests = Tests{
    val scen = Scen460Atlantic
    val gs = scen.gridSys
    val corners = scen.corners

    val sPoly: PolygonHvOffset = corners.sidePoly(HSep(72, 10750))(gs)
    test("SidePoly"){
      sPoly.numVerts ==> 6
    }

  }
}