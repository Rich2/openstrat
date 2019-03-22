/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import pGrid._

/** Not sure if this trait needs to exist anymore */
trait ESide extends TileSide
{
   def terr: SideTerr
}

object ESide
{
   implicit object ESideIsType extends IsType[ESide]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESide]
      override def asType(obj: AnyRef): ESide = obj.asInstanceOf[ESide]      
   }
}

trait SideTerr

object SideNone extends SideTerr
object SideEdge extends SideTerr
object Straits extends SideTerr

object VertIn

case class ESideOnly(x: Int, y: Int, terr: SideTerr) extends ESide

object ESideOnly
{
   implicit object ESideOnlyIsType extends IsType[ESideOnly]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESideOnly]
      override def asType(obj: AnyRef): ESideOnly = obj.asInstanceOf[ESideOnly]      
   }
}