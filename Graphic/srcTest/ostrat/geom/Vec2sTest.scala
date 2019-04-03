/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object LinePathTest extends TestSuite
{
  
  val tests = Tests
  {
    val v1s: LinePath = LinePath(2.1 vv 0, 5.4 vv 0)
    't1 { assert(v1s.str == "LinePath(2.1, 0.0; 5.4, 0.0)") }
    val v3: Vec2 = 4 vv 4    
    val v2s: LinePath = v1s :+ v3
    't2 { assert(v2s.str == "LinePath(2.1, 0.0; 5.4, 0.0; 4.0, 4.0)") }    
  }
}