/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import pGrid._

/** Not sure if this trait needs to exist anymore */
trait ESideOld extends TileSideOld
{
   def terr: SideTerr
}

object ESideOld
{
   implicit object ESideIsType extends IsType[ESideOld]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESideOld]
      override def asType(obj: AnyRef): ESideOld = obj.asInstanceOf[ESideOld]
   }
}

trait SideTerr

object SideNone extends SideTerr
object SideEdge extends SideTerr
object Straitsold extends SideTerr

object VertIn

case class ESideOldOnly(x: Int, y: Int, terr: SideTerr) extends ESideOld

object ESideOldOnly
{
   implicit object ESideOnlyIsType extends IsType[ESideOldOnly]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESideOldOnly]
      override def asType(obj: AnyRef): ESideOldOnly = obj.asInstanceOf[ESideOldOnly]
   }
}