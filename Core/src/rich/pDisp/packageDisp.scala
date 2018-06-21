/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

/** So first off the demarcation between this package and rich.geom is unclear. The package attempts to encapsulate the various 
 *  abstract canvas traits while the actual objects that populate a canvas go in package geom */
package object pDisp
{
  type MenuSeq = Seq[MenuNode]
}