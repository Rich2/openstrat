/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import utest.{Show => _, _}, prid.phex._, egrid._

object EPersistTest  extends TestSuite
{
  val tests = Tests {
    val r1 = HCenRowLayer[WTile](4, Land(Plain), Land(Hilly))
    val rs1 = "HRow(4; Land(); Land(Hilly))"

    test("Test E1")
    {  r1.str ==> rs1
      "Land(Level; Temperate; MixedUse)".asTypeOld[Land] ==> Good(Land())
      "Land(Level; Temperate)".asTypeOld[Land] ==> Good(Land())
      "Land(Level)".asTypeOld[Land] ==> Good(Land())
      "Land()".asTypeOld[Land] ==> Good(Land())
      //rs1.asType[HCenRowLayer[WTile]] ==> Good(r1)
      4 ==> 4
    }
  }
}
