/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pWW2
import pEarth._

case class W2Side(x: Int, y: Int, terr: SideTerr) extends ESide
{
  
}

object W2Side
{
   implicit val W2SideMakerImplicit: (Int, Int, SideTerr) => W2Side = W2Side.apply   
   
   implicit object ZugSideIsType extends IsType[W2Side]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2Side]
      override def asType(obj: AnyRef): W2Side = obj.asInstanceOf[W2Side]   
   }
}