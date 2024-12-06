/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object DirPathTest extends TestSuite
{
  val tests = Tests {
    test("String ext")
    { "hello".dropRightWhile(_ == 'o') ==> "hell"
      "John421".dropRightWhile(_.isDigit) ==> "John"
    }
    val pa1: DirPathRel = DirPathRel("Documentation")
    test("Relative")
    {
      pa1 /> "hello.html" ==> "Documentation/hello.html"
    }
  }
}