/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import utest.*, utest.{Show => _, _}

object PathTokenTest extends TestSuite
{
  val tests = Tests {
    test("PathToken")
    {
      lexSrc.str("/op") ==> Succ(RArr(PathToken(StrPosn(1, 1), Array[String]("op"))))
    }
  }
}