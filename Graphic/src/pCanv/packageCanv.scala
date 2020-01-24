/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The package attempts to encapsulate the various abstract canvas traits while the actual objects that populate a canvas go in package geom. */
package object pCanv 
{
  type MenuSeq = Seq[MenuNode]
  type MB0 = MouseButton => Unit
}