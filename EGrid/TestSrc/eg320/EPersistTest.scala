/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import utest.{Show => _, _}, prid.phex._, egrid._

object EPersistTest  extends TestSuite
{
  val tests = Tests {
    val r1 = HCenRowLayer[WTile](4, Land(Plain, Oceanic), Land(Hilly, Oceanic))
    val rs1 = "HRow(4; Plain, Oceanic; Hilly, Oceanic)"

    test("Test E1")
    {  r1.str ==> rs1
      "Land(Plain; Oceanic; MixedUse)".asType[Land] ==> Succ(Land(Plain, Oceanic))
      "Land(Plain; Oceanic)".asType[Land] ==> Succ(Land(Plain, Oceanic))
      "Land(Plain)".asType[Land] ==> Succ(Land(Plain, Oceanic))
      "Land()".asType[Land] ==> Succ(Land(Plain, Oceanic))
    }
  }
}
