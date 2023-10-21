/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import utest._

object Peri extends TestSuite
{
  val tests = Tests{
    val army1: Army = Army(NRed, 2)
    val str1 = "Army(NRed; 2)"
    
    test("Path")
    {  army1.str ==> str1
      "NRed".asType[Nation](Nation.unshowEv(NRed)) ==> Good(NRed)
      str1.asType[Army](Army.unshowEv(NRed)) ==> Good(army1)
    }
  }
}