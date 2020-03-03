/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWW2
import pEarth._

case class W2SideOld(x: Int, y: Int, terr: SideTerr) extends ESideOld
{
  
}

object W2SideOld
{
   implicit val W2SideMakerImplicit: (Int, Int, SideTerr) => W2SideOld = W2SideOld.apply
   
   implicit object ZugSideIsType extends IsType[W2SideOld]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2SideOld]
      override def asType(obj: AnyRef): W2SideOld = obj.asInstanceOf[W2SideOld]
   }
}