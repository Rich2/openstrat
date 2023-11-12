/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import utest._, prid.phex._, egrid._

object EPersistTest  extends TestSuite
{
  val tests = Tests {
    val r1 = HCenRowLayer[WTile](4, Land(), Land(Hilly))
    val rs1 = "HRow(4; Land(Level); Land(Hilly))"

    test("Test E1")
    {  r1.str ==> rs1
      "Land(Level; Temperate; MixedUse)".asType[Land] ==> Good(Land())
      "Land(Level; Temperate)".asType[Land] ==> Good(Land())
      "Land(Level)".asType[Land] ==> Good(Land())
      "Land()".asType[Land] ==> Good(Land())
      //rs1.asType[HCenRowLayer[WTile]] ==> Good(r1)
      4 ==> 4
    }
  }
}
