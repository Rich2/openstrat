/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

abstract class ShowSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](ev1: Show[A1], ev2: Show[A2]) extends Show[ST]
{
  
}