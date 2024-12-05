/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object DirnPathTest extends TestSuite
{
  val tests = Tests {
    val pa1: DirPathRel = DirPathRel("Documentation")
    test("Relative")
    {
      pa1.appendStr("hello.html") ==> "Documentation/hello.html"
    }
  }
}