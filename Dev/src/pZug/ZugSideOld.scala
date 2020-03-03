/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

case class ZugSideOld(x: Int, y: Int, wall: Boolean) extends TileSideOld
{
  
}

object ZugSideOld
{
   implicit val zugSideMakerImplicit: (Int, Int, Boolean) => ZugSideOld = ZugSideOld.apply
   
   implicit object ZugSideIsType extends IsType[ZugSideOld]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ZugSideOld]
      override def asType(obj: AnyRef): ZugSideOld = obj.asInstanceOf[ZugSideOld]
   }
}