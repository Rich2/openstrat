/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gTwo
import pGrid._

trait TwoScen
{
  def grid: SquareGridSimple
}

object TwoScen1 extends TwoScen
{
  implicit val grid = new SquareGridSimple(2, 8, 2, 10)
}
