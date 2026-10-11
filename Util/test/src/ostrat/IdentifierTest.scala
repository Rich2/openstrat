/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest.{Show => _, _}, pParse.*

object IdentifierTest extends TestSuite
{ val tests = Tests {
  test("Ident 1")
  { assert("Id1".asType[Identifier].isRight)
    assert("Id1".asType[IdentUpper].isRight)
    assert("Id1".asType[IdentLower].isLeft)
  }
}
}