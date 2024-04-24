/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object LengthTest  extends TestSuite
{
  val tests = Tests {

    test("Length 1"){
      "4km".asType[Kilometres] ==> Good(Kilometres(4))
    }
  }
}