/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pGrid._

/** Not sure if this trait needs to exist anymore */
trait ESideAncient extends TileSideAncient
{
   def terr: SideTerr
}

object ESideAncient
{
   implicit object ESideIsType extends IsType[ESideAncient]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESideAncient]
      override def asType(obj: AnyRef): ESideAncient = obj.asInstanceOf[ESideAncient]
   }
}

trait SideTerr

object SideNone extends SideTerr
object SideEdge extends SideTerr
object Straitsold extends SideTerr

object VertIn

case class ESideOnyAncient(x: Int, y: Int, terr: SideTerr) extends ESideAncient

object ESideOnyAncient
{
   implicit object ESideOnlyIsType extends IsType[ESideOnyAncient]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESideOnyAncient]
      override def asType(obj: AnyRef): ESideOnyAncient = obj.asInstanceOf[ESideOnyAncient]
   }
}