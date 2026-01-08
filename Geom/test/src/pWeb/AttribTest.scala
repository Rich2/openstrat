/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest.*

/** Also tests the srcToETokens function object. */
object  AttribTest extends TestSuite
{
  val tests = Tests {
    test("Test Attributes")
    { NameAtt("John").out ==> "name='John'"
      TypeAtt("text/css").out ==> "type='text/css'"
      ClassAtt("main").out ==> "class='main'"
      IdAtt("topmenu").out ==> "id='topmenu'"
    }
    test("Links"){

    }
  }
}