/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._, DirPath.{ strToStrs => sts}

object DirPathTest extends TestSuite
{
  val tests = Tests {

    val aa1: Array[String] = sts("dir1/dir2/dir3")
    test("String ext")
    { "hello".dropRightWhile(_ == 'o') ==> "hell"
      "John421".dropRightWhile(_.isDigit) ==> "John"
      aa1.length ==> 3
      aa1(0) ==> "dir1"
      aa1(1) ==> "dir2"
      aa1(2) ==> "dir3"
    }

    val pa1: DirPathAbs = DirPathAbs("/Documentation")
    test("Absololute")
    { pa1 /> "hello.html" ==> "/Documentation/hello.html"
    }

    val pr1: DirPathRel = DirPathRel("Documentation")
    test("Relative")
    { pr1 /> "hello.html" ==> "Documentation/hello.html"
    }
  }
}