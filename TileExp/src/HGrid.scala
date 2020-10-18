/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import scala.math.sqrt

trait HGrid extends TGridSimple
{
  def numOfRow2s: Int
  def numOfRow0s: Int


  def numOfTileRows: Int = numOfRow2s + numOfRow0s

  override def xRatio: Double = 1.0 / sqrt(3)
}


