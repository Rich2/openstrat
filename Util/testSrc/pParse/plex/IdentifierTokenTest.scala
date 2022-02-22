/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import utest._

object IdentifierTokenTest extends TestSuite
{
  val z1 = "Zog"
  val c1 = z1.toChars
  val o1 = c1.offsetter0
  val p1 = StrPosn(1, 1)
  val r1: EMon3[CharsOff, TextPosn, Token] = lexIdentifierToken(o1, p1)(z1.toChars)
  val z2 = "zog"
  val r2: EMon3[CharsOff, TextPosn, Token] = lexIdentifierToken(o1, p1)(z2.toChars)
  val e1 = "e4a"
  val r3: EMon3[CharsOff, TextPosn, Token] = lexIdentifierToken(o1, p1)(e1.toChars)

  val tests = Tests {
    "Parse" -
    {
      assertMatch(r1){ case Good3(CharsOff(3), StrPosn(1, 4), IdentUpperToken(_, _)) => }
      assertMatch(r2){ case Good3(CharsOff(3), StrPosn(1, 4), IdentLowerToken(_, _)) => }
      assertMatch(r3){ case Good3(CharsOff(3), StrPosn(1, 4), IdentLowerToken( _, _)) => }
    }
  }
}