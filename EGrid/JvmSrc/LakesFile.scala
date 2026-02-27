/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import utiljvm.*

object LakesFile
{
  def apply(path: String): Unit =
  { val l1: RArr[LakePoly] = earthAllEPolys.lakeFilter
    val l2 = l1.sortBy(_.area < _.area)
    val l3 = l2.map(lk => lk.name.oneLine -- lk.area.str)
    val str = l3.mkStr("\n")
    writeFile(path / "lakes.txt", str)
  }
}