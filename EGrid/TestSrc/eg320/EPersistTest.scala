/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import utest._, prid.phex._, egrid._

object EPersistTest  extends TestSuite{
  val tests = Tests {
    val r1 = HCenRowLayer[WTile](4, Land(), Land(Hilly))

    test("Test E1"){
      r1.str ==> "HRow(4; Level, Temperate, MixedUse; Hilly, Temperate, MixedUse)"
      4 ==> 4
    }
  }
}
