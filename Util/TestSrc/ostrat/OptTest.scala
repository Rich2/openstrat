/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object OptTest extends TestSuite
{
  val tests = Tests {

    val i1: OptInt = NoInt

//    val i2 = OptOld(5)
    //def fa(opt: OptInt): Opt[String] = opt.map(_.toString)

    "test1" -
    {
      i1.toString ==> "NoInt"
 //     i1.mMap(_ *  2) ==> NoIntOld
//      i2.map(_ * 2) ==> SomeInt(10)
//      n1.map(_.length  + 2) ==> NoIntOld
//      assertMatch(n1.map(_.length  + 2)){ case NoOptOld() => }
//      assertMatch(n2.map(_.length  + 2)){ case SomeInt(7) => }
//      assertMatch(i2.map(i => Colour.Red)){ case SomeColour(Colour.Red) => }
    }
  }
}
