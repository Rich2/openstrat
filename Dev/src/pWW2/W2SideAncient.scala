/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pWW2
import pGrid._

case class W2SideAncient(x: Int, y: Int, terr: SideTerr) extends ESideAncient

object W2SideAncient
{
   implicit val W2SideMakerImplicit: (Int, Int, SideTerr) => W2SideAncient = W2SideAncient.apply
   
   implicit object ZugSideIsType extends IsType[W2SideAncient]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2SideAncient]
      override def asType(obj: AnyRef): W2SideAncient = obj.asInstanceOf[W2SideAncient]
   }
}