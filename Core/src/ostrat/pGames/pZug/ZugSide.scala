/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

case class ZugSide(x: Int, y: Int, wall: Boolean) extends GridElem
{
  
}

object ZugSide
{
   implicit val zugSideMakerImplicit: (Int, Int, Boolean) => ZugSide = ZugSide.apply 
}